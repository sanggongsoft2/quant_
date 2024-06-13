package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.models.Product;
import com.quant_socket.models.SG_model;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class EquitiesSnapshot extends SG_model {

    @SG_column(dbField = "eq_board_id")
    private String board_id;
    @SG_column(dbField = "eq_isin_code")
    private String isin_code;
    @SG_column(dbField = "eq_price_change_against_previous_day")
    private String price_change_against_previous_day;
    @SG_column(dbField = "eq_price_change_against_the_previous_day")
    private BigDecimal price_change_against_the_previous_day;
    @SG_column(dbField = "eq_upper_limit_price")
    private BigDecimal upper_limit_price;
    @SG_column(dbField = "eq_lower_limit_price")
    private BigDecimal lower_limit_price;
    @SG_column(dbField = "eq_current_price")
    private BigDecimal current_price;
    @SG_column(dbField = "eq_opening_price")
    private BigDecimal opening_price;
    @SG_column(dbField = "eq_todays_high")
    private BigDecimal todays_high;
    @SG_column(dbField = "eq_todays_low")
    private BigDecimal todays_low;
    @SG_column(dbField = "eq_accumulated_trading_volume")
    private Long accumulated_trading_volume;
    @SG_column(dbField = "eq_accumulated_trading_value")
    private Float accumulated_trading_value;
    @SG_column(dbField = "eq_final_ask_bid_type_code")
    private String final_ask_bid_type_code;

    @SG_column(dbField = "eq_ask_level_1_price")
    private BigDecimal ask_level_1_price;
    @SG_column(dbField = "eq_ask_level_2_price")
    private BigDecimal ask_level_2_price;
    @SG_column(dbField = "eq_ask_level_3_price")
    private BigDecimal ask_level_3_price;
    @SG_column(dbField = "eq_ask_level_4_price")
    private BigDecimal ask_level_4_price;
    @SG_column(dbField = "eq_ask_level_5_price")
    private BigDecimal ask_level_5_price;
    @SG_column(dbField = "eq_ask_level_6_price")
    private BigDecimal ask_level_6_price;
    @SG_column(dbField = "eq_ask_level_7_price")
    private BigDecimal ask_level_7_price;
    @SG_column(dbField = "eq_ask_level_8_price")
    private BigDecimal ask_level_8_price;
    @SG_column(dbField = "eq_ask_level_9_price")
    private BigDecimal ask_level_9_price;
    @SG_column(dbField = "eq_ask_level_10_price")
    private BigDecimal ask_level_10_price;

    @SG_column(dbField = "eq_ask_level_1_price")
    private BigDecimal bid_level_1_price;
    @SG_column(dbField = "eq_bid_level_2_price")
    private BigDecimal bid_level_2_price;
    @SG_column(dbField = "eq_bid_level_3_price")
    private BigDecimal bid_level_3_price;
    @SG_column(dbField = "eq_bid_level_4_price")
    private BigDecimal bid_level_4_price;
    @SG_column(dbField = "eq_bid_level_5_price")
    private BigDecimal bid_level_5_price;
    @SG_column(dbField = "eq_bid_level_6_price")
    private BigDecimal bid_level_6_price;
    @SG_column(dbField = "eq_bid_level_7_price")
    private BigDecimal bid_level_7_price;
    @SG_column(dbField = "eq_bid_level_8_price")
    private BigDecimal bid_level_8_price;
    @SG_column(dbField = "eq_bid_level_9_price")
    private BigDecimal bid_level_9_price;
    @SG_column(dbField = "eq_bid_level_10_price")
    private BigDecimal bid_level_10_price;

    @SG_column(dbField = "eq_ask_level_1_volume")
    private Long ask_level_1_volume;
    @SG_column(dbField = "eq_ask_level_2_volume")
    private Long ask_level_2_volume;
    @SG_column(dbField = "eq_ask_level_3_volume")
    private Long ask_level_3_volume;
    @SG_column(dbField = "eq_ask_level_4_volume")
    private Long ask_level_4_volume;
    @SG_column(dbField = "eq_ask_level_5_volume")
    private Long ask_level_5_volume;
    @SG_column(dbField = "eq_ask_level_6_volume")
    private Long ask_level_6_volume;
    @SG_column(dbField = "eq_ask_level_7_volume")
    private Long ask_level_7_volume;
    @SG_column(dbField = "eq_ask_level_8_volume")
    private Long ask_level_8_volume;
    @SG_column(dbField = "eq_ask_level_9_volume")
    private Long ask_level_9_volume;
    @SG_column(dbField = "eq_ask_level_10_volume")
    private Long ask_level_10_volume;

    @SG_column(dbField = "eq_bid_level_1_volume")
    private Long bid_level_1_volume;
    @SG_column(dbField = "eq_bid_level_2_volume")
    private Long bid_level_2_volume;
    @SG_column(dbField = "eq_bid_level_3_volume")
    private Long bid_level_3_volume;
    @SG_column(dbField = "eq_bid_level_4_volume")
    private Long bid_level_4_volume;
    @SG_column(dbField = "eq_bid_level_5_volume")
    private Long bid_level_5_volume;
    @SG_column(dbField = "eq_bid_level_6_volume")
    private Long bid_level_6_volume;
    @SG_column(dbField = "eq_bid_level_7_volume")
    private Long bid_level_7_volume;
    @SG_column(dbField = "eq_bid_level_8_volume")
    private Long bid_level_8_volume;
    @SG_column(dbField = "eq_bid_level_9_volume")
    private Long bid_level_9_volume;
    @SG_column(dbField = "eq_bid_level_10_volume")
    private Long bid_level_10_volume;

    @SG_column(dbField = "eq_lp_ask_level_1_volume")
    private Long lp_ask_level_1_volume;
    @SG_column(dbField = "eq_lp_ask_level_2_volume")
    private Long lp_ask_level_2_volume;
    @SG_column(dbField = "eq_lp_ask_level_3_volume")
    private Long lp_ask_level_3_volume;
    @SG_column(dbField = "eq_lp_ask_level_4_volume")
    private Long lp_ask_level_4_volume;
    @SG_column(dbField = "eq_lp_ask_level_5_volume")
    private Long lp_ask_level_5_volume;
    @SG_column(dbField = "eq_lp_ask_level_6_volume")
    private Long lp_ask_level_6_volume;
    @SG_column(dbField = "eq_lp_ask_level_7_volume")
    private Long lp_ask_level_7_volume;
    @SG_column(dbField = "eq_lp_ask_level_8_volume")
    private Long lp_ask_level_8_volume;
    @SG_column(dbField = "eq_lp_ask_level_9_volume")
    private Long lp_ask_level_9_volume;
    @SG_column(dbField = "eq_lp_ask_level_10_volume")
    private Long lp_ask_level_10_volume;

    @SG_column(dbField = "eq_lp_bid_level_1_volume")
    private Long lp_bid_level_1_volume;
    @SG_column(dbField = "eq_lp_bid_level_2_volume")
    private Long lp_bid_level_2_volume;
    @SG_column(dbField = "eq_lp_bid_level_3_volume")
    private Long lp_bid_level_3_volume;
    @SG_column(dbField = "eq_lp_bid_level_4_volume")
    private Long lp_bid_level_4_volume;
    @SG_column(dbField = "eq_lp_bid_level_5_volume")
    private Long lp_bid_level_5_volume;
    @SG_column(dbField = "eq_lp_bid_level_6_volume")
    private Long lp_bid_level_6_volume;
    @SG_column(dbField = "eq_lp_bid_level_7_volume")
    private Long lp_bid_level_7_volume;
    @SG_column(dbField = "eq_lp_bid_level_8_volume")
    private Long lp_bid_level_8_volume;
    @SG_column(dbField = "eq_lp_bid_level_9_volume")
    private Long lp_bid_level_9_volume;
    @SG_column(dbField = "eq_lp_bid_level_10_volume")
    private Long lp_bid_level_10_volume;

    @SG_column(dbField = "eq_total_ask_volume")
    private Long total_ask_volume;
    @SG_column(dbField = "eq_total_bid_volume")
    private Long total_bid_volume;
    @SG_column(dbField = "eq_estimated_trading_price")
    private BigDecimal estimated_trading_price;
    @SG_column(dbField = "eq_estimated_trading_volume")
    private Long estimated_trading_volume;
    @SG_column(dbField = "eq_closing_price_type_code")
    private String closing_price_type_code;
    @SG_column(dbField = "eq_trading_halt")
    private String trading_halt;
    @SG_column(dbField = "eq_is_fast_close")
    private boolean is_fast_close;
    @SG_column(dbField = "eq_fast_close_time")
    private String fast_close_time;

    public EquitiesSnapshot(ResultSet rs) {
        super.resultSetToClass(rs);
    }

    public EquitiesSnapshot(String msg) {

        board_id = msg.substring(5, 7);
        isin_code = msg.substring(9, 21);
        price_change_against_previous_day = msg.substring(27, 28);
        if(!msg.substring(28, 39).isBlank()) price_change_against_the_previous_day = new BigDecimal(msg.substring(28, 39));
        if(!msg.substring(39, 50).isBlank()) upper_limit_price = new BigDecimal(msg.substring(39, 50));
        if(!msg.substring(50, 61).isBlank()) lower_limit_price = new BigDecimal(msg.substring(50, 61));
        if(!msg.substring(61, 72).isBlank()) current_price = new BigDecimal(msg.substring(61, 72));
        if(!msg.substring(72, 83).isBlank()) opening_price = new BigDecimal(msg.substring(72, 83));
        if(!msg.substring(83, 94).isBlank()) todays_high = new BigDecimal(msg.substring(83, 94));
        if(!msg.substring(94, 105).isBlank()) todays_low = new BigDecimal(msg.substring(94, 105));
        if(!msg.substring(105, 117).isBlank()) accumulated_trading_volume = Long.parseLong(msg.substring(105, 117));
        if(!msg.substring(117, 139).isBlank()) accumulated_trading_value = Float.parseFloat(msg.substring(117, 139));
        final_ask_bid_type_code = msg.substring(139, 140);

        if(msg.length() >= 899) {
            if(!msg.substring(140, 151).isBlank()) ask_level_1_price = new BigDecimal(msg.substring(140, 151));
            if(!msg.substring(151, 162).isBlank()) bid_level_1_price = new BigDecimal(msg.substring(151, 162));
            if(!msg.substring(162, 174).isBlank()) ask_level_1_volume = Long.parseLong(msg.substring(162, 174));
            if(!msg.substring(174, 186).isBlank()) bid_level_1_volume = Long.parseLong(msg.substring(174, 186));
            if(!msg.substring(186, 198).isBlank()) lp_ask_level_1_volume = Long.parseLong(msg.substring(186, 198));
            if(!msg.substring(198, 210).isBlank()) lp_bid_level_1_volume = Long.parseLong(msg.substring(198, 210));

            if(!msg.substring(210, 221).isBlank()) ask_level_2_price = new BigDecimal(msg.substring(210, 221));
            if(!msg.substring(221, 232).isBlank()) bid_level_2_price = new BigDecimal(msg.substring(221, 232));
            if(!msg.substring(232, 244).isBlank()) ask_level_2_volume = Long.parseLong(msg.substring(232, 244));
            if(!msg.substring(244, 256).isBlank()) bid_level_2_volume = Long.parseLong(msg.substring(244, 256));
            if(!msg.substring(256, 268).isBlank()) lp_ask_level_2_volume = Long.parseLong(msg.substring(256, 268));
            if(!msg.substring(268, 280).isBlank()) lp_bid_level_2_volume = Long.parseLong(msg.substring(268, 280));

            if(!msg.substring(280, 291).isBlank()) ask_level_3_price = new BigDecimal(msg.substring(280, 291));
            if(!msg.substring(291, 302).isBlank()) bid_level_3_price = new BigDecimal(msg.substring(291, 302));
            if(!msg.substring(302, 314).isBlank()) ask_level_3_volume = Long.parseLong(msg.substring(302, 314));
            if(!msg.substring(314, 326).isBlank()) bid_level_3_volume = Long.parseLong(msg.substring(314, 326));
            if(!msg.substring(326, 338).isBlank()) lp_ask_level_3_volume = Long.parseLong(msg.substring(326, 338));
            if(!msg.substring(338, 350).isBlank()) lp_bid_level_3_volume = Long.parseLong(msg.substring(338, 350));

            if(!msg.substring(350, 361).isBlank()) ask_level_4_price = new BigDecimal(msg.substring(350, 361));
            if(!msg.substring(361, 372).isBlank()) bid_level_4_price = new BigDecimal(msg.substring(361, 372));
            if(!msg.substring(372, 384).isBlank()) ask_level_4_volume = Long.parseLong(msg.substring(372, 384));
            if(!msg.substring(384, 396).isBlank()) bid_level_4_volume = Long.parseLong(msg.substring(384, 396));
            if(!msg.substring(396, 408).isBlank()) lp_ask_level_4_volume = Long.parseLong(msg.substring(396, 408));
            if(!msg.substring(408, 420).isBlank()) lp_bid_level_4_volume = Long.parseLong(msg.substring(408, 420));

            if(!msg.substring(420, 431).isBlank()) ask_level_5_price = new BigDecimal(msg.substring(420, 431));
            if(!msg.substring(431, 442).isBlank()) bid_level_5_price = new BigDecimal(msg.substring(431, 442));
            if(!msg.substring(442, 454).isBlank()) ask_level_5_volume = Long.parseLong(msg.substring(442, 454));
            if(!msg.substring(454, 466).isBlank()) bid_level_5_volume = Long.parseLong(msg.substring(454, 466));
            if(!msg.substring(466, 478).isBlank()) lp_ask_level_5_volume = Long.parseLong(msg.substring(466, 478));
            if(!msg.substring(478, 490).isBlank()) lp_bid_level_5_volume = Long.parseLong(msg.substring(478, 490));

            if(!msg.substring(490, 501).isBlank()) ask_level_6_price = new BigDecimal(msg.substring(490, 501));
            if(!msg.substring(501, 512).isBlank()) bid_level_6_price = new BigDecimal(msg.substring(501, 512));
            if(!msg.substring(512, 524).isBlank()) ask_level_6_volume = Long.parseLong(msg.substring(512, 524));
            if(!msg.substring(524, 536).isBlank()) bid_level_6_volume = Long.parseLong(msg.substring(524, 536));
            if(!msg.substring(536, 548).isBlank()) lp_ask_level_6_volume = Long.parseLong(msg.substring(536, 548));
            if(!msg.substring(548, 560).isBlank()) lp_bid_level_6_volume = Long.parseLong(msg.substring(548, 560));

            if(!msg.substring(560, 571).isBlank()) ask_level_7_price = new BigDecimal(msg.substring(560, 571));
            if(!msg.substring(571, 582).isBlank()) bid_level_7_price = new BigDecimal(msg.substring(571, 582));
            if(!msg.substring(582, 594).isBlank()) ask_level_7_volume = Long.parseLong(msg.substring(582, 594));
            if(!msg.substring(594, 606).isBlank()) bid_level_7_volume = Long.parseLong(msg.substring(594, 606));
            if(!msg.substring(606, 618).isBlank()) lp_ask_level_7_volume = Long.parseLong(msg.substring(606, 618));
            if(!msg.substring(618, 630).isBlank()) lp_bid_level_7_volume = Long.parseLong(msg.substring(618, 630));

            if(!msg.substring(630, 641).isBlank()) ask_level_8_price = new BigDecimal(msg.substring(630, 641));
            if(!msg.substring(641, 652).isBlank()) bid_level_8_price = new BigDecimal(msg.substring(641, 652));
            if(!msg.substring(652, 664).isBlank()) ask_level_8_volume = Long.parseLong(msg.substring(652, 664));
            if(!msg.substring(664, 676).isBlank()) bid_level_8_volume = Long.parseLong(msg.substring(664, 676));
            if(!msg.substring(676, 688).isBlank()) lp_ask_level_8_volume = Long.parseLong(msg.substring(676, 688));
            if(!msg.substring(688, 700).isBlank()) lp_bid_level_8_volume = Long.parseLong(msg.substring(688, 700));

            if(!msg.substring(700, 711).isBlank()) ask_level_9_price = new BigDecimal(msg.substring(700, 711));
            if(!msg.substring(711, 722).isBlank()) bid_level_9_price = new BigDecimal(msg.substring(711, 722));
            if(!msg.substring(722, 734).isBlank()) ask_level_9_volume = Long.parseLong(msg.substring(722, 734));
            if(!msg.substring(734, 746).isBlank()) bid_level_9_volume = Long.parseLong(msg.substring(734, 746));
            if(!msg.substring(746, 758).isBlank()) lp_ask_level_9_volume = Long.parseLong(msg.substring(746, 758));
            if(!msg.substring(758, 770).isBlank()) lp_bid_level_9_volume = Long.parseLong(msg.substring(758, 770));

            if(!msg.substring(770, 781).isBlank()) ask_level_10_price = new BigDecimal(msg.substring(770, 781));
            if(!msg.substring(781, 792).isBlank()) bid_level_10_price = new BigDecimal(msg.substring(781, 792));
            if(!msg.substring(792, 804).isBlank()) ask_level_10_volume = Long.parseLong(msg.substring(792, 804));
            if(!msg.substring(804, 816).isBlank()) bid_level_10_volume = Long.parseLong(msg.substring(804, 816));
            if(!msg.substring(816, 828).isBlank()) lp_ask_level_10_volume = Long.parseLong(msg.substring(816, 828));
            if(!msg.substring(828, 840).isBlank()) lp_bid_level_10_volume = Long.parseLong(msg.substring(828, 840));

            if(!msg.substring(828, 840).isBlank()) total_ask_volume = Long.parseLong(msg.substring(840, 852));
            if(!msg.substring(840, 852).isBlank()) total_bid_volume = Long.parseLong(msg.substring(852, 864));
            if(!msg.substring(864, 875).isBlank()) estimated_trading_price = new BigDecimal(msg.substring(864, 875));
            if(!msg.substring(875, 887).isBlank()) estimated_trading_volume = Long.parseLong(msg.substring(875, 887));
            closing_price_type_code = msg.substring(887, 888);
            if(!msg.substring(888, 889).isBlank()) trading_halt = msg.substring(888, 889);
            is_fast_close = msg.charAt(889) == 'Y';
            if(!msg.substring(890, 899).isBlank()) fast_close_time = msg.substring(890, 899);
        } else if(msg.length() >= 649) {
            if(!msg.substring(140, 151).isBlank()) ask_level_1_price = new BigDecimal(msg.substring(140, 151));
            if(!msg.substring(151, 162).isBlank()) bid_level_1_price = new BigDecimal(msg.substring(151, 162));
            if(!msg.substring(162, 174).isBlank()) ask_level_1_volume = Long.parseLong(msg.substring(162, 174));
            if(!msg.substring(174, 186).isBlank()) bid_level_1_volume = Long.parseLong(msg.substring(174, 186));
            if(!msg.substring(186, 197).isBlank()) ask_level_2_price = new BigDecimal(msg.substring(186, 197));
            if(!msg.substring(197, 208).isBlank()) bid_level_2_price = new BigDecimal(msg.substring(197, 208));
            if(!msg.substring(208, 220).isBlank()) ask_level_2_volume = Long.parseLong(msg.substring(208, 220));
            if(!msg.substring(220, 232).isBlank()) bid_level_2_volume = Long.parseLong(msg.substring(220, 232));
            if(!msg.substring(232, 243).isBlank()) ask_level_3_price = new BigDecimal(msg.substring(232, 243));
            if(!msg.substring(243, 254).isBlank()) bid_level_3_price = new BigDecimal(msg.substring(243, 254));
            if(!msg.substring(254, 266).isBlank()) ask_level_3_volume = Long.parseLong(msg.substring(254, 266));
            if(!msg.substring(266, 278).isBlank()) bid_level_3_volume = Long.parseLong(msg.substring(266, 278));
            if(!msg.substring(278, 289).isBlank()) ask_level_4_price = new BigDecimal(msg.substring(278, 289));
            if(!msg.substring(289, 300).isBlank()) bid_level_4_price = new BigDecimal(msg.substring(289, 300));
            if(!msg.substring(300, 312).isBlank()) ask_level_4_volume = Long.parseLong(msg.substring(300, 312));
            if(!msg.substring(312, 324).isBlank()) bid_level_4_volume = Long.parseLong(msg.substring(312, 324));
            if(!msg.substring(324, 335).isBlank()) ask_level_5_price = new BigDecimal(msg.substring(324, 335));
            if(!msg.substring(335, 346).isBlank()) bid_level_5_price = new BigDecimal(msg.substring(335, 346));
            if(!msg.substring(346, 358).isBlank()) ask_level_5_volume = Long.parseLong(msg.substring(346, 358));
            if(!msg.substring(358, 370).isBlank()) bid_level_5_volume = Long.parseLong(msg.substring(358, 370));
            if(!msg.substring(370, 381).isBlank()) ask_level_6_price = new BigDecimal(msg.substring(370, 381));
            if(!msg.substring(381, 392).isBlank()) bid_level_6_price = new BigDecimal(msg.substring(381, 392));
            if(!msg.substring(392, 404).isBlank()) ask_level_6_volume = Long.parseLong(msg.substring(392, 404));
            if(!msg.substring(404, 416).isBlank()) bid_level_6_volume = Long.parseLong(msg.substring(404, 416));
            if(!msg.substring(416, 427).isBlank()) ask_level_7_price = new BigDecimal(msg.substring(416, 427));
            if(!msg.substring(427, 438).isBlank()) bid_level_7_price = new BigDecimal(msg.substring(427, 438));
            if(!msg.substring(438, 450).isBlank()) ask_level_7_volume = Long.parseLong(msg.substring(438, 450));
            if(!msg.substring(450, 462).isBlank()) bid_level_7_volume = Long.parseLong(msg.substring(450, 462));
            if(!msg.substring(462, 473).isBlank()) ask_level_8_price = new BigDecimal(msg.substring(462, 473));
            if(!msg.substring(473, 484).isBlank()) bid_level_8_price = new BigDecimal(msg.substring(473, 484));
            if(!msg.substring(484, 496).isBlank()) ask_level_8_volume = Long.parseLong(msg.substring(484, 496));
            if(!msg.substring(496, 508).isBlank()) bid_level_8_volume = Long.parseLong(msg.substring(496, 508));
            if(!msg.substring(508, 519).isBlank()) ask_level_9_price = new BigDecimal(msg.substring(508, 519));
            if(!msg.substring(519, 530).isBlank()) bid_level_9_price = new BigDecimal(msg.substring(519, 530));
            if(!msg.substring(530, 542).isBlank()) ask_level_9_volume = Long.parseLong(msg.substring(530, 542));
            if(!msg.substring(542, 554).isBlank()) bid_level_9_volume = Long.parseLong(msg.substring(542, 554));
            if(!msg.substring(554, 565).isBlank()) ask_level_10_price = new BigDecimal(msg.substring(554, 565));
            if(!msg.substring(565, 576).isBlank()) bid_level_10_price = new BigDecimal(msg.substring(565, 576));
            if(!msg.substring(576, 588).isBlank()) ask_level_10_volume = Long.parseLong(msg.substring(576, 588));
            if(!msg.substring(588, 600).isBlank()) bid_level_10_volume = Long.parseLong(msg.substring(588, 600));
            if(!msg.substring(600, 612).isBlank()) total_ask_volume = Long.parseLong(msg.substring(600, 612));
            if(!msg.substring(612, 624).isBlank()) total_bid_volume = Long.parseLong(msg.substring(612, 624));
            if(!msg.substring(624, 635).isBlank()) estimated_trading_price = new BigDecimal(msg.substring(624, 635));
            if(!msg.substring(635, 647).isBlank()) estimated_trading_volume = Long.parseLong(msg.substring(635, 647));
            closing_price_type_code = msg.substring(647, 648);
            trading_halt = msg.substring(648, 649);
        }
    }


    public BigDecimal getYesterdayPrice() {
        return switch (price_change_against_previous_day) {
            case "4", "5" -> current_price.add(price_change_against_the_previous_day);
            default -> current_price.subtract(price_change_against_the_previous_day);
        };
    }
    public double getComparePriceRate() {
        try {
            double value = 0;
            if(getYesterdayPrice().doubleValue() != 0 && getYesterdayPrice() != null) value = (current_price.doubleValue() - getYesterdayPrice().doubleValue()) / getYesterdayPrice().doubleValue() * 100;
            return value;
        } catch (Exception e) {
            return 0;
        }
    }
    private double getComparePriceRate(BigDecimal price) {
        final BigDecimal yesterday_price = getYesterdayPrice();
        if (yesterday_price.compareTo(BigDecimal.ZERO) == 0) return 0;
        // (B - A) / A * 100
        BigDecimal increase = price.subtract(yesterday_price); // B - A
        BigDecimal percentageIncrease = increase.divide(yesterday_price, 4, RoundingMode.HALF_UP) // (B - A) / A
                .multiply(new BigDecimal("100")); // ((B - A) / A) * 100

        return percentageIncrease.doubleValue();

    }
    public Double getCompareYesterdayPrice() {
        try {
            return switch (price_change_against_previous_day) {
                case "4", "5" -> -price_change_against_the_previous_day.doubleValue();
                default -> price_change_against_the_previous_day.doubleValue();
            };
        } catch (Exception e) {
            return null;
        }
    }

    private double marketTotalPrice(Long havingCount) {
        if(current_price != null) return havingCount * current_price.doubleValue();
        else return 0;
    }

    public Map<String, Object> toSocket(Product prod) {
        final Map<String, Object> response = new LinkedHashMap<>();
        response.put("max_52_price", prod.getMax_52_price());
        response.put("min_52_price", prod.getMin_52_price());
        response.put("yesterday_price", getYesterdayPrice());
        response.put("yesterday_trading_volume", prod.getYesterday_trading_count());
        response.put("face_value", prod.getFace_value());
        response.put("name_kr", prod.getName_kr());
        response.put("name_kr_abbr", prod.getName_kr_abbr());
        response.put("name_en", prod.getName_en());
        response.put("isin_code", isin_code);
        response.put("current_price", current_price);
        response.put("today_trading_count", accumulated_trading_volume);
        response.put("compare_yesterday_price", getCompareYesterdayPrice());
        response.put("compare_yesterday_rate", getComparePriceRate());
        response.put("market_total_price", marketTotalPrice(prod.getHaving_count()));

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


    private long bid_count() {
        if(total_bid_volume == null || total_ask_volume == null) return 0;
        return total_bid_volume - total_ask_volume;
    }

    public boolean isRealBoard() {
        return board_id != null && board_id.equals("G1");
    }
}
