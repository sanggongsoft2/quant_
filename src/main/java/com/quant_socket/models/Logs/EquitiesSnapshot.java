package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.Product;
import com.quant_socket.models.SG_model;
import lombok.Getter;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@SG_table(name = "equities_snapshot")
@Getter
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
    @SG_column(dbField = "es_ask_level_2_price")
    private Double ask_level_2_price;
    @SG_column(dbField = "es_bid_level_2_price")
    private Double bid_level_2_price;
    @SG_column(dbField = "es_ask_level_2_volume")
    private Long ask_level_2_volume;
    @SG_column(dbField = "es_bid_level_2_volume")
    private Long bid_level_2_volume;
    @SG_column(dbField = "es_ask_level_3_price")
    private Double ask_level_3_price;
    @SG_column(dbField = "es_bid_level_3_price")
    private Double bid_level_3_price;
    @SG_column(dbField = "es_ask_level_3_volume")
    private Long ask_level_3_volume;
    @SG_column(dbField = "es_bid_level_3_volume")
    private Long bid_level_3_volume;
    @SG_column(dbField = "es_ask_level_4_price")
    private Double ask_level_4_price;
    @SG_column(dbField = "es_bid_level_4_price")
    private Double bid_level_4_price;
    @SG_column(dbField = "es_ask_level_4_volume")
    private Long ask_level_4_volume;
    @SG_column(dbField = "es_bid_level_4_volume")
    private Long bid_level_4_volume;
    @SG_column(dbField = "es_ask_level_5_price")
    private Double ask_level_5_price;
    @SG_column(dbField = "es_bid_level_5_price")
    private Double bid_level_5_price;
    @SG_column(dbField = "es_ask_level_5_volume")
    private Long ask_level_5_volume;
    @SG_column(dbField = "es_bid_level_5_volume")
    private Long bid_level_5_volume;
    @SG_column(dbField = "es_ask_level_6_price")
    private Double ask_level_6_price;
    @SG_column(dbField = "es_bid_level_6_price")
    private Double bid_level_6_price;
    @SG_column(dbField = "es_ask_level_6_volume")
    private Long ask_level_6_volume;
    @SG_column(dbField = "es_bid_level_6_volume")
    private Long bid_level_6_volume;
    @SG_column(dbField = "es_ask_level_7_price")
    private Double ask_level_7_price;
    @SG_column(dbField = "es_bid_level_7_price")
    private Double bid_level_7_price;
    @SG_column(dbField = "es_ask_level_7_volume")
    private Long ask_level_7_volume;
    @SG_column(dbField = "es_bid_level_7_volume")
    private Long bid_level_7_volume;
    @SG_column(dbField = "es_ask_level_8_price")
    private Double ask_level_8_price;
    @SG_column(dbField = "es_bid_level_8_price")
    private Double bid_level_8_price;
    @SG_column(dbField = "es_ask_level_8_volume")
    private Long ask_level_8_volume;
    @SG_column(dbField = "es_bid_level_8_volume")
    private Long bid_level_8_volume;
    @SG_column(dbField = "es_ask_level_9_price")
    private Double ask_level_9_price;
    @SG_column(dbField = "es_bid_level_9_price")
    private Double bid_level_9_price;
    @SG_column(dbField = "es_ask_level_9_volume")
    private Long ask_level_9_volume;
    @SG_column(dbField = "es_bid_level_9_volume")
    private Long bid_level_9_volume;
    @SG_column(dbField = "es_ask_level_10_price")
    private Double ask_level_10_price;
    @SG_column(dbField = "es_bid_level_10_price")
    private Double bid_level_10_price;
    @SG_column(dbField = "es_ask_level_10_volume")
    private Long ask_level_10_volume;
    @SG_column(dbField = "es_bid_level_10_volume")
    private Long bid_level_10_volume;
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
    @SG_column(dbField = "es_end_keyword")
    private String end_keyword;
    @SG_crdt
    @SG_column(dbField = "es_idx")
    private Timestamp createdAt;

    public EquitiesSnapshot(String msg) {
        int index = 0;
        data_category = msg.substring(index, index += 2);
        info_category = msg.substring(index, index += 3);
        board_id = msg.substring(index, index += 2);
        session_id = msg.substring(index, index += 2);
        isin_code = msg.substring(index, index += 12);
        a_designated_number_for_an_issue = Integer.parseInt(msg.substring(index, index += 6));
        price_change_against_previous_day = msg.substring(index, index += 1);
        price_change_against_the_previous_day = Double.parseDouble(msg.substring(index, index += 11));
        upper_limit_price = Double.parseDouble(msg.substring(index, index += 11));
        lower_limit_price = Double.parseDouble(msg.substring(index, index += 11));
        current_price = Double.parseDouble(msg.substring(index, index += 11));
        opening_price = Double.parseDouble(msg.substring(index, index += 11));
        todays_high = Double.parseDouble(msg.substring(index, index += 11));
        todays_low = Double.parseDouble(msg.substring(index, index += 11));
        accumulated_trading_volume = Long.parseLong(msg.substring(index, index += 12));
        accumulated_trading_value = Float.parseFloat(msg.substring(index, index += 22));
        final_ask_bid_type_code = msg.substring(index, index += 1);
        ask_level_1_price = Double.parseDouble(msg.substring(index, index += 11));
        bid_level_1_price = Double.parseDouble(msg.substring(index, index += 11));
        ask_level_1_volume = Long.parseLong(msg.substring(index, index += 12));
        bid_level_1_volume = Long.parseLong(msg.substring(index, index += 12));
        ask_level_2_price = Double.parseDouble(msg.substring(index, index += 11));
        bid_level_2_price = Double.parseDouble(msg.substring(index, index += 11));
        ask_level_2_volume = Long.parseLong(msg.substring(index, index += 12));
        bid_level_2_volume = Long.parseLong(msg.substring(index, index += 12));
        ask_level_3_price = Double.parseDouble(msg.substring(index, index += 11));
        bid_level_3_price = Double.parseDouble(msg.substring(index, index += 11));
        ask_level_3_volume = Long.parseLong(msg.substring(index, index += 12));
        bid_level_3_volume = Long.parseLong(msg.substring(index, index += 12));
        ask_level_4_price = Double.parseDouble(msg.substring(index, index += 11));
        bid_level_4_price = Double.parseDouble(msg.substring(index, index += 11));
        ask_level_4_volume = Long.parseLong(msg.substring(index, index += 12));
        bid_level_4_volume = Long.parseLong(msg.substring(index, index += 12));
        ask_level_5_price = Double.parseDouble(msg.substring(index, index += 11));
        bid_level_5_price = Double.parseDouble(msg.substring(index, index += 11));
        ask_level_5_volume = Long.parseLong(msg.substring(index, index += 12));
        bid_level_5_volume = Long.parseLong(msg.substring(index, index += 12));
        ask_level_6_price = Double.parseDouble(msg.substring(index, index += 11));
        bid_level_6_price = Double.parseDouble(msg.substring(index, index += 11));
        ask_level_6_volume = Long.parseLong(msg.substring(index, index += 12));
        bid_level_6_volume = Long.parseLong(msg.substring(index, index += 12));
        ask_level_7_price = Double.parseDouble(msg.substring(index, index += 11));
        bid_level_7_price = Double.parseDouble(msg.substring(index, index += 11));
        ask_level_7_volume = Long.parseLong(msg.substring(index, index += 12));
        bid_level_7_volume = Long.parseLong(msg.substring(index, index += 12));
        ask_level_8_price = Double.parseDouble(msg.substring(index, index += 11));
        bid_level_8_price = Double.parseDouble(msg.substring(index, index += 11));
        ask_level_8_volume = Long.parseLong(msg.substring(index, index += 12));
        bid_level_8_volume = Long.parseLong(msg.substring(index, index += 12));
        ask_level_9_price = Double.parseDouble(msg.substring(index, index += 11));
        bid_level_9_price = Double.parseDouble(msg.substring(index, index += 11));
        ask_level_9_volume = Long.parseLong(msg.substring(index, index += 12));
        bid_level_9_volume = Long.parseLong(msg.substring(index, index += 12));
        ask_level_10_price = Double.parseDouble(msg.substring(index, index += 11));
        bid_level_10_price = Double.parseDouble(msg.substring(index, index += 11));
        ask_level_10_volume = Long.parseLong(msg.substring(index, index += 12));
        bid_level_10_volume = Long.parseLong(msg.substring(index, index += 12));
        total_ask_volume = Long.parseLong(msg.substring(index, index += 12));
        total_bid_volume = Long.parseLong(msg.substring(index, index += 12));
        estimated_trading_price = Double.parseDouble(msg.substring(index, index += 11));
        estimated_trading_volume = Long.parseLong(msg.substring(index, index += 12));
        closing_price_type_code = msg.substring(index, index += 1);
        trading_halt = msg.substring(index, index += 1);
        end_keyword = msg.substring(index, index + 1);
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

    public Double getYesterdayPrice() {
        return current_price + getPrice_change_against_the_previous_day();
    }

    public Double getComparePriceRate() {
        double value = 0;
        if(current_price != 0 && getYesterdayPrice() != 0) value = (current_price - getYesterdayPrice()) / getYesterdayPrice() * 100;
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
        response.put("current_price", current_price);
        response.put("today_trading_count", accumulated_trading_volume);
        response.put("compare_yesterday_price", getCompareYesterdayPrice());
        response.put("compare_yesterday_rate", getComparePriceRate());
        response.put("market_total_price", prod.getHaving_count() * current_price);
        return response;
    }
    public Map<String, Object> toDetailsSocket() {
        final Map<String, Object> response = new HashMap<>();
        response.put("response_type", 1);
        response.put("isin_code", isin_code);
        //0: 초기값, 1: 상한, 2: 상승, 3: 보합, 4: 하한, 5: 하락
        response.put("compare_type", final_ask_bid_type_code);
        //4. 현재가
        response.put("current_price", current_price);
        //5. 증감
        response.put("compare_price", getCompareYesterdayPrice());
        //6. 증감률
        response.put("compare_price_percent", getComparePriceRate());
        //7. 거래량(현재)
        response.put("trading_count", accumulated_trading_volume);
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

    private double getComparePriceRate(double price) {
        double value = 0;
        if(opening_price != 0 && price != 0) value = (current_price - price) / price * 100;
        return value;
    }
}
