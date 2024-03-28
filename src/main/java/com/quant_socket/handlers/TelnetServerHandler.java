package com.quant_socket.handlers;

import com.quant_socket.repos.EquitiesSnapshotRepo;
import com.quant_socket.repos.SocketLogRepo;
import com.quant_socket.services.EquitiesSnapshotService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

@Slf4j
@RequiredArgsConstructor
public class TelnetServerHandler extends ChannelInboundHandlerAdapter {
    private final SocketLogRepo repo;
    private final EquitiesSnapshotRepo esRepo;
    private final EquitiesSnapshotService service;
    private Integer port;
    private String remote_url;

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

            for(String splitMsg : receivedMessage.split("�")) {
                splitMsg += "�";
                log.info("Received message: {}", splitMsg);

                esHandler(splitMsg);
                repo.insert(splitMsg, this.port, this.remote_url);
            }

        } finally {
            in.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        repo.insert(cause.toString(), this.port, this.remote_url);
        ctx.close();
    }

    private void esHandler(String msg) {
        final String prodCode = msg.substring(0, 5);

        service.sendMessage(msg+"<br>");

        switch (prodCode) {
            case "B201S":
                esRepo.insert(data -> {
                    int index = 0;
                    data.put("es_data_category", msg.substring(index, index += 2));
                    data.put("es_info_category", msg.substring(index, index += 3));
                    data.put("es_board_id", msg.substring(index, index += 2));
                    data.put("es_session_id", msg.substring(index, index += 2));
                    data.put("es_isin_code", msg.substring(index, index += 12));
                    data.put("es_a_designated_number_for_an_issue", msg.substring(index, index += 6));
                    data.put("es_price_change_against_previous_day", msg.substring(index, index += 1));
                    data.put("es_price_change_against_the_previous_day", msg.substring(index, index += 11));
                    data.put("es_upper_limit_price", msg.substring(index, index += 11));
                    data.put("es_lower_limit_price", msg.substring(index, index += 11));
                    data.put("es_current_price", msg.substring(index, index += 11));
                    data.put("es_opening_price", msg.substring(index, index += 11));
                    data.put("es_todays_high", msg.substring(index, index += 11));
                    data.put("es_todays_low", msg.substring(index, index += 11));
                    data.put("es_accumulated_trading_volume", msg.substring(index, index += 12));
                    data.put("es_accumulated_trading_value", msg.substring(index, index += 22));
                    data.put("es_final_ask_bid_type_code", msg.substring(index, index += 1));
                    data.put("es_ask_level_1_price", msg.substring(index, index += 11));
                    data.put("es_bid_level_1_price", msg.substring(index, index += 11));
                    data.put("es_ask_level_1_volume", msg.substring(index, index += 12));
                    data.put("es_bid_level_1_volume", msg.substring(index, index += 12));
                    data.put("es_ask_level_2_price", msg.substring(index, index += 11));
                    data.put("es_bid_level_2_price", msg.substring(index, index += 11));
                    data.put("es_ask_level_2_volume", msg.substring(index, index += 12));
                    data.put("es_bid_level_2_volume", msg.substring(index, index += 12));
                    data.put("es_ask_level_3_price", msg.substring(index, index += 11));
                    data.put("es_bid_level_3_price", msg.substring(index, index += 11));
                    data.put("es_ask_level_3_volume", msg.substring(index, index += 12));
                    data.put("es_bid_level_3_volume", msg.substring(index, index += 12));
                    data.put("es_ask_level_4_price", msg.substring(index, index += 11));
                    data.put("es_bid_level_4_price", msg.substring(index, index += 11));
                    data.put("es_ask_level_4_volume", msg.substring(index, index += 12));
                    data.put("es_bid_level_4_volume", msg.substring(index, index += 12));
                    data.put("es_ask_level_5_price", msg.substring(index, index += 11));
                    data.put("es_bid_level_5_price", msg.substring(index, index += 11));
                    data.put("es_ask_level_5_volume", msg.substring(index, index += 12));
                    data.put("es_bid_level_5_volume", msg.substring(index, index += 12));
                    data.put("es_ask_level_6_price", msg.substring(index, index += 11));
                    data.put("es_bid_level_6_price", msg.substring(index, index += 11));
                    data.put("es_ask_level_6_volume", msg.substring(index, index += 12));
                    data.put("es_bid_level_6_volume", msg.substring(index, index += 12));
                    data.put("es_ask_level_7_price", msg.substring(index, index += 11));
                    data.put("es_bid_level_7_price", msg.substring(index, index += 11));
                    data.put("es_ask_level_7_volume", msg.substring(index, index += 12));
                    data.put("es_bid_level_7_volume", msg.substring(index, index += 12));
                    data.put("es_ask_level_8_price", msg.substring(index, index += 11));
                    data.put("es_bid_level_8_price", msg.substring(index, index += 11));
                    data.put("es_ask_level_8_volume", msg.substring(index, index += 12));
                    data.put("es_bid_level_8_volume", msg.substring(index, index += 12));
                    data.put("es_ask_level_9_price", msg.substring(index, index += 11));
                    data.put("es_bid_level_9_price", msg.substring(index, index += 11));
                    data.put("es_ask_level_9_volume", msg.substring(index, index += 12));
                    data.put("es_bid_level_9_volume", msg.substring(index, index += 12));
                    data.put("es_ask_level_10_price", msg.substring(index, index += 11));
                    data.put("es_bid_level_10_price", msg.substring(index, index += 11));
                    data.put("es_ask_level_10_volume", msg.substring(index, index += 12));
                    data.put("es_bid_level_10_volume", msg.substring(index, index += 12));
                    data.put("es_total_ask_volume", msg.substring(index, index += 12));
                    data.put("es_total_bid_volume", msg.substring(index, index += 12));
                    data.put("es_estimated_trading_price", msg.substring(index, index += 11));
                    data.put("es_estimated_trading_volume", msg.substring(index, index += 12));
                    data.put("es_closing_price_type_code", msg.substring(index, index += 1));
                    data.put("es_trading_halt", msg.substring(index, index += 1));
                    data.put("es_end_keyword", msg.substring(index, index + 1));
                    service.sendMessage(data);
                });
                break;
        }
    }
}
