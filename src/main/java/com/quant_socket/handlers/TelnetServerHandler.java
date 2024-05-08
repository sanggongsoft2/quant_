package com.quant_socket.handlers;

import com.quant_socket.models.Logs.*;
import com.quant_socket.models.Product;
import com.quant_socket.repos.*;
import com.quant_socket.services.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class TelnetServerHandler extends ChannelInboundHandlerAdapter {
    private final SocketLogRepo repo;
    private final EquitiesSnapshotService equitiesSnapshotService;
    private final EquityIndexIndicatorService equityIndexIndicatorService;
    private final SocketLogService socketLogService;
    private final SecuritiesOrderFilledService securitiesOrderFilledService;
    private final InvestActivitiesEODService investActivitiesEODService;
    private final EquitiesBatchDataService equitiesBatchDataService;
    private final SeqQuoteService seqQuoteService;
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
            this.msg = in.toString(StandardCharsets.UTF_8);
            log.info("received message : {}, remote_url: {}, port: {}", this.msg, this.remote_url, this.port);

            if(!this.msg.isBlank()) {
                final SocketLog sl = new SocketLog();

                sl.setLog(this.msg);
                sl.setPort(this.port);
                sl.setRemote_url(remote_url);
                socketLogService.addLog(sl);

                esHandler();
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

    private void esHandler() {
        if(msg.length() >= 5) {
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
                    investor_activities_per_an_issue_EOD_handler(msg);
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
                case "B202S":
                case "B203S":
                case "B204S":
                    equities_snapshot_handler2(msg);
                    break;
                case "CA01S":
                case "CA01Q":
                    equity_index_indicator_handler(msg);
                    break;
                case "B702S":
                case "B703S":
                case "B704S":
                    seq_quote_handler(msg);
                    break;
            }
        }
    }

    //종목별 투자자별 종가통계
    private void investor_activities_per_an_issue_EOD_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{96})")) {
            if (chunk.length() >= 96) {
                final InvestorActivitiesEOD iae = new InvestorActivitiesEOD(msg);
                investActivitiesEODService.addLog(iae);
            }
        }
    }

    //증권 체결
    private void securities_order_filled_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{186})")) if(chunk.length() >= 186) {
            final SecOrderFilled sef = new SecOrderFilled(msg);
            securitiesOrderFilledService.addLog(sef);
        }
    }

    //증권 Snapshot (MM/LP호가 제외)s
    private void equities_snapshot_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{650})")) {
            if(chunk.length() >= 650) {
                final EquitiesSnapshot es = new EquitiesSnapshot(chunk);
                equitiesSnapshotService.addLog(es);
            }
        }
    }

    private void equities_snapshot_handler2(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{900})")) {
            if(chunk.length() >= 900) {
                final EquitiesSnapshot es = new EquitiesSnapshot(chunk);
                equitiesSnapshotService.addLog(es);
            }
        }
    }

    //증권 지수지표
    private void equity_index_indicator_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{185})")) {
            if (chunk.length() >= 185) {
                final EquityIndexIndicator eii = new EquityIndexIndicator(chunk);
                equityIndexIndicatorService.addLog(eii);
            }
        }
    }

    //증권 종목 정보
    private void equities_batch_data_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{620})")) {
            if(chunk.length() >= 620) {
                final EquitiesBatchData ebd = new EquitiesBatchData(chunk);
                equitiesBatchDataService.addLog(ebd);
            }
        }
    }

    private void seq_quote_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{795})")) {
            if(chunk.length() >= 795) {
                final SeqQuote data = new SeqQuote(chunk);
                seqQuoteService.addLog(data);
            }
        }
    }
}
