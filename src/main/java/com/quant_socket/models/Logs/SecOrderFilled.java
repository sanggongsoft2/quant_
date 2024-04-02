package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
}
