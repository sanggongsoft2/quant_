package com.quant_socket.models.Logs;

import com.quant_socket.models.Product;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class InvestorActivitiesEOD {

    private String isin_code;
    private Integer a_designated_number_for_an_issue;
    private String investor_code;
    private Long accu_ask_trading_volume;
    private Float accu_ask_trading_value;
    private Long accu_bid_trading_volume;
    private Float accu_bid_trading_value;

    public InvestorActivitiesEOD(String msg) throws NumberFormatException {
        isin_code = msg.substring(5, 17);
        if(!msg.substring(17, 23).isBlank()) a_designated_number_for_an_issue = Integer.parseInt(msg.substring(17, 23));
        investor_code = msg.substring(23, 27);
        if(!msg.substring(27, 39).isBlank()) accu_ask_trading_volume = Long.parseLong(msg.substring(27, 39));
        if(!msg.substring(39, 61).isBlank()) accu_ask_trading_value = Float.parseFloat(msg.substring(39, 61));
        if(!msg.substring(61, 73).isBlank()) accu_bid_trading_volume = Long.parseLong(msg.substring(61, 73));
        if(!msg.substring(73, 95).isBlank()) accu_bid_trading_value = Float.parseFloat(msg.substring(73, 95));
    }
}
