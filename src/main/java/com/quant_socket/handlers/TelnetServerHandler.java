package com.quant_socket.handlers;

import com.quant_socket.models.Logs.EquitiesSnapshot;
import com.quant_socket.models.Logs.EquityIndexIndicator;
import com.quant_socket.models.Logs.SecOrderFilled;
import com.quant_socket.models.Logs.SocketLog;
import com.quant_socket.models.Product;
import com.quant_socket.repos.EquitiesSnapshotRepo;
import com.quant_socket.repos.EquityIndexIndicatorRepo;
import com.quant_socket.repos.SecOrderFilledRepo;
import com.quant_socket.repos.SocketLogRepo;
import com.quant_socket.services.EquitiesSnapshotService;
import com.quant_socket.services.SocketLogService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class TelnetServerHandler extends ChannelInboundHandlerAdapter {
    private final SocketLogRepo repo;
    private final EquitiesSnapshotRepo esRepo;
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

        try {
            String receivedMessage = in.toString(Charset.defaultCharset());
            this.msg = receivedMessage;

            for(String splitMsg : receivedMessage.split("�")) {
                if(!splitMsg.isBlank()) {
                    splitMsg = splitMsg.replaceAll(" ", "0");
                    final SocketLog sl = new SocketLog();
                    splitMsg += "�";
                    log.debug("Received message: {}", splitMsg);
                    esHandler(splitMsg);
                    sl.setLog(splitMsg);
                    sl.setPort(this.port);
                    sl.setRemote_url(remote_url);
                    socketLogService.addLog(sl);
                }
            }

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
            log.info(msg);
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
        if(msg.length() == 186) secOrderFilledRepo.insert(data -> {

            final SecOrderFilled sof = new SecOrderFilled(msg);
            data.putAll(sof.toMap());

            final Product prod = service.productFromIsinCode(sof.getIsin_code());
            if(prod != null) {
                service.updateProductTradingList(sof.getTrading_price(), sof.getTrading_volume());
                service.sendMessage(sof.toSocket(prod), sof.getIsin_code());
                service.sendMessage(sof.toSocket(prod));
            }
        });
    }

    //증권 Snapshot (MM/LP호가 제외)
    private void equities_snapshot_handler(String msg) {
        if(msg.length() == 650) esRepo.insert(data -> {
            final EquitiesSnapshot es = new EquitiesSnapshot(msg);
            data.putAll(es.toMap());

            final Product prod = service.productFromIsinCode(es.getIsin_code());

            if(prod != null) {

                if(data.get("es_accumulated_trading_value") != null) {
                    service.updateProductCount(es.getIsin_code(), es.getFinal_ask_bid_type_code(), es.getAccumulated_trading_volume());
                }

                service.sendMessage(es.toSocket(prod));
                service.sendMessage(es.toSocket(prod), es.getIsin_code());
            }

            log.debug("DATA : {}", es.toMap());
        });
    }

    //증권 종목 정보
    private void equity_index_indicator_handler(String msg) {
        if(msg.length() == 185) eiiRepo.insert(data -> {
            final EquityIndexIndicator eii = new EquityIndexIndicator(msg);
            data.putAll(eii.toMap());
        });
    }

    private void equities_batch_data_handler(String msg) {
        if(msg.length() == 620) {
            final Map<String, Object> data = new HashMap<>();
            int index = 0;
            data.put(String.valueOf(index+2), msg.substring(index, index += 2));
            data.put(String.valueOf(index+3), msg.substring(index, index += 3));
            data.put(String.valueOf(index+8), Integer.parseInt(msg.substring(index, index += 8)));
            data.put(String.valueOf(index+6), Integer.parseInt(msg.substring(index, index += 6)));
            data.put(String.valueOf(index+8), msg.substring(index, index += 8));
            data.put(String.valueOf(index+12), msg.substring(index, index += 12));
            data.put(String.valueOf(index+6), Integer.parseInt(msg.substring(index, index += 6)));
            data.put(String.valueOf(index+9), msg.substring(index, index += 9));
            data.put(String.valueOf(index+40), msg.substring(index, index += 40));
            data.put(String.valueOf(index+40), msg.substring(index, index += 40));
            data.put(String.valueOf(index+5), msg.substring(index, index += 5));
            data.put(String.valueOf(index+3), msg.substring(index, index += 3));
            data.put(String.valueOf(index+2), msg.substring(index, index += 2));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+2), msg.substring(index, index += 2));
            data.put(String.valueOf(index+2), msg.substring(index, index += 2));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+2), msg.substring(index, index += 2));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+2), msg.substring(index, index += 2));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+10), msg.substring(index, index += 10));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+11), Double.parseDouble(msg.substring(index, index += 11)));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+11), Double.parseDouble(msg.substring(index, index += 11)));
            data.put(String.valueOf(index+12), Long.parseLong(msg.substring(index, index += 12)));
            data.put(String.valueOf(index+22), Float.parseFloat(msg.substring(index, index += 22)));
            data.put(String.valueOf(index+11), Double.parseDouble(msg.substring(index, index += 11)));
            data.put(String.valueOf(index+11), Double.parseDouble(msg.substring(index, index += 11)));
            data.put(String.valueOf(index+11), Double.parseDouble(msg.substring(index, index += 11)));
            data.put(String.valueOf(index+11), Double.parseDouble(msg.substring(index, index += 11)));
            data.put(String.valueOf(index+11), Double.parseDouble(msg.substring(index, index += 11)));
            data.put(String.valueOf(index+8), msg.substring(index, index += 8));
            data.put(String.valueOf(index+16), Long.parseLong(msg.substring(index, index += 16)));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+8), msg.substring(index, index += 8));
            data.put(String.valueOf(index+8), msg.substring(index, index += 8));
            data.put(String.valueOf(index+8), msg.substring(index, index += 8));
            data.put(String.valueOf(index+8), msg.substring(index, index += 8));
            data.put(String.valueOf(index+13), Double.parseDouble(msg.substring(index, index += 13)));
            data.put(String.valueOf(index+22), Float.parseFloat(msg.substring(index, index += 22)));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+5), Integer.parseInt(msg.substring(index, index += 5)));
            data.put(String.valueOf(index+5), Integer.parseInt(msg.substring(index, index += 5)));
            data.put(String.valueOf(index+5), Integer.parseInt(msg.substring(index, index += 5)));
            data.put(String.valueOf(index+5), Integer.parseInt(msg.substring(index, index += 5)));
            data.put(String.valueOf(index+5), Integer.parseInt(msg.substring(index, index += 5)));
            data.put(String.valueOf(index+2), msg.substring(index, index += 2));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+11), Double.parseDouble(msg.substring(index, index += 11)));
            data.put(String.valueOf(index+11), Double.parseDouble(msg.substring(index, index += 11)));
            data.put(String.valueOf(index+11), Double.parseDouble(msg.substring(index, index += 11)));
            data.put(String.valueOf(index+11), Long.parseLong(msg.substring(index, index += 11)));
            data.put(String.valueOf(index+11), Long.parseLong(msg.substring(index, index += 11)));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+12), msg.substring(index, index += 12));
            data.put(String.valueOf(index+3), msg.substring(index, index += 3));
            data.put(String.valueOf(index+3), msg.substring(index, index += 3));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+13), Double.parseDouble(msg.substring(index, index += 13)));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+13), Double.parseDouble(msg.substring(index, index += 13)));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+8), msg.substring(index, index += 8));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+8), msg.substring(index, index += 8));
            data.put(String.valueOf(index+2), msg.substring(index, index += 2));
            data.put(String.valueOf(index+8), msg.substring(index, index += 8));
            data.put(String.valueOf(index+8), msg.substring(index, index += 8));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+2), msg.substring(index, index += 2));
            data.put(String.valueOf(index+6), msg.substring(index, index += 6));
            data.put(String.valueOf(index+3), msg.substring(index, index += 3));
            data.put(String.valueOf(index+2), msg.substring(index, index += 2));
            data.put(String.valueOf(index+2), msg.substring(index, index += 2));
            data.put(String.valueOf(index+6), msg.substring(index, index += 6));
            data.put(String.valueOf(index+6), msg.substring(index, index += 6));
            data.put(String.valueOf(index+5), msg.substring(index, index += 5));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+23), Float.parseFloat(msg.substring(index, index += 23)));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index += 1));
            data.put(String.valueOf(index+1), msg.substring(index, index + 1));

            final String isinCode = (String) data.get("39");

            final Map<String, Object> response = new HashMap<>();

            response.put("isin_code", isinCode);

            service.sendMessage(response);
            service.sendMessage(response, isinCode);
        }
    }
}
