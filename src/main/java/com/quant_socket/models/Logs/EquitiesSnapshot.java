package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.SG_model;

import java.sql.Timestamp;

@SG_table(name = "equities_snapshot")
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
    private String a_designated_number_for_an_issue;
    @SG_column(dbField = "es_price_change_against_previous_day")
    private String price_change_against_previous_day;
    @SG_column(dbField = "es_price_change_against_the_previous_day")
    private String price_change_against_the_previous_day;
    @SG_column(dbField = "es_upper_limit_price")
    private String upper_limit_price;
    @SG_column(dbField = "es_lower_limit_price")
    private String lower_limit_price;
    @SG_column(dbField = "es_current_price")
    private String current_price;
    @SG_column(dbField = "es_opening_price")
    private String opening_price;
    @SG_column(dbField = "es_todays_high")
    private String todays_high;
    @SG_column(dbField = "es_todays_low")
    private String todays_low;
    @SG_column(dbField = "es_accumulated_trading_volume")
    private String accumulated_trading_volume;
    @SG_column(dbField = "es_accumulated_trading_value")
    private String accumulated_trading_value;
    @SG_column(dbField = "es_final_ask_bid_type_code")
    private String final_ask_bid_type_code;
    @SG_column(dbField = "es_ask_level_1_price")
    private String ask_level_1_price;
    @SG_column(dbField = "es_bid_level_1_price")
    private String bid_level_1_price;
    @SG_column(dbField = "es_ask_level_1_volume")
    private String ask_level_1_volume;
    @SG_column(dbField = "es_bid_level_1_volume")
    private String bid_level_1_volume;
    @SG_column(dbField = "es_ask_level_2_price")
    private String ask_level_2_price;
    @SG_column(dbField = "es_bid_level_2_price")
    private String bid_level_2_price;
    @SG_column(dbField = "es_ask_level_2_volume")
    private String ask_level_2_volume;
    @SG_column(dbField = "es_bid_level_2_volume")
    private String bid_level_2_volume;
    @SG_column(dbField = "es_ask_level_3_price")
    private String ask_level_3_price;
    @SG_column(dbField = "es_bid_level_3_price")
    private String bid_level_3_price;
    @SG_column(dbField = "es_ask_level_3_volume")
    private String ask_level_3_volume;
    @SG_column(dbField = "es_bid_level_3_volume")
    private String bid_level_3_volume;
    @SG_column(dbField = "es_ask_level_4_price")
    private String ask_level_4_price;
    @SG_column(dbField = "es_bid_level_4_price")
    private String bid_level_4_price;
    @SG_column(dbField = "es_ask_level_4_volume")
    private String ask_level_4_volume;
    @SG_column(dbField = "es_bid_level_4_volume")
    private String bid_level_4_volume;
    @SG_column(dbField = "es_ask_level_5_price")
    private String ask_level_5_price;
    @SG_column(dbField = "es_bid_level_5_price")
    private String bid_level_5_price;
    @SG_column(dbField = "es_ask_level_5_volume")
    private String ask_level_5_volume;
    @SG_column(dbField = "es_bid_level_5_volume")
    private String bid_level_5_volume;
    @SG_column(dbField = "es_ask_level_6_price")
    private String ask_level_6_price;
    @SG_column(dbField = "es_bid_level_6_price")
    private String bid_level_6_price;
    @SG_column(dbField = "es_ask_level_6_volume")
    private String ask_level_6_volume;
    @SG_column(dbField = "es_bid_level_6_volume")
    private String bid_level_6_volume;
    @SG_column(dbField = "es_ask_level_7_price")
    private String ask_level_7_price;
    @SG_column(dbField = "es_bid_level_7_price")
    private String bid_level_7_price;
    @SG_column(dbField = "es_ask_level_7_volume")
    private String ask_level_7_volume;
    @SG_column(dbField = "es_bid_level_7_volume")
    private String bid_level_7_volume;
    @SG_column(dbField = "es_ask_level_8_price")
    private String ask_level_8_price;
    @SG_column(dbField = "es_bid_level_8_price")
    private String bid_level_8_price;
    @SG_column(dbField = "es_ask_level_8_volume")
    private String ask_level_8_volume;
    @SG_column(dbField = "es_bid_level_8_volume")
    private String bid_level_8_volume;
    @SG_column(dbField = "es_ask_level_9_price")
    private String ask_level_9_price;
    @SG_column(dbField = "es_bid_level_9_price")
    private String bid_level_9_price;
    @SG_column(dbField = "es_ask_level_9_volume")
    private String ask_level_9_volume;
    @SG_column(dbField = "es_bid_level_9_volume")
    private String bid_level_9_volume;
    @SG_column(dbField = "es_ask_level_10_price")
    private String ask_level_10_price;
    @SG_column(dbField = "es_bid_level_10_price")
    private String bid_level_10_price;
    @SG_column(dbField = "es_ask_level_10_volume")
    private String ask_level_10_volume;
    @SG_column(dbField = "es_bid_level_10_volume")
    private String bid_level_10_volume;
    @SG_column(dbField = "es_total_ask_volume")
    private String total_ask_volume;
    @SG_column(dbField = "es_total_bid_volume")
    private String total_bid_volume;
    @SG_column(dbField = "es_estimated_trading_price")
    private String estimated_trading_price;
    @SG_column(dbField = "es_estimated_trading_volume")
    private String estimated_trading_volume;
    @SG_column(dbField = "es_closing_price_type_code")
    private String closing_price_type_code;
    @SG_column(dbField = "es_trading_halt")
    private String trading_halt;
    @SG_column(dbField = "es_end_keyword")
    private String end_keyword;

    @SG_crdt
    @SG_column(dbField = "es_idx")
    private Timestamp createdAt;
}
