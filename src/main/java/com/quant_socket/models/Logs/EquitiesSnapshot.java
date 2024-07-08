package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_substring;
import com.quant_socket.annotations.SG_substring_lp;
import com.quant_socket.models.Product;
import com.quant_socket.models.SG_model;
import com.quant_socket.models.SG_substring_model;
import com.quant_socket.models.Signal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@Slf4j
public class EquitiesSnapshot extends SG_model {

    @SG_substring(start = 5, end = 7)
    @SG_substring_lp(start = 5, end = 7)
    @SG_column(dbField = "eq_board_id")
    private String board_id;
    @SG_substring(start = 9, end = 21)
    @SG_substring_lp(start = 9, end = 21)
    @SG_column(dbField = "eq_isin_code")
    private String isin_code;
    @SG_substring(start = 27, end = 28)
    @SG_substring_lp(start = 27, end = 28)
    @SG_column(dbField = "eq_price_change_against_previous_day")
    private String price_change_against_previous_day;
    @SG_substring(start = 28, end = 39)
    @SG_substring_lp(start = 28, end = 39)
    @SG_column(dbField = "eq_price_change_against_the_previous_day")
    private BigDecimal price_change_against_the_previous_day;
    @SG_substring(start = 39, end = 50)
    @SG_substring_lp(start = 39, end = 50)
    @SG_column(dbField = "eq_upper_limit_price")
    private BigDecimal upper_limit_price;
    @SG_substring(start = 50, end = 61)
    @SG_substring_lp(start = 50, end = 61)
    @SG_column(dbField = "eq_lower_limit_price")
    private BigDecimal lower_limit_price;
    @SG_substring(start = 61, end = 72)
    @SG_substring_lp(start = 61, end = 72)
    @SG_column(dbField = "eq_current_price")
    private BigDecimal current_price;
    @SG_substring(start = 72, end = 83)
    @SG_substring_lp(start = 72, end = 83)
    @SG_column(dbField = "eq_opening_price")
    private BigDecimal opening_price;
    @SG_substring(start = 83, end = 94)
    @SG_substring_lp(start = 83, end = 94)
    @SG_column(dbField = "eq_todays_high")
    private BigDecimal todays_high;
    @SG_substring(start = 94, end = 105)
    @SG_substring_lp(start = 94, end = 105)
    @SG_column(dbField = "eq_todays_low")
    private BigDecimal todays_low;
    @SG_substring(start = 105, end = 117)
    @SG_substring_lp(start = 105, end = 117)
    @SG_column(dbField = "eq_accumulated_trading_volume")
    private Long accumulated_trading_volume;
    @SG_substring(start = 117, end = 139)
    @SG_substring_lp(start = 117, end = 139)
    @SG_column(dbField = "eq_accumulated_trading_value")
    private Float accumulated_trading_value;
    @SG_substring(start = 139, end = 140)
    @SG_substring_lp(start = 139, end = 140)
    @SG_column(dbField = "eq_final_ask_bid_type_code")
    private String final_ask_bid_type_code;

    @SG_substring(start = 600, end = 612)
    @SG_substring_lp(start = 840, end = 852)
    @SG_column(dbField = "eq_total_ask_volume")
    private Long total_ask_volume;
    @SG_substring(start = 612, end = 624)
    @SG_substring_lp(start = 852, end = 864)
    @SG_column(dbField = "eq_total_bid_volume")
    private Long total_bid_volume;
    @SG_substring(start = 624, end = 635)
    @SG_substring_lp(start = 864, end = 875)
    @SG_column(dbField = "eq_estimated_trading_price")
    private BigDecimal estimated_trading_price;
    @SG_substring(start = 635, end = 647)
    @SG_substring_lp(start = 875, end = 887)
    @SG_column(dbField = "eq_estimated_trading_volume")
    private Long estimated_trading_volume;
    @SG_substring(start = 647, end = 648)
    @SG_substring_lp(start = 887, end = 888)
    @SG_column(dbField = "eq_closing_price_type_code")
    private String closing_price_type_code;
    @SG_substring(start = 648, end = 649)
    @SG_substring_lp(start = 888, end = 889)
    @SG_column(dbField = "eq_trading_halt")
    private String trading_halt;

    public EquitiesSnapshot(ResultSet rs) {
        super(rs);
    }

