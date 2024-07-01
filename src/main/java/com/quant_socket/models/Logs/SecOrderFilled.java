package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_substring;
import com.quant_socket.models.Product;
import com.quant_socket.models.SG_substring_model;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.DateFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.time.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@ToString
@Slf4j
public class SecOrderFilled extends SG_substring_model {
    @SG_substring(start = 17, end = 29)
    private String isin_code;
    @SG_substring(start = 35, end = 47)
    private String processing_time_of_trading_system;
    @SG_substring(start = 47, end = 48)
    private String price_change_against_previous_day ;
    @SG_substring(start = 48, end = 59)
    private double a_price_change_against_the_pre_day;
    @SG_substring(start = 59, end = 70)
    private double trading_price;
    @SG_substring(start = 70, end = 80)
    private long trading_volume;
    @SG_substring(start = 80, end = 91)
    private double opening_price;
    @SG_substring(start = 91, end = 102)
    private double todays_high;
    @SG_substring(start = 102, end = 113)
    private double todays_low;
    @SG_substring(start = 113, end = 125)
    private long accu_trading_volume;
    @SG_substring(start = 125, end = 147)
    private float accu_trading_value;
    @SG_substring(start = 147, end = 148)
    private String final_askbid_type_code;
    @SG_substring(start = 148, end = 163)
    private long lp_holding_quantity;
    @SG_substring(start = 163, end = 174)
    private double the_best_ask;
    @SG_substring(start = 174, end = 185)
    private double the_best_bid;

    public double getYesterdayPrice() {
        if(price_change_against_previous_day == null) return 0;
        return switch (price_change_against_previous_day) {
            case "4", "5" -> trading_price + a_price_change_against_the_pre_day;
            default -> trading_price - a_price_change_against_the_pre_day;
        };
    }

    public SecOrderFilled(String msg) {
        super(msg, false);
    }
    private double getTradingRate(Product prod) {
        double value = 0;
        if(prod.getTodayBidCount() != 0 && prod.getTodayAskCount() != 0) value = (double) prod.getTodayBidCount() / prod.getTodayAskCount()*100;
        return value;
    }

    public Map<String, Object> toSocket(Product prod) {
        final Map<String, Object> response = new LinkedHashMap<>();
        response.put("market_total_price", prod.getHaving_count() * trading_price);
        response.put("trading_rate", getTradingRate(prod));
        response.put("trading_price", trading_price);
        response.put("compare_type", price_change_against_previous_day);
        response.put("isin_code", isin_code);
        response.put("compare_price", a_price_change_against_the_pre_day);
        response.put("compare_rate", getCompareRate());
        response.put("trading_count", trading_volume);
        response.put("opening_price", opening_price);
        response.put("opening_rate", getYesterdayCompareRate(opening_price));
        response.put("high_price", todays_high);
        response.put("high_rate", getYesterdayCompareRate(todays_high));
        response.put("low_price", todays_low);
        response.put("low_rate", getYesterdayCompareRate(todays_low));
        response.put("trading_type", bidTypeToString());
        response.put("trading_time", processing_time_of_trading_system);
        response.put("accu_trading_volume", accu_trading_volume);
        response.put("accu_trading_value", accu_trading_value);
        response.putAll(prod.signalToMap());
        return response;
    }

    public double getCompareRate() {
        final double result = a_price_change_against_the_pre_day / trading_price*100;
        if(Double.isNaN(result) || Double.isInfinite(result)) return 0;
        return switch (price_change_against_previous_day) {
            case "4", "5" -> -result;
            default -> result;
        };
    }

    private double getYesterdayCompareRate(double price) {
        if(price == 0 || a_price_change_against_the_pre_day == 0) return 0;
        return a_price_change_against_the_pre_day/price*100;
    }

    private String bidTypeToString() {
        if(final_askbid_type_code != null) {
            return switch (final_askbid_type_code) {
                case " " -> "단일가";
                case "1" -> "매도";
                case "2" -> "매수";
                default -> null;
            };
        } else {
            return null;
        }
    }

    private String tradingTimeToString() {
        if(processing_time_of_trading_system != null) {
            final long microseconds = Long.parseLong(processing_time_of_trading_system);
            final Instant instant = Instant.ofEpochMilli(microseconds / 1000);
            final ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Asia/Seoul"));
            final LocalDate date = zonedDateTime.toLocalDate();
            return date.toString();
        } else {
            return LocalDate.now().toString();
        }
    }

    public LocalDateTime getUnixTimestamp() {
        if(processing_time_of_trading_system == null) return null;
        String hour = processing_time_of_trading_system.substring(0, 2);
        String minute = processing_time_of_trading_system.substring(2, 4);
        String second = processing_time_of_trading_system.substring(4, 6);

        final LocalDate now = LocalDate.now();

        return LocalDateTime.of(
                now.getYear(),
                now.getMonth(),
                now.getDayOfMonth(),
                Integer.parseInt(hour),
                Integer.parseInt(minute),
                Integer.parseInt(second)
        );
    }
}
