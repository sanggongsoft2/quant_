package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.Product;
import com.quant_socket.models.SG_model;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@SG_table(name = "equities_snapshot")
@Getter
@Slf4j
public class EquitiesSnapshot extends SG_model<EquitiesSnapshot> {

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
    private int a_designated_number_for_an_issue = 0;
    @SG_column(dbField = "es_price_change_against_previous_day")
    private String price_change_against_previous_day;
    @SG_column(dbField = "es_price_change_against_the_previous_day")
    private double price_change_against_the_previous_day = 0;
    @SG_column(dbField = "es_upper_limit_price")
    private double upper_limit_price = 0;
    @SG_column(dbField = "es_lower_limit_price")
    private double lower_limit_price = 0;
    @SG_column(dbField = "es_current_price")
    private double current_price = 0;
    @SG_column(dbField = "es_opening_price")
    private double opening_price = 0;
    @SG_column(dbField = "es_todays_high")
    private double todays_high = 0;
    @SG_column(dbField = "es_todays_low")
    private double todays_low = 0;
    @SG_column(dbField = "es_accumulated_trading_volume")
    private long accumulated_trading_volume = 0;
    @SG_column(dbField = "es_accumulated_trading_value")
    private float accumulated_trading_value = 0;
    @SG_column(dbField = "es_final_ask_bid_type_code")
    private String final_ask_bid_type_code;
    @SG_column(dbField = "es_ask_level_1_price")
    private double ask_level_1_price = 0;
    @SG_column(dbField = "es_bid_level_1_price")
    private double bid_level_1_price = 0;
    @SG_column(dbField = "es_ask_level_1_volume")
    private long ask_level_1_volume = 0;
    @SG_column(dbField = "es_bid_level_1_volume")
    private long bid_level_1_volume = 0;
    @SG_column(dbField = "es_ask_level_2_price")
    private double ask_level_2_price = 0;
    @SG_column(dbField = "es_bid_level_2_price")
    private double bid_level_2_price = 0;
    @SG_column(dbField = "es_ask_level_2_volume")
    private long ask_level_2_volume = 0;
    @SG_column(dbField = "es_bid_level_2_volume")
    private long bid_level_2_volume = 0;
    @SG_column(dbField = "es_ask_level_3_price")
    private double ask_level_3_price = 0;
    @SG_column(dbField = "es_bid_level_3_price")
    private double bid_level_3_price = 0;
    @SG_column(dbField = "es_ask_level_3_volume")
    private long ask_level_3_volume = 0;
    @SG_column(dbField = "es_bid_level_3_volume")
    private long bid_level_3_volume = 0;
    @SG_column(dbField = "es_ask_level_4_price")
    private double ask_level_4_price = 0;
    @SG_column(dbField = "es_bid_level_4_price")
    private double bid_level_4_price = 0;
    @SG_column(dbField = "es_ask_level_4_volume")
    private long ask_level_4_volume = 0;
    @SG_column(dbField = "es_bid_level_4_volume")
    private long bid_level_4_volume = 0;
    @SG_column(dbField = "es_ask_level_5_price")
    private double ask_level_5_price = 0;
    @SG_column(dbField = "es_bid_level_5_price")
    private double bid_level_5_price = 0;
    @SG_column(dbField = "es_ask_level_5_volume")
    private long ask_level_5_volume = 0;
    @SG_column(dbField = "es_bid_level_5_volume")
    private long bid_level_5_volume = 0;
    @SG_column(dbField = "es_ask_level_6_price")
    private double ask_level_6_price = 0;
    @SG_column(dbField = "es_bid_level_6_price")
    private double bid_level_6_price = 0;
    @SG_column(dbField = "es_ask_level_6_volume")
    private long ask_level_6_volume = 0;
    @SG_column(dbField = "es_bid_level_6_volume")
    private long bid_level_6_volume = 0;
    @SG_column(dbField = "es_ask_level_7_price")
    private double ask_level_7_price = 0;
    @SG_column(dbField = "es_bid_level_7_price")
    private double bid_level_7_price = 0;
    @SG_column(dbField = "es_ask_level_7_volume")
    private long ask_level_7_volume = 0;
    @SG_column(dbField = "es_bid_level_7_volume")
    private long bid_level_7_volume = 0;
    @SG_column(dbField = "es_ask_level_8_price")
    private double ask_level_8_price = 0;
    @SG_column(dbField = "es_bid_level_8_price")
    private double bid_level_8_price = 0;
    @SG_column(dbField = "es_ask_level_8_volume")
    private long ask_level_8_volume = 0;
    @SG_column(dbField = "es_bid_level_8_volume")
    private long bid_level_8_volume = 0;
    @SG_column(dbField = "es_ask_level_9_price")
    private double ask_level_9_price  = 0;
    @SG_column(dbField = "es_bid_level_9_price")
    private double bid_level_9_price  = 0;
    @SG_column(dbField = "es_ask_level_9_volume")
    private long ask_level_9_volume = 0;
    @SG_column(dbField = "es_bid_level_9_volume")
    private long bid_level_9_volume = 0;
    @SG_column(dbField = "es_ask_level_10_price")
    private double ask_level_10_price  = 0;
    @SG_column(dbField = "es_bid_level_10_price")
    private double bid_level_10_price  = 0;
    @SG_column(dbField = "es_ask_level_10_volume")
    private long ask_level_10_volume = 0;
    @SG_column(dbField = "es_bid_level_10_volume")
    private long bid_level_10_volume = 0;
    @SG_column(dbField = "es_total_ask_volume")
    private long total_ask_volume = 0;
    @SG_column(dbField = "es_total_bid_volume")
    private long total_bid_volume = 0;
    @SG_column(dbField = "es_estimated_trading_price")
    private double estimated_trading_price = 0;
    @SG_column(dbField = "es_estimated_trading_volume")
    private long estimated_trading_volume = 0;
    @SG_column(dbField = "es_closing_price_type_code")
    private String closing_price_type_code;
    @SG_column(dbField = "es_trading_halt")
    private String trading_halt;
    @SG_column(dbField = "es_end_keyword")
    private String end_keyword;
    @SG_crdt
    @SG_column(dbField = "es_idx")
    private Timestamp createdAt;

