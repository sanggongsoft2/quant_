package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
@SG_table(name = "sec_order_filled")
public class SecOrderFilled {
    @SG_idx
    @SG_column(dbField = "sof_idx")
    private Long idx;
    @SG_column(dbField = "sof_data_category")
    private String data_category;
    @SG_column(dbField = "sof_info_category")
    private String info_category;
    @SG_column(dbField = "sof_message_seq_number")
    private int message_seq_number;
    @SG_column(dbField = "sof_board_id")
    private String board_id;
    @SG_column(dbField = "sof_session_id")
    private String session_id;
    @SG_column(dbField = "sof_isin_code")
    private String isin_code;
    @SG_column(dbField = "sof_a_des_number_for_an_issue")
    private int a_des_number_for_an_issue;
    @SG_column(dbField = "sof_processing_time_of_trading_system")
    private String processing_time_of_trading_system;
    @SG_column(dbField = "sof_price_change_against_previous_day")
    private String price_change_against_previous_day;
    @SG_column(dbField = "sof_price_change_against_the_pre_day")
    private double price_change_against_the_pre_day;
    @SG_column(dbField = "sof_trading_price")
    private double trading_price;
    @SG_column(dbField = "sof_trading_volume")
    private long trading_volume;
    @SG_column(dbField = "sof_opening_price")
    private double opening_price;
    @SG_column(dbField = "sof_todays_high")
    private double todays_high;
    @SG_column(dbField = "sof_todays_low")
    private double todays_low;
    @SG_column(dbField = "sof_accu_trading_volume")
    private double accu_trading_volume;
    @SG_column(dbField = "sof_accu_trading_value")
    private float accu_trading_value;
    @SG_column(dbField = "sof_final_askbid_type_code")
    private String final_askbid_type_code;
    @SG_column(dbField = "sof_lp_holding_quantity")
    private long lp_holding_quantity;
    @SG_column(dbField = "sof_the_best_ask")
    private double the_best_ask;
    @SG_column(dbField = "sof_the_best_bid")
    private double the_best_bid;
    @SG_column(dbField = "sof_end_keyword")
    private String end_keyword;
    @SG_crdt
    @SG_column(dbField = "sof_crdt")
    private String createdAt;

    public SecOrderFilled(String msg) {
        data_category = msg.substring(0, 2);
        info_category = msg.substring(2, 5);
        if(!msg.substring(5, 13).isBlank()) message_seq_number = Integer.parseInt(msg.substring(5, 13));
        board_id = msg.substring(13, 15);
        session_id = msg.substring(15, 17);
        isin_code = msg.substring(17, 29);
        if(!msg.substring(29, 35).isBlank()) a_des_number_for_an_issue = Integer.parseInt(msg.substring(29, 35));
        processing_time_of_trading_system = msg.substring(35, 47);
        price_change_against_previous_day = msg.substring(47, 48);
        if(!msg.substring(48, 59).isBlank()) price_change_against_the_pre_day = Double.parseDouble(msg.substring(48, 59));
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
        end_keyword = msg.substring(185, 186);
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

    private double getTradingRate(Product prod) {
        double value = 0;
        if(prod.getTodayBidCount() != 0 && prod.getTodayAskCount() != 0) value = (double) prod.getTodayBidCount() / prod.getTodayAskCount()*100;
        return value;
    }

    public Map<String, Object> toSocket(Product prod) {
        final Map<String, Object> response = new HashMap<>();
        response.put("response_type", 2);
        //17. 체결강도
        response.put("trading_rate", getTradingRate(prod));
        //18. 체결가격
        response.put("trading_price", trading_price);
        //19. 거래량
        response.put("trading_list", prod.getTradingList());
        return response;
    }
}
