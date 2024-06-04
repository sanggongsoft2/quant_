package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.Product;
import com.quant_socket.models.SG_model;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@SG_table(name = "equities_snapshot")
@Getter
@Slf4j
@ToString
public class EquitiesSnapshot extends SG_model{

    @SG_idx
    @SG_column(dbField = "es_idx")
    private Long idx;
    @SG_column(dbField = "es_data_category")
    private String data_category;
    @SG_column(dbField = "es_info_category")
    private String info_category;
    @SG_column(dbField = "es_board_id")
    private String board_id;
    @SG_column(dbField = "es_session_id")
    private String session_id;
    @SG_column(dbField = "es_isin_code")
    private String isin_code;
    @SG_column(dbField = "es_a_designated_number_for_an_issue")
    private Integer a_designated_number_for_an_issue;
    @SG_column(dbField = "es_price_change_against_previous_day")
    private String price_change_against_previous_day;
    @SG_column(dbField = "es_price_change_against_the_previous_day")
    private Double price_change_against_the_previous_day;
    @SG_column(dbField = "es_upper_limit_price")
    private Double upper_limit_price;
    @SG_column(dbField = "es_lower_limit_price")
    private Double lower_limit_price;
    @SG_column(dbField = "es_current_price")
    private Double current_price;
    @SG_column(dbField = "es_opening_price")
    private Double opening_price;
    @SG_column(dbField = "es_todays_high")
    private Double todays_high;
    @SG_column(dbField = "es_todays_low")
    private Double todays_low;
    @SG_column(dbField = "es_accumulated_trading_volume")
    private Long accumulated_trading_volume;
    @SG_column(dbField = "es_accumulated_trading_value")
    private Float accumulated_trading_value;
    @SG_column(dbField = "es_final_ask_bid_type_code")
    private String final_ask_bid_type_code;

    @SG_column(dbField = "es_ask_level_1_price")
    private Double ask_level_1_price;
    @SG_column(dbField = "es_bid_level_1_price")
    private Double bid_level_1_price;
    @SG_column(dbField = "es_ask_level_1_volume")
    private Long ask_level_1_volume;
    @SG_column(dbField = "es_bid_level_1_volume")
    private Long bid_level_1_volume;
    @SG_column(dbField = "es_lp_ask_level_1_volume")
    private Long es_lp_ask_level_1_volume;
    @SG_column(dbField = "es_lp_bid_level_1_volume")
    private Long es_lp_bid_level_1_volume;

    @SG_column(dbField = "es_ask_level_2_price")
    private Double ask_level_2_price;
    @SG_column(dbField = "es_bid_level_2_price")
    private Double bid_level_2_price;
    @SG_column(dbField = "es_ask_level_2_volume")
    private Long ask_level_2_volume;
    @SG_column(dbField = "es_bid_level_2_volume")
    private Long bid_level_2_volume;
    @SG_column(dbField = "es_lp_bid_level_2_volume")
    private Long es_lp_bid_level_2_volume;
    @SG_column(dbField = "es_lp_ask_level_2_volume")
    private Long es_lp_ask_level_2_volume;

    @SG_column(dbField = "es_ask_level_3_price")
    private Double ask_level_3_price;
    @SG_column(dbField = "es_bid_level_3_price")
    private Double bid_level_3_price;
    @SG_column(dbField = "es_ask_level_3_volume")
    private Long ask_level_3_volume;
    @SG_column(dbField = "es_bid_level_3_volume")
    private Long bid_level_3_volume;
    @SG_column(dbField = "es_lp_ask_level_3_volume")
    private Long es_lp_ask_level_3_volume;
    @SG_column(dbField = "es_lp_bid_level_3_volume")
    private Long es_lp_bid_level_3_volume;

    @SG_column(dbField = "es_ask_level_4_price")
    private Double ask_level_4_price;
    @SG_column(dbField = "es_bid_level_4_price")
    private Double bid_level_4_price;
    @SG_column(dbField = "es_ask_level_4_volume")
    private Long ask_level_4_volume;
    @SG_column(dbField = "es_bid_level_4_volume")
    private Long bid_level_4_volume;
    @SG_column(dbField = "es_lp_ask_level_4_volume")
    private Long es_lp_ask_level_4_volume;
    @SG_column(dbField = "es_lp_bid_level_4_volume")
    private Long es_lp_bid_level_4_volume;

