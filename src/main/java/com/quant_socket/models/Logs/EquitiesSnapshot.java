package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_substring;
import com.quant_socket.models.Product;
import com.quant_socket.models.SG_model;
import com.quant_socket.models.SG_substring_model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@ToString
public class EquitiesSnapshot extends SG_model {

    @SG_substring(start = 5, end = 7)
    @SG_column(dbField = "eq_board_id")
    private String board_id;
    @SG_substring(start = 9, end = 21)
    @SG_column(dbField = "eq_isin_code")
    private String isin_code;
    @SG_substring(start = 27, end = 28)
    @SG_column(dbField = "eq_price_change_against_previous_day")
    private String price_change_against_previous_day;
    @SG_substring(start = 28, end = 39)
    @SG_column(dbField = "eq_price_change_against_the_previous_day")
    private BigDecimal price_change_against_the_previous_day;
    @SG_substring(start = 39, end = 50)
    @SG_column(dbField = "eq_upper_limit_price")
    private BigDecimal upper_limit_price;
    @SG_substring(start = 50, end = 61)
    @SG_column(dbField = "eq_lower_limit_price")
    private BigDecimal lower_limit_price;
    @SG_substring(start = 61, end = 72)
    @SG_column(dbField = "eq_current_price")
    private BigDecimal current_price;
    @SG_substring(start = 72, end = 83)
    @SG_column(dbField = "eq_opening_price")
    private BigDecimal opening_price;
    @SG_substring(start = 83, end = 94)
    @SG_column(dbField = "eq_todays_high")
    private BigDecimal todays_high;
    @SG_substring(start = 94, end = 105)
    @SG_column(dbField = "eq_todays_low")
    private BigDecimal todays_low;
    @SG_substring(start = 105, end = 117)
    @SG_column(dbField = "eq_accumulated_trading_volume")
    private Long accumulated_trading_volume;
    @SG_substring(start = 117, end = 139)
    @SG_column(dbField = "eq_accumulated_trading_value")
    private Float accumulated_trading_value;
    @SG_substring(start = 139, end = 140)
    @SG_column(dbField = "eq_final_ask_bid_type_code")
    private String final_ask_bid_type_code;

    @SG_substring(start = 600, end = 612)
    @SG_column(dbField = "eq_total_ask_volume")
    private Long total_ask_volume;
    @SG_substring(start = 612, end = 624)
    @SG_column(dbField = "eq_total_bid_volume")
    private Long total_bid_volume;
    @SG_substring(start = 624, end = 635)
    @SG_column(dbField = "eq_estimated_trading_price")
    private BigDecimal estimated_trading_price;
    @SG_substring(start = 635, end = 647)
    @SG_column(dbField = "eq_estimated_trading_volume")
    private Long estimated_trading_volume;
    @SG_substring(start = 647, end = 648)
    @SG_column(dbField = "eq_closing_price_type_code")
    private String closing_price_type_code;
    @SG_substring(start = 648, end = 649)
    @SG_column(dbField = "eq_trading_halt")
    private String trading_halt;

    public EquitiesSnapshot(ResultSet rs) {
        super(rs);
    }

    public EquitiesSnapshot(String msg) {
        super(msg);
    }

    /*public EquitiesSnapshot(String msg) {
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
            if(!msg.substring(828, 840).isBlank()) total_ask_volume = Long.parseLong(msg.substring(840, 852));
            if(!msg.substring(840, 852).isBlank()) total_bid_volume = Long.parseLong(msg.substring(852, 864));
            if(!msg.substring(864, 875).isBlank()) estimated_trading_price = new BigDecimal(msg.substring(864, 875));
            if(!msg.substring(875, 887).isBlank()) estimated_trading_volume = Long.parseLong(msg.substring(875, 887));
            closing_price_type_code = msg.substring(887, 888);
            if(!msg.substring(888, 889).isBlank()) trading_halt = msg.substring(888, 889);
            is_fast_close = msg.charAt(889) == 'Y';
            if(!msg.substring(890, 899).isBlank()) fast_close_time = msg.substring(890, 899);
        } else if(msg.length() >= 649) {
            if(!msg.substring(600, 612).isBlank()) total_ask_volume = Long.parseLong(msg.substring(600, 612));
            if(!msg.substring(612, 624).isBlank()) total_bid_volume = Long.parseLong(msg.substring(612, 624));
            if(!msg.substring(624, 635).isBlank()) estimated_trading_price = new BigDecimal(msg.substring(624, 635));
            if(!msg.substring(635, 647).isBlank()) estimated_trading_volume = Long.parseLong(msg.substring(635, 647));
            closing_price_type_code = msg.substring(647, 648);
            trading_halt = msg.substring(648, 649);
        }
    }*/


    public BigDecimal getYesterdayPrice() {
        if(current_price == null) return null;
        return switch (price_change_against_previous_day) {
            case "4", "5" -> current_price.add(price_change_against_the_previous_day);
            default -> current_price.subtract(price_change_against_the_previous_day);
        };
    }
    public Double getComparePriceRate() {
        try {
            final Double result = (current_price.doubleValue() - getYesterdayPrice().doubleValue()) / getYesterdayPrice().doubleValue() * 100;
            if(result.isNaN()) return null;
            else return result;
        } catch (Exception e) {
            return null;
        }
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
        //13. 상한가, 하한가
        response.put("limit_high_price", upper_limit_price);
        response.put("limit_low_price", lower_limit_price);
        //14. 시가총액
        response.put("today_trading_total_price", accumulated_trading_value);
        //15. 시가, 고가, 저가
        response.put("opening_price", opening_price);
        response.put("today_high_price", todays_high);
        response.put("today_low_price", todays_low);
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