    public EquitiesSnapshot(String msg) {
        this(msg, false);
    }
    public EquitiesSnapshot(String msg, boolean withLP) {
        super(msg, withLP);
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
    public double getComparePriceRate() {
        try {
            final double result = (current_price.doubleValue() - getYesterdayPrice().doubleValue()) / getYesterdayPrice().doubleValue() * 100;
            if(Double.isNaN(result) || Double.isInfinite(result)) return 0;
            else return result;
        } catch (Exception e) {
            return 0;
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

    public Map<String, Object> toSocket(Product prod, Signal signal) {
        final Map<String, Object> response = new LinkedHashMap<>();
        response.putAll(prod.signalToMap(signal));
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

/*
{
        "market_total_price": 5.16386190575E14,
        "trading_rate": 136.79317459515778,
        "trading_price": 86500.0,
        "compare_type": "2",
        "isin_code": "KR7005930003",
        "compare_price": 1900.0,
        "compare_rate": 2.1965317919075145,
        "trading_count": 13,
        "opening_price": 85600.0,
        "opening_rate": 1.18,
        "high_price": 86500.0,
        "high_rate": 2.25,
        "low_price": 85200.0,
        "low_rate": 0.71,
        "trading_type": "매수",
        "trading_time": "125642493777",
        "accu_trading_volume": 27812442,
        "accu_trading_value": 2.39067359E12,
        "avg_5_day_price": 82300.0000,
        "signal_5_day_text": "보유",
        "signal_5_day_min_price": 81065.5,
        "signal_5_day_max_price": 83534.49999999999,
        "avg_20_day_price": 81688.8889,
        "signal_20_day_text": "보유",
        "signal_20_day_min_price": 80463.5555665,
        "signal_20_day_max_price": 82914.2222335
        }

        {
        "isin_code": "KR7005930003",
        "ask_level_1_volume": 1576983,
        "ask_level_2_volume": 439617,
        "ask_level_3_volume": 393770,
        "ask_level_4_volume": 410278,
        "ask_level_5_volume": 667837,
        "ask_level_6_volume": 1777866,
        "ask_level_7_volume": 156652,
        "ask_level_8_volume": 160582,
        "ask_level_9_volume": 187732,
        "ask_level_10_volume": 138181,
        "ask_level_1_price": 86500,
        "ask_level_2_price": 86600,
        "ask_level_3_price": 86700,
        "ask_level_4_price": 86800,
        "ask_level_5_price": 86900,
        "ask_level_6_price": 87000,
        "ask_level_7_price": 87100,
        "ask_level_8_price": 87200,
        "ask_level_9_price": 87300,
        "ask_level_10_price": 87400,
        "ask_level_1_rate": 2.25,
        "ask_level_2_rate": 2.36,
        "ask_level_3_rate": 2.48,
        "ask_level_4_rate": 2.6,
        "ask_level_5_rate": 2.72,
        "ask_level_6_rate": 2.84,
        "ask_level_7_rate": 2.96,
        "ask_level_8_rate": 3.07,
        "ask_level_9_rate": 3.19,
        "ask_level_10_rate": 3.31,
        "bid_level_1_volume": 196013,
        "bid_level_2_volume": 446103,
        "bid_level_3_volume": 238634,
        "bid_level_4_volume": 236754,
        "bid_level_5_volume": 284125,
        "bid_level_6_volume": 1132162,
        "bid_level_7_volume": 137577,
        "bid_level_8_volume": 174798,
        "bid_level_9_volume": 485381,
        "bid_level_10_volume": 280844,
        "bid_level_1_price": 86400,
        "bid_level_2_price": 86300,
        "bid_level_3_price": 86200,
        "bid_level_4_price": 86100,
        "bid_level_5_price": 86000,
        "bid_level_6_price": 85900,
        "bid_level_7_price": 85800,
        "bid_level_8_price": 85700,
        "bid_level_9_price": 85600,
        "bid_level_10_price": 85500,
        "bid_level_1_rate": 2.13,
        "bid_level_2_rate": 2.01,
        "bid_level_3_rate": 1.89,
        "bid_level_4_rate": 1.77,
        "bid_level_5_rate": 1.65,
        "bid_level_6_rate": 1.54,
        "bid_level_7_rate": 1.42,
        "bid_level_8_rate": 1.3,
        "bid_level_9_rate": 1.18,
        "bid_level_10_rate": 1.06,
        "ask_total_count": 5909498,
        "bid_total_count": 3612391,
        "estimated_trading_price": 3612391,
        "estimated_trading_volume": 0,
        "yesterday_price": 84600.0
        }*/
