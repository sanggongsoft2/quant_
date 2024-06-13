package com.quant_socket.models.Logs;

import com.quant_socket.models.Product;
import lombok.Getter;
import lombok.ToString;

import java.time.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@ToString
public class SecOrderFilled{
    private String isin_code;
    private String processing_time_of_trading_system;
    private String price_change_against_previous_day ;
    private double a_price_change_against_the_pre_day = 0;
    private double trading_price = 0;
    private long trading_volume = 0;
    private double opening_price = 0;
    private double todays_high = 0;
    private double todays_low = 0;
    private double accu_trading_volume = 0;
    private float accu_trading_value = 0;
    private String final_askbid_type_code;
    private long lp_holding_quantity = 0;
    private double the_best_ask = 0;
    private double the_best_bid = 0;

    public double getYesterdayPrice() {
        return switch (price_change_against_previous_day) {
            case "4", "5" -> trading_price + a_price_change_against_the_pre_day;
            default -> trading_price - a_price_change_against_the_pre_day;
        };
    }

    public SecOrderFilled(String msg) throws NumberFormatException {
        isin_code = msg.substring(17, 29);
        processing_time_of_trading_system = msg.substring(35, 47);
        price_change_against_previous_day = msg.substring(47, 48);
        if(!msg.substring(48, 59).isBlank()) a_price_change_against_the_pre_day = Double.parseDouble(msg.substring(48, 59));
        if(!msg.substring(59, 70).isBlank()) trading_price = Double.parseDouble(msg.substring(59, 70));
        if(!msg.substring(70, 80).isBlank()) trading_volume = Long.parseLong(msg.substring(70, 80));
        if(!msg.substring(80, 91).isBlank()) opening_price = Double.parseDouble(msg.substring(80, 91));
        if(!msg.substring(91, 102).isBlank()) todays_high = Double.parseDouble(msg.substring(91, 102));
        if(!msg.substring(102, 113).isBlank()) todays_low = Double.parseDouble(msg.substring(102, 113));
        if(!msg.substring(113, 125).isBlank()) accu_trading_volume = Double.parseDouble(msg.substring(113, 125));
        if(!msg.substring(125, 147).isBlank()) accu_trading_value = Float.parseFloat(msg.substring(125, 147));
        final_askbid_type_code = msg.substring(147, 148);
        if(!msg.substring(148, 163).isBlank()) lp_holding_quantity = Long.parseLong(msg.substring(148, 163));
        if(!msg.substring(163, 174).isBlank()) the_best_ask = Double.parseDouble(msg.substring(163, 174));
        if(!msg.substring(174, 185).isBlank()) the_best_bid = Double.parseDouble(msg.substring(174, 185));
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
        response.put("trading_time", tradingTimeToString());
        response.put("accu_trading_volume", accu_trading_volume);
        response.put("accu_trading_value", accu_trading_value);
        return response;
    }

    private double getCompareRate() {
        return a_price_change_against_the_pre_day / trading_price*100;
    }

    private double getYesterdayCompareRate(double price) {
        return a_price_change_against_the_pre_day /price*100;
    }

    private String bidTypeToString() {
        return switch (final_askbid_type_code) {
            case " " -> "단일가";
            case "1" -> "매도";
            case "2" -> "매수";
            default -> null;
        };
    }

    private String tradingTimeToString() {
        final long microseconds = Long.parseLong(processing_time_of_trading_system);
        final Instant instant = Instant.ofEpochMilli(microseconds / 1000);
        final ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Asia/Seoul"));
        final LocalDate date = zonedDateTime.toLocalDate();
        return date.toString();
    }
}