    @SG_column(dbField = "es_ask_level_5_price")
    private Double ask_level_5_price;
    @SG_column(dbField = "es_bid_level_5_price")
    private Double bid_level_5_price;
    @SG_column(dbField = "es_ask_level_5_volume")
    private Long ask_level_5_volume;
    @SG_column(dbField = "es_bid_level_5_volume")
    private Long bid_level_5_volume;
    @SG_column(dbField = "es_lp_ask_level_5_volume")
    private Long es_lp_ask_level_5_volume;
    @SG_column(dbField = "es_lp_bid_level_5_volume")
    private Long es_lp_bid_level_5_volume;

    @SG_column(dbField = "es_ask_level_6_price")
    private Double ask_level_6_price;
    @SG_column(dbField = "es_bid_level_6_price")
    private Double bid_level_6_price;
    @SG_column(dbField = "es_ask_level_6_volume")
    private Long ask_level_6_volume;
    @SG_column(dbField = "es_bid_level_6_volume")
    private Long bid_level_6_volume;
    @SG_column(dbField = "es_lp_ask_level_6_volume")
    private Long es_lp_ask_level_6_volume;
    @SG_column(dbField = "es_lp_bid_level_6_volume")
    private Long es_lp_bid_level_6_volume;

    @SG_column(dbField = "es_ask_level_7_price")
    private Double ask_level_7_price;
    @SG_column(dbField = "es_bid_level_7_price")
    private Double bid_level_7_price;
    @SG_column(dbField = "es_ask_level_7_volume")
    private Long ask_level_7_volume;
    @SG_column(dbField = "es_bid_level_7_volume")
    private Long bid_level_7_volume;
    @SG_column(dbField = "es_lp_ask_level_7_volume")
    private Long es_lp_ask_level_7_volume;
    @SG_column(dbField = "es_lp_bid_level_7_volume")
    private Long es_lp_bid_level_7_volume;

    @SG_column(dbField = "es_ask_level_8_price")
    private Double ask_level_8_price;
    @SG_column(dbField = "es_bid_level_8_price")
    private Double bid_level_8_price;
    @SG_column(dbField = "es_ask_level_8_volume")
    private Long ask_level_8_volume;
    @SG_column(dbField = "es_bid_level_8_volume")
    private Long bid_level_8_volume;
    @SG_column(dbField = "es_lp_ask_level_8_volume")
    private Long es_lp_ask_level_8_volume;
    @SG_column(dbField = "es_lp_bid_level_8_volume")
    private Long es_lp_bid_level_8_volume;

    @SG_column(dbField = "es_ask_level_9_price")
    private Double ask_level_9_price ;
    @SG_column(dbField = "es_bid_level_9_price")
    private Double bid_level_9_price ;
    @SG_column(dbField = "es_ask_level_9_volume")
    private Long ask_level_9_volume;
    @SG_column(dbField = "es_bid_level_9_volume")
    private Long bid_level_9_volume;
    @SG_column(dbField = "es_lp_ask_level_9_volume")
    private Long es_lp_ask_level_9_volume;
    @SG_column(dbField = "es_lp_bid_level_9_volume")
    private Long es_lp_bid_level_9_volume;

    @SG_column(dbField = "es_ask_level_10_price")
    private Double ask_level_10_price ;
    @SG_column(dbField = "es_bid_level_10_price")
    private Double bid_level_10_price ;
    @SG_column(dbField = "es_ask_level_10_volume")
    private Long ask_level_10_volume;
    @SG_column(dbField = "es_bid_level_10_volume")
    private Long bid_level_10_volume;
    @SG_column(dbField = "es_lp_ask_level_10_volume")
    private Long es_lp_ask_level_10_volume;
    @SG_column(dbField = "es_lp_bid_level_10_volume")
    private Long es_lp_bid_level_10_volume;

    @SG_column(dbField = "es_total_ask_volume")
    private Long total_ask_volume;
    @SG_column(dbField = "es_total_bid_volume")
    private Long total_bid_volume;
    @SG_column(dbField = "es_estimated_trading_price")
    private Double estimated_trading_price;
    @SG_column(dbField = "es_estimated_trading_volume")
    private Long estimated_trading_volume;
    @SG_column(dbField = "es_closing_price_type_code")
    private String closing_price_type_code;
    @SG_column(dbField = "es_trading_halt")
    private String trading_halt;
    @SG_column(dbField = "es_is_fast_close")
    private boolean is_fast_close;
    @SG_column(dbField = "es_fast_close_time")
    private String fast_close_time;
    @SG_column(dbField = "es_end_keyword")
    private String end_keyword;

    @SG_column(dbField = "es_crdt")
    private Timestamp createdAt;