    public EquitiesSnapshot(String msg) {
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

    public Map<String, Object> toMap() {
        final Map<String, Object> data = new HashMap<>();
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
    private double getComparePriceRate(double price) {
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
        final Map<String, Object> response = new HashMap<>();
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
        final Map<String, Object> response = new HashMap<>(toSocket(prod));
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
        response.put("bid_count", total_bid_volume - total_ask_volume);
        //23. 매수 잔량
        response.put("bid_total_count", total_bid_volume);

        return response;
    }
    public void setPreparedStatement(PreparedStatement ps) {
        int index = 1;
        for (Field field : getClass().getDeclaredFields()) {
            final Class<?> type = field.getType();
            field.setAccessible(true);
            if(field.isAnnotationPresent(com.quant_socket.annotations.SG_column.class) && !field.isAnnotationPresent(com.quant_socket.annotations.SG_idx.class) && !field.isAnnotationPresent(SG_crdt.class)) {
                try {
                    if(type.equals(String.class)) ps.setString(index, (String) field.get(this));
                    else if(type.equals(Integer.class) || type.equals(int.class)) ps.setInt(index, (Integer) field.get(this));
                    else if(type.equals(Float.class) || type.equals(float.class)) ps.setFloat(index, (float) field.get(this));
                    else if(type.equals(Double.class) || type.equals(double.class)) ps.setDouble(index, (double) field.get(this));
                    else if(type.equals(Long.class) || type.equals(long.class)) ps.setLong(index, (long) field.get(this));
                } catch (Exception ignore) {
                } finally {
                    index++;
                }
            }
        }
    }
}
