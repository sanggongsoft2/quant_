package com.quant_socket.handlers;

import com.quant_socket.models.Logs.*;
import com.quant_socket.models.Product;
import com.quant_socket.repos.*;
import com.quant_socket.services.EquitiesSnapshotService;
import com.quant_socket.services.SocketLogService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class TelnetServerHandler extends ChannelInboundHandlerAdapter {
    private final SocketLogRepo repo;
    private final EquitiesBatchDataRepo equitiesBatchDataRepo;
    private final EquitiesSnapshotRepo esRepo;
    private final ProductRepo productRepo;
    private final EquityIndexIndicatorRepo eiiRepo;
    private final SecOrderFilledRepo secOrderFilledRepo;

    private final EquitiesSnapshotService service;
    private final SocketLogService socketLogService;
    private Integer port;
    private String remote_url;
    private String msg;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        // 소켓 채널이 활성화될 때 포트 번호 저장
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        InetSocketAddress inetSocketAddress = socketChannel.localAddress();
        port = inetSocketAddress.getPort();
        remote_url = inetSocketAddress.getHostString();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        final ByteBuf in = (ByteBuf) msg;

        int len = in.writerIndex();
        byte[] msgByte = new byte[len];
        in.readBytes(msgByte);
        try {
            String receivedMessage = new String(msgByte,"UTF-8");
            this.msg = receivedMessage;

            if(!receivedMessage.isBlank()) {
                receivedMessage = receivedMessage.replaceAll(" ", "0");
                final SocketLog sl = new SocketLog();
                esHandler(receivedMessage);

                sl.setLog(receivedMessage);
                sl.setPort(this.port);
                sl.setRemote_url(remote_url);
                socketLogService.addLog(sl);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } finally {
            in.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        repo.insert(this.msg, this.port, this.remote_url, cause.toString());
        ctx.close();
    }

    private void esHandler(String msg) {
        if(msg.length() >= 5) {
            log.info("received message : {}", msg);
            final String prodCode = msg.substring(0, 5);
            switch (prodCode) {
                case "A001S":
                case "A002S":
                case "A003S":
                case "A004S":
                case "A001Q":
                case "A001X":
                    equities_batch_data_handler(msg);
                    break;
                case "C101S":
                case "C102S":
                case "C103S":
                case "C104S":
                case "C101Q":
                case "C101X":
                case "C101G":

                    break;
                case "A301S":
                case "A301Q":
                case "A301X":
                    securities_order_filled_handler(msg);
                    break;
                case "B201X":
                case "B201Q":
                case "B201S":
                    equities_snapshot_handler(msg);
                    break;
                case "CA01S":
                case "CA01Q":
                    equity_index_indicator_handler(msg);
                    break;
            }
        }
    }

    //증권 체결
    private void securities_order_filled_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{186})")) {
            if(chunk.length() == 186) {
                secOrderFilledRepo.insert(data -> {

                    final SecOrderFilled sof = new SecOrderFilled(chunk);
                    data.putAll(sof.toMap());

                    final Product prod = service.productFromIsinCode(sof.getIsin_code());
                    if(prod != null) {
                        service.updateProductTradingList(sof.getTrading_price(), sof.getTrading_volume());
                        service.sendMessage(sof.toSocket(prod), sof.getIsin_code());
                        service.sendMessage(sof.toSocket(prod));
                    }
                });
            }
        }
    }

    //증권 Snapshot (MM/LP호가 제외)
    private void equities_snapshot_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{650})")) if(chunk.length() == 650) esRepo.insert(data -> {
            final EquitiesSnapshot es = new EquitiesSnapshot(chunk);
            data.putAll(es.toMap());

            if(data.get("es_accumulated_trading_value") != null) {
                service.updateProductCount(es.getIsin_code(), es.getFinal_ask_bid_type_code(), es.getAccumulated_trading_volume());
            }

            final Product prod = service.productFromIsinCode(es.getIsin_code());
            if(prod != null) service.sendMessage(es.toSocket(prod));
            service.sendMessage(es.toDetailsSocket(), es.getIsin_code());

            log.debug("DATA : {}", es.toMap());
        });
    }

    //증권 지수지표
    private void equity_index_indicator_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{185})")) if(chunk.length() == 185) eiiRepo.insert(data -> {
            final EquityIndexIndicator eii = new EquityIndexIndicator(chunk);
            data.putAll(eii.toMap());
        });
    }

    //증권 종목 정보
    private void equities_batch_data_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{620})")) {
            if(chunk.length() == 620) {
                final EquitiesBatchData ebd = new EquitiesBatchData(chunk);
                equitiesBatchDataRepo.insert(data -> data.putAll(ebd.toMap()));

                final Product prod = service.productFromIsinCode(ebd.getIsin_code());

                if(prod != null) {
                    service.updateProductFromBatchData(ebd);
                    service.sendMessage(ebd.toSocket(prod));
                    service.sendMessage(ebd.toSocket(prod), ebd.getIsin_code());
                }
            }
        }
    }
}