    public EquitiesSnapshot(String msg) throws NumberFormatException {

        data_category = msg.substring(0, 2);
        info_category = msg.substring(2, 5);
        board_id = msg.substring(5, 7);
        session_id = msg.substring(7, 9);
        isin_code = msg.substring(9, 21);
        if(!msg.substring(21, 27).isBlank()) a_designated_number_for_an_issue = Integer.parseInt(msg.substring(21, 27));
        price_change_against_previous_day = msg.substring(27, 28);
        if(!msg.substring(28, 39).isBlank()) price_change_against_the_previous_day = Double.parseDouble(msg.substring(28, 39));
        if(!msg.substring(39, 50).isBlank()) upper_limit_price = Double.parseDouble(msg.substring(39, 50));
        if(!msg.substring(50, 61).isBlank()) lower_limit_price = Double.parseDouble(msg.substring(50, 61));
        if(!msg.substring(61, 72).isBlank()) current_price = Double.parseDouble(msg.substring(61, 72));
        if(!msg.substring(72, 83).isBlank()) opening_price = Double.parseDouble(msg.substring(72, 83));
        if(!msg.substring(83, 94).isBlank()) todays_high = Double.parseDouble(msg.substring(83, 94));
        if(!msg.substring(94, 105).isBlank()) todays_low = Double.parseDouble(msg.substring(94, 105));
        if(!msg.substring(105, 117).isBlank()) accumulated_trading_volume = Long.parseLong(msg.substring(105, 117));
        if(!msg.substring(117, 139).isBlank()) accumulated_trading_value = Float.parseFloat(msg.substring(117, 139));
        final_ask_bid_type_code = msg.substring(139, 140);

        if(msg.length() >= 900) {
            if(!msg.substring(140, 151).isBlank()) ask_level_1_price = Double.parseDouble(msg.substring(140, 151));
            if(!msg.substring(151, 162).isBlank()) bid_level_1_price = Double.parseDouble(msg.substring(151, 162));
            if(!msg.substring(162, 174).isBlank()) ask_level_1_volume = Long.parseLong(msg.substring(162, 174));
            if(!msg.substring(174, 186).isBlank()) bid_level_1_volume = Long.parseLong(msg.substring(174, 186));
            if(!msg.substring(186, 198).isBlank()) es_lp_ask_level_1_volume = Long.parseLong(msg.substring(186, 198));
            if(!msg.substring(198, 210).isBlank()) es_lp_bid_level_1_volume = Long.parseLong(msg.substring(198, 210));

            if(!msg.substring(210, 221).isBlank()) ask_level_2_price = Double.parseDouble(msg.substring(210, 221));
            if(!msg.substring(221, 232).isBlank()) bid_level_2_price = Double.parseDouble(msg.substring(221, 232));
            if(!msg.substring(232, 244).isBlank()) ask_level_2_volume = Long.parseLong(msg.substring(232, 244));
            if(!msg.substring(244, 256).isBlank()) bid_level_2_volume = Long.parseLong(msg.substring(244, 256));
            if(!msg.substring(256, 268).isBlank()) es_lp_ask_level_2_volume = Long.parseLong(msg.substring(256, 268));
            if(!msg.substring(268, 280).isBlank()) es_lp_bid_level_2_volume = Long.parseLong(msg.substring(268, 280));

            if(!msg.substring(280, 291).isBlank()) ask_level_3_price = Double.parseDouble(msg.substring(280, 291));
            if(!msg.substring(291, 302).isBlank()) bid_level_3_price = Double.parseDouble(msg.substring(291, 302));
            if(!msg.substring(302, 314).isBlank()) ask_level_3_volume = Long.parseLong(msg.substring(302, 314));
            if(!msg.substring(314, 326).isBlank()) bid_level_3_volume = Long.parseLong(msg.substring(314, 326));
            if(!msg.substring(326, 338).isBlank()) es_lp_ask_level_3_volume = Long.parseLong(msg.substring(326, 338));
            if(!msg.substring(338, 350).isBlank()) es_lp_bid_level_3_volume = Long.parseLong(msg.substring(338, 350));

            if(!msg.substring(350, 361).isBlank()) ask_level_4_price = Double.parseDouble(msg.substring(350, 361));
            if(!msg.substring(361, 372).isBlank()) bid_level_4_price = Double.parseDouble(msg.substring(361, 372));
            if(!msg.substring(372, 384).isBlank()) ask_level_4_volume = Long.parseLong(msg.substring(372, 384));
            if(!msg.substring(384, 396).isBlank()) bid_level_4_volume = Long.parseLong(msg.substring(384, 396));
            if(!msg.substring(396, 408).isBlank()) es_lp_ask_level_4_volume = Long.parseLong(msg.substring(396, 408));
            if(!msg.substring(408, 420).isBlank()) es_lp_bid_level_4_volume = Long.parseLong(msg.substring(408, 420));

            if(!msg.substring(420, 431).isBlank()) ask_level_5_price = Double.parseDouble(msg.substring(420, 431));
            if(!msg.substring(431, 442).isBlank()) bid_level_5_price = Double.parseDouble(msg.substring(431, 442));
            if(!msg.substring(442, 454).isBlank()) ask_level_5_volume = Long.parseLong(msg.substring(442, 454));
            if(!msg.substring(454, 466).isBlank()) bid_level_5_volume = Long.parseLong(msg.substring(454, 466));
            if(!msg.substring(466, 478).isBlank()) es_lp_ask_level_5_volume = Long.parseLong(msg.substring(466, 478));
            if(!msg.substring(478, 490).isBlank()) es_lp_bid_level_5_volume = Long.parseLong(msg.substring(478, 490));

            if(!msg.substring(490, 501).isBlank()) ask_level_6_price = Double.parseDouble(msg.substring(490, 501));
            if(!msg.substring(501, 512).isBlank()) bid_level_6_price = Double.parseDouble(msg.substring(501, 512));
            if(!msg.substring(512, 524).isBlank()) ask_level_6_volume = Long.parseLong(msg.substring(512, 524));
            if(!msg.substring(524, 536).isBlank()) bid_level_6_volume = Long.parseLong(msg.substring(524, 536));
            if(!msg.substring(536, 548).isBlank()) es_lp_ask_level_6_volume = Long.parseLong(msg.substring(536, 548));
            if(!msg.substring(548, 560).isBlank()) es_lp_bid_level_6_volume = Long.parseLong(msg.substring(548, 560));

            if(!msg.substring(560, 571).isBlank()) ask_level_7_price = Double.parseDouble(msg.substring(560, 571));
            if(!msg.substring(571, 582).isBlank()) bid_level_7_price = Double.parseDouble(msg.substring(571, 582));
            if(!msg.substring(582, 594).isBlank()) ask_level_7_volume = Long.parseLong(msg.substring(582, 594));
            if(!msg.substring(594, 606).isBlank()) bid_level_7_volume = Long.parseLong(msg.substring(594, 606));
            if(!msg.substring(606, 618).isBlank()) es_lp_ask_level_7_volume = Long.parseLong(msg.substring(606, 618));
            if(!msg.substring(618, 630).isBlank()) es_lp_bid_level_7_volume = Long.parseLong(msg.substring(618, 630));

            if(!msg.substring(630, 641).isBlank()) ask_level_8_price = Double.parseDouble(msg.substring(630, 641));
            if(!msg.substring(641, 652).isBlank()) bid_level_8_price = Double.parseDouble(msg.substring(641, 652));
            if(!msg.substring(652, 664).isBlank()) ask_level_8_volume = Long.parseLong(msg.substring(652, 664));
            if(!msg.substring(664, 676).isBlank()) bid_level_8_volume = Long.parseLong(msg.substring(664, 676));
            if(!msg.substring(676, 688).isBlank()) es_lp_ask_level_8_volume = Long.parseLong(msg.substring(676, 688));
            if(!msg.substring(688, 700).isBlank()) es_lp_bid_level_8_volume = Long.parseLong(msg.substring(688, 700));

            if(!msg.substring(700, 711).isBlank()) ask_level_9_price = Double.parseDouble(msg.substring(700, 711));
            if(!msg.substring(711, 722).isBlank()) bid_level_9_price = Double.parseDouble(msg.substring(711, 722));
            if(!msg.substring(722, 734).isBlank()) ask_level_9_volume = Long.parseLong(msg.substring(722, 734));
            if(!msg.substring(734, 746).isBlank()) bid_level_9_volume = Long.parseLong(msg.substring(734, 746));
            if(!msg.substring(746, 758).isBlank()) es_lp_ask_level_9_volume = Long.parseLong(msg.substring(746, 758));
            if(!msg.substring(758, 770).isBlank()) es_lp_bid_level_9_volume = Long.parseLong(msg.substring(758, 770));

            if(!msg.substring(770, 781).isBlank()) ask_level_10_price = Double.parseDouble(msg.substring(770, 781));
            if(!msg.substring(781, 792).isBlank()) bid_level_10_price = Double.parseDouble(msg.substring(781, 792));
            if(!msg.substring(792, 804).isBlank()) ask_level_10_volume = Long.parseLong(msg.substring(792, 804));
            if(!msg.substring(804, 816).isBlank()) bid_level_10_volume = Long.parseLong(msg.substring(804, 816));
            if(!msg.substring(816, 828).isBlank()) es_lp_ask_level_10_volume = Long.parseLong(msg.substring(816, 828));
            if(!msg.substring(828, 840).isBlank()) es_lp_bid_level_10_volume = Long.parseLong(msg.substring(828, 840));

            if(!msg.substring(828, 840).isBlank()) total_ask_volume = Long.parseLong(msg.substring(840, 852));
            if(!msg.substring(840, 852).isBlank()) total_bid_volume = Long.parseLong(msg.substring(852, 864));
            if(!msg.substring(864, 875).isBlank()) estimated_trading_price = Double.parseDouble(msg.substring(864, 875));
            if(!msg.substring(875, 887).isBlank()) estimated_trading_volume = Long.parseLong(msg.substring(875, 887));
            closing_price_type_code = msg.substring(887, 888);
            if(!msg.substring(888, 889).isBlank()) trading_halt = msg.substring(888, 889);
            is_fast_close = msg.charAt(889) == 'Y';
            if(!msg.substring(890, 899).isBlank()) fast_close_time = msg.substring(890, 899);
            end_keyword = msg.substring(899, 900);
        } else if(msg.length() >= 650) {
            if(!msg.substring(140, 151).isBlank()) ask_level_1_price = Double.parseDouble(msg.substring(140, 151));
            if(!msg.substring(151, 162).isBlank()) bid_level_1_price = Double.parseDouble(msg.substring(151, 162));
            if(!msg.substring(162, 174).isBlank()) ask_level_1_volume = Long.parseLong(msg.substring(162, 174));
            if(!msg.substring(174, 186).isBlank()) bid_level_1_volume = Long.parseLong(msg.substring(174, 186));
            if(!msg.substring(186, 197).isBlank()) ask_level_2_price = Double.parseDouble(msg.substring(186, 197));
            if(!msg.substring(197, 208).isBlank()) bid_level_2_price = Double.parseDouble(msg.substring(197, 208));
            if(!msg.substring(208, 220).isBlank()) ask_level_2_volume = Long.parseLong(msg.substring(208, 220));
            if(!msg.substring(220, 232).isBlank()) bid_level_2_volume = Long.parseLong(msg.substring(220, 232));
            if(!msg.substring(232, 243).isBlank()) ask_level_3_price = Double.parseDouble(msg.substring(232, 243));
            if(!msg.substring(243, 254).isBlank()) bid_level_3_price = Double.parseDouble(msg.substring(243, 254));
            if(!msg.substring(254, 266).isBlank()) ask_level_3_volume = Long.parseLong(msg.substring(254, 266));
            if(!msg.substring(266, 278).isBlank()) bid_level_3_volume = Long.parseLong(msg.substring(266, 278));
            if(!msg.substring(278, 289).isBlank()) ask_level_4_price = Double.parseDouble(msg.substring(278, 289));
            if(!msg.substring(289, 300).isBlank()) bid_level_4_price = Double.parseDouble(msg.substring(289, 300));
            if(!msg.substring(300, 312).isBlank()) ask_level_4_volume = Long.parseLong(msg.substring(300, 312));
            if(!msg.substring(312, 324).isBlank()) bid_level_4_volume = Long.parseLong(msg.substring(312, 324));
            if(!msg.substring(324, 335).isBlank()) ask_level_5_price = Double.parseDouble(msg.substring(324, 335));
            if(!msg.substring(335, 346).isBlank()) bid_level_5_price = Double.parseDouble(msg.substring(335, 346));
            if(!msg.substring(346, 358).isBlank()) ask_level_5_volume = Long.parseLong(msg.substring(346, 358));
            if(!msg.substring(358, 370).isBlank()) bid_level_5_volume = Long.parseLong(msg.substring(358, 370));
            if(!msg.substring(370, 381).isBlank()) ask_level_6_price = Double.parseDouble(msg.substring(370, 381));
            if(!msg.substring(381, 392).isBlank()) bid_level_6_price = Double.parseDouble(msg.substring(381, 392));
            if(!msg.substring(392, 404).isBlank()) ask_level_6_volume = Long.parseLong(msg.substring(392, 404));
            if(!msg.substring(404, 416).isBlank()) bid_level_6_volume = Long.parseLong(msg.substring(404, 416));
            if(!msg.substring(416, 427).isBlank()) ask_level_7_price = Double.parseDouble(msg.substring(416, 427));
            if(!msg.substring(427, 438).isBlank()) bid_level_7_price = Double.parseDouble(msg.substring(427, 438));
            if(!msg.substring(438, 450).isBlank()) ask_level_7_volume = Long.parseLong(msg.substring(438, 450));
            if(!msg.substring(450, 462).isBlank()) bid_level_7_volume = Long.parseLong(msg.substring(450, 462));
            if(!msg.substring(462, 473).isBlank()) ask_level_8_price = Double.parseDouble(msg.substring(462, 473));
            if(!msg.substring(473, 484).isBlank()) bid_level_8_price = Double.parseDouble(msg.substring(473, 484));
            if(!msg.substring(484, 496).isBlank()) ask_level_8_volume = Long.parseLong(msg.substring(484, 496));
            if(!msg.substring(496, 508).isBlank()) bid_level_8_volume = Long.parseLong(msg.substring(496, 508));
            if(!msg.substring(508, 519).isBlank()) ask_level_9_price = Double.parseDouble(msg.substring(508, 519));
            if(!msg.substring(519, 530).isBlank()) bid_level_9_price = Double.parseDouble(msg.substring(519, 530));
            if(!msg.substring(530, 542).isBlank()) ask_level_9_volume = Long.parseLong(msg.substring(530, 542));
            if(!msg.substring(542, 554).isBlank()) bid_level_9_volume = Long.parseLong(msg.substring(542, 554));
            if(!msg.substring(554, 565).isBlank()) ask_level_10_price = Double.parseDouble(msg.substring(554, 565));
            if(!msg.substring(565, 576).isBlank()) bid_level_10_price = Double.parseDouble(msg.substring(565, 576));
            if(!msg.substring(576, 588).isBlank()) ask_level_10_volume = Long.parseLong(msg.substring(576, 588));
            if(!msg.substring(588, 600).isBlank()) bid_level_10_volume = Long.parseLong(msg.substring(588, 600));
            if(!msg.substring(600, 612).isBlank()) total_ask_volume = Long.parseLong(msg.substring(600, 612));
            if(!msg.substring(612, 624).isBlank()) total_bid_volume = Long.parseLong(msg.substring(612, 624));
            if(!msg.substring(624, 635).isBlank()) estimated_trading_price = Double.parseDouble(msg.substring(624, 635));
            if(!msg.substring(635, 647).isBlank()) estimated_trading_volume = Long.parseLong(msg.substring(635, 647));
            closing_price_type_code = msg.substring(647, 648);
            trading_halt = msg.substring(648, 649);
            end_keyword = msg.substring(649, 650);
        }
    }

