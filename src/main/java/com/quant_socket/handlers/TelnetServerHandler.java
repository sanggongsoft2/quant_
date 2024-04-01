package com.quant_socket.handlers;

import com.quant_socket.models.Logs.SocketLog;
import com.quant_socket.models.Product;
import com.quant_socket.repos.EquitiesSnapshotRepo;
import com.quant_socket.repos.EquityIndexIndicatorRepo;
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
            final String prodCode = msg.substring(0, 5);
            switch (prodCode) {
                case "B201X":
                case "B201Q":
                case "B201S":
                    try {
                        if(msg.length() == 650) esRepo.insert(data -> {
                            int index = 0;
                            data.put("es_data_category", msg.substring(index, index += 2));
                            data.put("es_info_category", msg.substring(index, index += 3));
                            data.put("es_board_id", msg.substring(index, index += 2));
                            data.put("es_session_id", msg.substring(index, index += 2));
                            data.put("es_isin_code", msg.substring(index, index += 12));
                            data.put("es_a_designated_number_for_an_issue", Integer.parseInt(msg.substring(index, index += 6)));
                            data.put("es_price_change_against_previous_day", msg.substring(index, index += 1));
                            data.put("es_price_change_against_the_previous_day", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_upper_limit_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_lower_limit_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_current_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_opening_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_todays_high", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_todays_low", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_accumulated_trading_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_accumulated_trading_value", Float.parseFloat(msg.substring(index, index += 22)));
                            data.put("es_final_ask_bid_type_code", msg.substring(index, index += 1));
                            data.put("es_ask_level_1_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_bid_level_1_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_ask_level_1_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_bid_level_1_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_ask_level_2_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_bid_level_2_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_ask_level_2_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_bid_level_2_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_ask_level_3_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_bid_level_3_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_ask_level_3_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_bid_level_3_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_ask_level_4_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_bid_level_4_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_ask_level_4_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_bid_level_4_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_ask_level_5_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_bid_level_5_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_ask_level_5_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_bid_level_5_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_ask_level_6_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_bid_level_6_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_ask_level_6_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_bid_level_6_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_ask_level_7_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_bid_level_7_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_ask_level_7_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_bid_level_7_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_ask_level_8_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_bid_level_8_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_ask_level_8_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_bid_level_8_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_ask_level_9_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_bid_level_9_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_ask_level_9_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_bid_level_9_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_ask_level_10_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_bid_level_10_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_ask_level_10_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_bid_level_10_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_total_ask_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_total_bid_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_estimated_trading_price", Double.parseDouble(msg.substring(index, index += 11)));
                            data.put("es_estimated_trading_volume", Long.parseLong(msg.substring(index, index += 12)));
                            data.put("es_closing_price_type_code", msg.substring(index, index += 1));
                            data.put("es_trading_halt", msg.substring(index, index += 1));
                            data.put("es_end_keyword", msg.substring(index, index + 1));
                            final Map<String, Object> response = new HashMap<>();
                            final String isinCode = (String) data.get("es_isin_code");
                            final Product prod = service.productFromIsinCode(isinCode);
                            if(prod != null) {
                                //0: 초기값, 1: 상한, 2: 상승, 3: 보합, 4: 하한, 5: 하락
                                response.put("compare_yes_type", data.get("es_price_change_against_previous_day"));
                                //1
                                response.put("name_kr_abbr", prod.getName_kr_abbr());
                                //2
                                response.put("class", prod.getGubun());
                                //3
                                response.put("short_code", prod.getShort_code());
                                //4
                                response.put("current_price", data.get("es_current_price"));
                                //5
                                response.put("compare_price", data.get("es_price_change_against_the_previous_day"));
                                //6
                                response.put("compare_price_percent", data.get("es_price_change_against_the_previous_day"));
                                //7
                                response.put("trading_count", data.get("es_accumulated_trading_volume"));
                                //8
                                response.put("yes_trading_count", data.get("es_accumulated_trading_volume"));
                                //9,10
                                response.put("ask_level_1_price", data.get("es_ask_level_1_price"));
                                response.put("bid_level_1_price", data.get("es_bid_level_1_price"));
                                response.put("ask_level_1_volume", data.get("es_ask_level_1_volume"));
                                response.put("bid_level_1_volume", data.get("es_bid_level_1_volume"));
                                response.put("ask_level_2_price", data.get("es_ask_level_2_price"));
                                response.put("bid_level_2_price", data.get("es_bid_level_2_price"));
                                response.put("ask_level_2_volume", data.get("es_ask_level_2_volume"));
                                response.put("bid_level_2_volume", data.get("es_bid_level_2_volume"));
                                response.put("ask_level_3_price", data.get("es_ask_level_3_price"));
                                response.put("bid_level_3_price", data.get("es_bid_level_3_price"));
                                response.put("ask_level_3_volume", data.get("es_ask_level_3_volume"));
                                response.put("bid_level_3_volume", data.get("es_bid_level_3_volume"));
                                response.put("ask_level_4_price", data.get("es_ask_level_4_price"));
                                response.put("bid_level_4_price", data.get("es_bid_level_4_price"));
                                response.put("ask_level_4_volume", data.get("es_ask_level_4_volume"));
                                response.put("bid_level_4_volume", data.get("es_bid_level_4_volume"));
                                response.put("ask_level_5_price", data.get("es_ask_level_5_price"));
                                response.put("bid_level_5_price", data.get("es_bid_level_5_price"));
                                response.put("ask_level_5_volume", data.get("es_ask_level_5_volume"));
                                response.put("bid_level_5_volume", data.get("es_bid_level_5_volume"));
                                response.put("ask_level_6_price", data.get("es_ask_level_6_price"));
                                response.put("bid_level_6_price", data.get("es_bid_level_6_price"));
                                response.put("ask_level_6_volume", data.get("es_ask_level_6_volume"));
                                response.put("bid_level_6_volume", data.get("es_bid_level_6_volume"));
                                response.put("ask_level_7_price", data.get("es_ask_level_7_price"));
                                response.put("bid_level_7_price", data.get("es_bid_level_7_price"));
                                response.put("ask_level_7_volume", data.get("es_ask_level_7_volume"));
                                response.put("bid_level_7_volume", data.get("es_bid_level_7_volume"));
                                response.put("ask_level_8_price", data.get("es_ask_level_8_price"));
                                response.put("bid_level_8_price", data.get("es_bid_level_8_price"));
                                response.put("ask_level_8_volume", data.get("es_ask_level_8_volume"));
                                response.put("bid_level_8_volume", data.get("es_bid_level_8_volume"));
                                response.put("ask_level_9_price", data.get("es_ask_level_9_price"));
                                response.put("bid_level_9_price", data.get("es_bid_level_9_price"));
                                response.put("ask_level_9_volume", data.get("es_ask_level_9_volume"));
                                response.put("bid_level_9_volume", data.get("es_bid_level_9_volume"));
                                response.put("ask_level_10_price", data.get("es_ask_level_10_price"));
                                response.put("bid_level_10_price", data.get("es_bid_level_10_price"));
                                response.put("ask_level_10_volume", data.get("es_ask_level_10_volume"));
                                response.put("bid_level_10_volume", data.get("es_bid_level_10_volume"));
                                //11
                                //12
                                //13

                                //16
                                response.put("face_value", prod.getFace_value());
                                //20
                                response.put("bid_total_count", data.get("es_total_bid_volume"));
                                //23
                                response.put("ask_total_count", data.get("es_total_ask_volume"));

                                service.sendMessage(response);
                                service.sendMessage(response, isinCode);
                            }
                        });
                    } catch (StringIndexOutOfBoundsException ignore) {

                    }
                    break;
                case "CA01S":
                case "CA01Q":
                    if(msg.length() == 185) eiiRepo.insert(data -> {
                        int index = 0;
                        data.put("eii_data_category", msg.substring(index, index += 2));
                        data.put("eii_info_category", msg.substring(index, index += 3));
                        data.put("eii_message_seq_number", msg.substring(index, index += 8));
                        data.put("eii_isin_code", msg.substring(index, index += 12));
                        data.put("eii_a_designated_number_for_an_issue", msg.substring(index, index += 6));
                        data.put("eii_sec_group_id", msg.substring(index, index += 2));
                        data.put("eii_eps_calculation", msg.substring(index, index += 1));
                        data.put("eii_eps", msg.substring(index, index += 22));
                        data.put("eii_loss_category", msg.substring(index, index += 1));
                        data.put("eii_per", msg.substring(index, index += 13));
                        data.put("eii_bps_calculation", msg.substring(index, index += 1));
                        data.put("eii_bps", msg.substring(index, index += 22));
                        data.put("eii_pbr", msg.substring(index, index += 13));
                        data.put("eii_dps_calculation", msg.substring(index, index += 1));
                        data.put("eii_dps", msg.substring(index, index += 22));
                        data.put("eii_dividend_yield", msg.substring(index, index += 13));
                        data.put("eii_market_capitalization", msg.substring(index, index += 1));
                        data.put("eii_manufacturing", msg.substring(index, index += 1));
                        data.put("eii_index_classification_level1", msg.substring(index, index += 6));
                        data.put("eii_index_classification_level2", msg.substring(index, index += 6));
                        data.put("eii_index_classification_level3", msg.substring(index, index += 6));
                        data.put("eii_kospi200_sector_code1", msg.substring(index, index += 1));
                        data.put("eii_kospi200_sector_code2", msg.substring(index, index += 1));
                        data.put("eii_is_kospi", msg.substring(index, index += 1));
                        data.put("eii_is_kosdaq", msg.substring(index, index += 1));
                        data.put("eii_is_kospi100", msg.substring(index, index += 1));
                        data.put("eii_is_kospi50", msg.substring(index, index += 1));
                        data.put("eii_kosdaq150", msg.substring(index, index += 1));
                        data.put("eii_krx100", msg.substring(index, index += 1));
                        data.put("eii_krx300", msg.substring(index, index += 1));
                        data.put("eii_kospi200_high_dividend_yield_index", msg.substring(index, index += 1));
                        data.put("eii_krx_bbig_knew_deal_index", msg.substring(index, index += 1));
                        data.put("eii_krx_second_battery_knew_deal_index", msg.substring(index, index += 1));
                        data.put("eii_krx_bio_knew_deal_index", msg.substring(index, index += 1));
                        data.put("eii_filler", msg.substring(index, index += 9));
                        data.put("eii_end_keyword", msg.substring(index, index + 1));
                    });
                    break;
            }
        }
    }
}
