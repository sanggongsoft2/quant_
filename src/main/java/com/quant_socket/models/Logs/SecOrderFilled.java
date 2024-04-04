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
    private Integer message_seq_number;
    @SG_column(dbField = "sof_board_id")
    private String board_id;
    @SG_column(dbField = "sof_session_id")
    private String session_id;
    @SG_column(dbField = "sof_isin_code")
    private String isin_code;
    @SG_column(dbField = "sof_a_des_number_for_an_issue")
    private Integer a_des_number_for_an_issue;
    @SG_column(dbField = "sof_processing_time_of_trading_system")
    private String processing_time_of_trading_system;
    @SG_column(dbField = "sof_price_change_against_previous_day")
    private String price_change_against_previous_day;
    @SG_column(dbField = "sof_price_change_against_the_pre_day")
    private Double price_change_against_the_pre_day;
    @SG_column(dbField = "sof_trading_price")
    private Double trading_price;
    @SG_column(dbField = "sof_trading_volume")
    private Long trading_volume;
    @SG_column(dbField = "sof_opening_price")
    private Double opening_price;
    @SG_column(dbField = "sof_todays_high")
    private Double todays_high;
    @SG_column(dbField = "sof_todays_low")
    private Double todays_low;
    @SG_column(dbField = "sof_accu_trading_volume")
    private Double accu_trading_volume;
    @SG_column(dbField = "sof_accu_trading_value")
    private Float accu_trading_value;
    @SG_column(dbField = "sof_final_askbid_type_code")
    private String final_askbid_type_code;
    @SG_column(dbField = "sof_lp_holding_quantity")
    private Long lp_holding_quantity;
    @SG_column(dbField = "sof_the_best_ask")
    private Double the_best_ask;
    @SG_column(dbField = "sof_the_best_bid")
    private Double the_best_bid;
    @SG_column(dbField = "sof_end_keyword")
    private String end_keyword;
    @SG_crdt
    @SG_column(dbField = "sof_crdt")
    private String createdAt;

    public SecOrderFilled(String msg) {
        int index = 0;
        data_category = msg.substring(index, index += 2);
        info_category = msg.substring(index, index += 3);
        message_seq_number = Integer.parseInt(msg.substring(index, index += 8));
        board_id = msg.substring(index, index += 2);
        session_id = msg.substring(index, index += 2);
        isin_code = msg.substring(index, index += 12);
        a_des_number_for_an_issue = Integer.parseInt(msg.substring(index, index += 6));
        processing_time_of_trading_system = msg.substring(index, index += 12);
        price_change_against_previous_day = msg.substring(index, index += 1);
        price_change_against_the_pre_day = Double.parseDouble(msg.substring(index, index += 11));
        trading_price = Double.parseDouble(msg.substring(index, index += 11));
        trading_volume = Long.parseLong(msg.substring(index, index += 10));
        opening_price = Double.parseDouble(msg.substring(index, index += 11));
        todays_high = Double.parseDouble(msg.substring(index, index += 11));
        todays_low = Double.parseDouble(msg.substring(index, index += 11));
        accu_trading_volume = Double.parseDouble(msg.substring(index, index += 12));
        accu_trading_value = Float.parseFloat(msg.substring(index, index += 22));
        final_askbid_type_code = msg.substring(index, index += 1);
        lp_holding_quantity = Long.parseLong(msg.substring(index, index += 15));
        the_best_ask = Double.parseDouble(msg.substring(index, index += 11));
        the_best_bid = Double.parseDouble(msg.substring(index, index += 11));
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

    private double getTradingRate(Product prod) {
        double value = 0;
        if(prod.getTodayBidCount() != 0 && prod.getTodayAskCount() != 0) value = (double) prod.getTodayBidCount() / prod.getTodayAskCount()*100;
        return value;
    }

    public Map<String, Object> toSocket(Product prod) {
        final Map<String, Object> response = new HashMap<>();
        response.put("response_type", 2);
        response.put("trading_rate", getTradingRate(prod));
        response.put("trading_list", prod.getTradingList());
        return response;
    }
}