    public Map<String, Object> toMap() {
        final Map<String, Object> data = new LinkedHashMap<>();
        for(final Field f: this.getClass().getDeclaredFields()) {
            if(f.isAnnotationPresent(SG_column.class)) {
                final SG_column sc = f.getAnnotation(SG_column.class);
                try {
                    data.put(sc.dbField(), f.get(this));
                } catch (Exception ignore) {
                }
            }
        }
        return data;
    }

    public double getYesterdayPrice() {
        return current_price + getPrice_change_against_the_previous_day();
    }
    public double getComparePriceRate() {
        double value = 0;
        if(current_price != 0 && getYesterdayPrice() != 0) value = (current_price - getYesterdayPrice()) / getYesterdayPrice() * 100;
        return value;
    }
    private Double getComparePriceRate(Double price) {
        if(price == null) return null;
        double value = 0;
        if(opening_price != 0 && price != 0) value = (current_price - price) / price * 100;
        return value;
    }
    public double getCompareYesterdayPrice() {
        double result;
        switch (price_change_against_previous_day) {
            case "4":
            case "5":
                result = -price_change_against_the_previous_day;
                break;
            default:
                result = price_change_against_the_previous_day;
        }
        return result;
    }
    public Map<String, Object> toSocket(Product prod) {
        final Map<String, Object> response = new LinkedHashMap<>();
        response.put("name_kr", prod.getName_kr());
        response.put("name_kr_abbr", prod.getName_kr_abbr());
        response.put("name_en", prod.getName_en());
        response.put("isin_code", isin_code);
        response.put("current_price", current_price);
        response.put("today_trading_count", accumulated_trading_volume);
        response.put("compare_yesterday_price", getCompareYesterdayPrice());
        response.put("compare_yesterday_rate", getComparePriceRate());
        response.put("market_total_price", prod.getHaving_count() * current_price);
        return response;
    }
    public Map<String, Object> toDetailsSocket(Product prod) {
        final Map<String, Object> response = new LinkedHashMap<>(toSocket(prod));
        //0: 초기값, 1: 상한, 2: 상승, 3: 보합, 4: 하한, 5: 하락
        response.put("compare_type", final_ask_bid_type_code);
        //9. 매도 호가 수량
        response.put("ask_level_1_volume", ask_level_1_volume);
        response.put("ask_level_2_volume", ask_level_2_volume);
        response.put("ask_level_3_volume", ask_level_3_volume);
        response.put("ask_level_4_volume", ask_level_4_volume);
        response.put("ask_level_5_volume", ask_level_5_volume);
        response.put("ask_level_6_volume", ask_level_6_volume);
        response.put("ask_level_7_volume", ask_level_7_volume);
        response.put("ask_level_8_volume", ask_level_8_volume);
        response.put("ask_level_9_volume", ask_level_9_volume);
        response.put("ask_level_10_volume", ask_level_10_volume);
        //10. 매도 호가 가격
        response.put("ask_level_1_price", ask_level_1_price);
        response.put("ask_level_2_price", ask_level_2_price);
        response.put("ask_level_3_price", ask_level_3_price);
        response.put("ask_level_4_price", ask_level_4_price);
        response.put("ask_level_5_price", ask_level_5_price);
        response.put("ask_level_6_price", ask_level_6_price);
        response.put("ask_level_7_price", ask_level_7_price);
        response.put("ask_level_8_price", ask_level_8_price);
        response.put("ask_level_9_price", ask_level_9_price);
        response.put("ask_level_10_price", ask_level_10_price);
        //11. 매도 호가 등락률
        response.put("ask_level_1_rate", getComparePriceRate(ask_level_1_price));
        response.put("ask_level_2_rate", getComparePriceRate(ask_level_2_price));
        response.put("ask_level_3_rate", getComparePriceRate(ask_level_3_price));
        response.put("ask_level_4_rate", getComparePriceRate(ask_level_4_price));
        response.put("ask_level_5_rate", getComparePriceRate(ask_level_5_price));
        response.put("ask_level_6_rate", getComparePriceRate(ask_level_6_price));
        response.put("ask_level_7_rate", getComparePriceRate(ask_level_7_price));
        response.put("ask_level_8_rate", getComparePriceRate(ask_level_8_price));
        response.put("ask_level_9_rate", getComparePriceRate(ask_level_9_price));
        response.put("ask_level_10_rate", getComparePriceRate(ask_level_10_price));
        //12. 52주 최고, 52주 최저 -------------------------------- 개발 필요
        response.put("best_high_from_52week", 0);
        response.put("best_low_from_52week", 0);
        //13. 상한가, 하한가
        response.put("limit_high_price", upper_limit_price);
        response.put("limit_low_price", lower_limit_price);
        //14. 시가총액
        response.put("today_trading_total_price", accumulated_trading_value);
        //15. 시가, 고가, 저가
        response.put("opening_price", opening_price);
        response.put("today_high_price", todays_high);
        response.put("today_low_price", todays_low);
        //19. 거래량
        response.put("total_trading_count", accumulated_trading_volume);

        //20. 매수 호가 수량, 가격, 등락률
        response.put("bid_level_1_volume", bid_level_1_volume);
        response.put("bid_level_2_volume", bid_level_2_volume);
        response.put("bid_level_3_volume", bid_level_3_volume);
        response.put("bid_level_4_volume", bid_level_4_volume);
        response.put("bid_level_5_volume", bid_level_5_volume);
        response.put("bid_level_6_volume", bid_level_6_volume);
        response.put("bid_level_7_volume", bid_level_7_volume);
        response.put("bid_level_8_volume", bid_level_8_volume);
        response.put("bid_level_9_volume", bid_level_9_volume);
        response.put("bid_level_10_volume", bid_level_10_volume);

        response.put("bid_level_1_price", bid_level_1_price);
        response.put("bid_level_2_price", bid_level_2_price);
        response.put("bid_level_3_price", bid_level_3_price);
        response.put("bid_level_4_price", bid_level_4_price);
        response.put("bid_level_5_price", bid_level_5_price);
        response.put("bid_level_6_price", bid_level_6_price);
        response.put("bid_level_7_price", bid_level_7_price);
        response.put("bid_level_8_price", bid_level_8_price);
        response.put("bid_level_9_price", bid_level_9_price);
        response.put("bid_level_10_price", bid_level_10_price);

        response.put("bid_level_1_rate", getComparePriceRate(bid_level_1_price));
        response.put("bid_level_2_rate", getComparePriceRate(bid_level_2_price));
        response.put("bid_level_3_rate", getComparePriceRate(bid_level_3_price));
        response.put("bid_level_4_rate", getComparePriceRate(bid_level_4_price));
        response.put("bid_level_5_rate", getComparePriceRate(bid_level_5_price));
        response.put("bid_level_6_rate", getComparePriceRate(bid_level_6_price));
        response.put("bid_level_7_rate", getComparePriceRate(bid_level_7_price));
        response.put("bid_level_8_rate", getComparePriceRate(bid_level_8_price));
        response.put("bid_level_9_rate", getComparePriceRate(bid_level_9_price));
        response.put("bid_level_10_rate", getComparePriceRate(bid_level_10_price));

        //21. 매도 잔량
        response.put("ask_total_count", total_ask_volume);
        //22. 매수 - 매도
        response.put("bid_count", bid_count());
        //23. 매수 잔량
        response.put("bid_total_count", total_bid_volume);

        return response;
    }

    static public String[] insertCols() {
        return new String[] {
                "es_data_category",
                "es_info_category",
                "es_board_id",
                "es_session_id",
                "es_isin_code",
                "es_a_designated_number_for_an_issue",
                "es_price_change_against_previous_day",
                "es_price_change_against_the_previous_day",
                "es_upper_limit_price",
                "es_lower_limit_price",
                "es_current_price",
                "es_opening_price",
                "es_todays_high",
                "es_todays_low",
                "es_accumulated_trading_volume",
                "es_accumulated_trading_value",
                "es_final_ask_bid_type_code",
                "es_ask_level_1_price",
                "es_bid_level_1_price",
                "es_ask_level_1_volume",
                "es_bid_level_1_volume",
                "es_lp_ask_level_1_volume",
                "es_lp_bid_level_1_volume",
                "es_ask_level_2_price",
                "es_bid_level_2_price",
                "es_ask_level_2_volume",
                "es_bid_level_2_volume",
                "es_lp_bid_level_2_volume",
                "es_lp_ask_level_2_volume",
                "es_ask_level_3_price",
                "es_bid_level_3_price",
                "es_ask_level_3_volume",
                "es_bid_level_3_volume",
                "es_lp_ask_level_3_volume",
                "es_lp_bid_level_3_volume",
                "es_ask_level_4_price",
                "es_bid_level_4_price",
                "es_ask_level_4_volume",
                "es_bid_level_4_volume",
                "es_lp_ask_level_4_volume",
                "es_lp_bid_level_4_volume",
                "es_ask_level_5_price",
                "es_bid_level_5_price",
                "es_ask_level_5_volume",
                "es_bid_level_5_volume",
                "es_lp_ask_level_5_volume",
                "es_lp_bid_level_5_volume",
                "es_ask_level_6_price",
                "es_bid_level_6_price",
                "es_ask_level_6_volume",
                "es_bid_level_6_volume",
                "es_lp_ask_level_6_volume",
                "es_lp_bid_level_6_volume",
                "es_ask_level_7_price",
                "es_bid_level_7_price",
                "es_ask_level_7_volume",
                "es_bid_level_7_volume",
                "es_lp_ask_level_7_volume",
                "es_lp_bid_level_7_volume",
                "es_ask_level_8_price",
                "es_bid_level_8_price",
                "es_ask_level_8_volume",
                "es_bid_level_8_volume",
                "es_lp_ask_level_8_volume",
                "es_lp_bid_level_8_volume",
                "es_ask_level_9_price",
                "es_bid_level_9_price",
                "es_ask_level_9_volume",
                "es_bid_level_9_volume",
                "es_lp_ask_level_9_volume",
                "es_lp_bid_level_9_volume",
                "es_ask_level_10_price",
                "es_bid_level_10_price",
                "es_ask_level_10_volume",
                "es_bid_level_10_volume",
                "es_lp_ask_level_10_volume",
                "es_lp_bid_level_10_volume",
                "es_total_ask_volume",
                "es_total_bid_volume",
                "es_estimated_trading_price",
                "es_estimated_trading_volume",
                "es_closing_price_type_code",
                "es_trading_halt",
                "es_is_fast_close",
                "es_fast_close_time",
                "es_end_keyword",
        };
    }

    private long bid_count() {
        if(total_bid_volume == null || total_ask_volume == null) return 0;
        return total_bid_volume - total_ask_volume;
    }
}
