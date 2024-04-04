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

@SG_table(name = "equities_batch_data")
@Getter
public class EquitiesBatchData extends SG_model<EquitiesBatchData> {

    @SG_idx
    @SG_column(dbField = "ebd_idx")
    private Long idx;
    @SG_column(dbField = "ebd_data_category")
    private String data_category;
    @SG_column(dbField = "ebd_info_category")
    private String info_category;
    @SG_column(dbField = "ebd_msg_seq_number")
    private Integer msg_seq_number;
    @SG_column(dbField = "ebd_total_number_contract")
    private Integer total_number_contract;
    @SG_column(dbField = "ebd_business_date")
    private String business_date;
    @SG_column(dbField = "ebd_isin_code")
    private String isin_code;
    @SG_column(dbField = "ebd_a_designated_number_for_an_issue")
    private Integer a_designated_number_for_an_issue;
    @SG_column(dbField = "ebd_abbr_issue_code")
    private String abbr_issue_code;
    @SG_column(dbField = "ebd_abbr_issue_name")
    private String abbr_issue_name;
    @SG_column(dbField = "ebd_abbr_issue_name_en")
    private String abbr_issue_name_en;
    @SG_column(dbField = "ebd_group_number")
    private String group_number;
    @SG_column(dbField = "ebd_market_oper_prod_id")
    private String market_oper_prod_id;
    @SG_column(dbField = "ebd_sec_group_id")
    private String sec_group_id;
    @SG_column(dbField = "ebd_unit_trading")
    private String unit_trading;
    @SG_column(dbField = "ebd_rights_type_code")
    private String rights_type_code;
    @SG_column(dbField = "ebd_par_value_type_code")
    private String par_value_type_code;
    @SG_column(dbField = "ebd_aiowbpiswatsp")
    private String aiowbpiswatsp;
    @SG_column(dbField = "ebd_re_evaluation_reason_code")
    private String re_evaluation_reason_code;
    @SG_column(dbField = "ebd_base_price_change")
    private String base_price_change;
    @SG_column(dbField = "ebd_random_end_trigger_code")
    private String random_end_trigger_code;
    @SG_column(dbField = "ebd_market_alert")
    private String market_alert;
    @SG_column(dbField = "ebd_market_alert_type_code")
    private String market_alert_type_code;
    @SG_column(dbField = "ebd_kogi")
    private String kogi;
    @SG_column(dbField = "ebd_issue_for_admin")
    private String issue_for_admin;
    @SG_column(dbField = "ebd_unfaithful_disclosure")
    private String unfaithful_disclosure;
    @SG_column(dbField = "ebd_back_door_listing")
    private String back_door_listing;
    @SG_column(dbField = "ebd_trading_halt")
    private String trading_halt;
    @SG_column(dbField = "ebd_industry_id")
    private String industry_id;
    @SG_column(dbField = "ebd_small_med_sized_business")
    private String small_med_sized_business;
    @SG_column(dbField = "ebd_section_type_code")
    private String section_type_code;
    @SG_column(dbField = "ebd_investment_institution_type_code")
    private String investment_institution_type_code;
    @SG_column(dbField = "ebd_base_price")
    private Double base_price;
    @SG_column(dbField = "ebd_yes_closing_price_type_code")
    private String yes_closing_price_type_code;
    @SG_column(dbField = "ebd_yes_closing_price")
    private Double yes_closing_price;
    @SG_column(dbField = "ebd_yes_accu_trading_amount")
    private Long yes_accu_trading_amount;
    @SG_column(dbField = "ebd_yes_accu_trading_value")
    private Float yes_accu_trading_value;
    @SG_column(dbField = "ebd_upper_limit_price")
    private Double upper_limit_price;
    @SG_column(dbField = "ebd_lower_limit_price")
    private Double lower_limit_price;
    @SG_column(dbField = "ebd_sub_price_of_sec")
    private Double sub_price_of_sec;
    @SG_column(dbField = "ebd_par_value")
    private Double par_value;
    @SG_column(dbField = "ebd_issuing_price")
    private Double issuing_price;
    @SG_column(dbField = "ebd_listing_date")
    private String listing_date;
    @SG_column(dbField = "ebd_number_of_listed_shares")
    private Long number_of_listed_shares;
    @SG_column(dbField = "ebd_liquidation_trade")
    private String liquidation_trade;
    @SG_column(dbField = "ebd_the_establishment_date")
    private String the_establishment_date;
    @SG_column(dbField = "ebd_maturity_date")
    private String maturity_date;
    @SG_column(dbField = "ebd_exercising_period")
    private String exercising_period;
    @SG_column(dbField = "ebd_expiration_date_for_right")
    private String expiration_date_for_right;
    @SG_column(dbField = "ebd_exercise_price_of_elw_or_bw")
    private Double exercise_price_of_elw_or_bw;
    @SG_column(dbField = "ebd_capital")
    private Float capital;
    @SG_column(dbField = "ebd_credit_order_possibillity")
    private String credit_order_possibillity;
    @SG_column(dbField = "ebd_limit_order_permission_type_code")
    private Integer limit_order_permission_type_code;
    @SG_column(dbField = "ebd_market_price_order_permission_type_code")
    private Integer market_price_order_permission_type_code;
    @SG_column(dbField = "ebd_conditioned_order_permission_type_code")
    private Integer conditioned_order_permission_type_code;
    @SG_column(dbField = "ebd_best_favorable_order_permission_type_code")
    private Integer best_favorable_order_permission_type_code;
    @SG_column(dbField = "ebd_first_best_order_permission_type")
    private Integer first_best_order_permission_type;
    @SG_column(dbField = "ebd_capital_increase_type_code")
    private String capital_increase_type_code;
    @SG_column(dbField = "ebd_other_stock_type_code")
    private String other_stock_type_code;
    @SG_column(dbField = "ebd_national_stock")
    private String national_stock;
    @SG_column(dbField = "ebd_appr_price")
    private Double appr_price;
    @SG_column(dbField = "ebd_loweest_order_price")
    private Double loweest_order_price;
    @SG_column(dbField = "ebd_highest_order_price")
    private Double highest_order_price;
    @SG_column(dbField = "ebd_unit_of_valume_in_main_board")
    private Long unit_of_valume_in_main_board;
    @SG_column(dbField = "ebd_lot_size")
    private Long lot_size;
    @SG_column(dbField = "ebd_reits_type_code")
    private String reits_type_code;
    @SG_column(dbField = "ebd_target_stock_isin_code")
    private String target_stock_isin_code;
    @SG_column(dbField = "ebd_currency_iso_code")
    private String currency_iso_code;
    @SG_column(dbField = "ebd_country_code")
    private String country_code;
    @SG_column(dbField = "ebd_market_making_possibillity")
    private String market_making_possibillity;
    @SG_column(dbField = "ebd_trading_possibillity_in_the_after_hours")
    private String trading_possibillity_in_the_after_hours;
    @SG_column(dbField = "ebd_closing_price_trading_in_the_pre_opening_market")
    private String closing_price_trading_in_the_pre_opening_market;
    @SG_column(dbField = "ebd_block_trading_in_the_pre_opening_market")
    private String block_trading_in_the_pre_opening_market;
    @SG_column(dbField = "ebd_basket_trading_in_the_pre_opening_market")
    private String basket_trading_in_the_pre_opening_market;
    @SG_column(dbField = "ebd_anno_of_est_trading_price")
    private String anno_of_est_trading_price;
    @SG_column(dbField = "ebd_short_selling")
    private String short_selling;
    @SG_column(dbField = "ebd_etf_tracking_difference")
    private Double etf_tracking_difference;
    @SG_column(dbField = "ebd_regs")
    private String regs;
    @SG_column(dbField = "ebd_spac")
    private String spac;
    @SG_column(dbField = "ebd_tax_type_code")
    private String tax_type_code;
    @SG_column(dbField = "ebd_appr_ratio_of_sub_price")
    private Double appr_ratio_of_sub_price;
    @SG_column(dbField = "ebd_investment_caution_issue")
    private String investment_caution_issue;
    @SG_column(dbField = "ebd_delisting_date")
    private String delisting_date;
    @SG_column(dbField = "ebd_short_term_overheat_issue_type_code")
    private String short_term_overheat_issue_type_code;
    @SG_column(dbField = "ebd_etf_replication_methods_type_code")
    private String etf_replication_methods_type_code;
    @SG_column(dbField = "ebd_expireation_date")
    private String expireation_date;
    @SG_column(dbField = "ebd_dis_type_code")
    private String dis_type_code;
    @SG_column(dbField = "ebd_calculation_of_red_price_start_date")
    private String calculation_of_red_price_start_date;
    @SG_column(dbField = "ebd_calculation_of_red_price_end_date")
    private String calculation_of_red_price_end_date;
    @SG_column(dbField = "ebd_etp_prod_type_code")
    private String etp_prod_type_code;
    @SG_column(dbField = "ebd_index_calculation_inst_type_code")
    private String index_calculation_inst_type_code;
    @SG_column(dbField = "ebd_index_market_class_id")
    private String index_market_class_id;
    @SG_column(dbField = "ebd_index_seq_number")
    private String index_seq_number;
    @SG_column(dbField = "ebd_tracking_index_type_code")
    private String tracking_index_type_code;
    @SG_column(dbField = "ebd_refer_index_type_code")
    private String refer_index_type_code;
    @SG_column(dbField = "ebd_index_asset_class_id1")
    private String index_asset_class_id1;
    @SG_column(dbField = "ebd_index_asset_class_id2")
    private String index_asset_class_id2;
    @SG_column(dbField = "ebd_ipo_under_member_number")
    private String ipo_under_member_number;
    @SG_column(dbField = "ebd_lp_order")
    private String lp_order;
    @SG_column(dbField = "ebd_low_liquidity")
    private String low_liquidity;
    @SG_column(dbField = "ebd_abnormal_rise")
    private String abnormal_rise;
    @SG_column(dbField = "ebd_upper_limit_quantity")
    private Float upper_limit_quantity;
    @SG_column(dbField = "ebd_investment_pre_issue")
    private String investment_pre_issue;
    @SG_column(dbField = "ebd_prefer_sttocks_with_lesser_shares")
    private String prefer_sttocks_with_lesser_shares;
    @SG_column(dbField = "ebd_spac_merger")
    private String spac_merger;
    @SG_column(dbField = "ebd_segment_type_code")
    private String segment_type_code;
    @SG_column(dbField = "ebd_end_keyword")
    private String end_keyword;
    @SG_crdt
    @SG_column(dbField = "ebd_idx")
    private Timestamp createdAt;

    public EquitiesBatchData(String msg) {
        int index = 0;
        data_category = msg.substring(index, index+=2);
        info_category = msg.substring(index, index+=3);
        msg_seq_number = Integer.valueOf(msg.substring(index, index+=8));
        total_number_contract = Integer.valueOf(msg.substring(index, index+=6));
        business_date = msg.substring(index, index+=8);
        isin_code = msg.substring(index, index+=12);
        a_designated_number_for_an_issue = Integer.valueOf(msg.substring(index, index+=6));
        abbr_issue_code = msg.substring(index, index+=9);
        abbr_issue_name = msg.substring(index, index+=40);
        abbr_issue_name_en = msg.substring(index, index+=40);
        group_number = msg.substring(index, index+=5);
        market_oper_prod_id = msg.substring(index, index+=3);
        sec_group_id = msg.substring(index, index+=2);
        unit_trading = msg.substring(index, index+=1);
        rights_type_code = msg.substring(index, index+=2);
        par_value_type_code = msg.substring(index, index+=2);
        aiowbpiswatsp = msg.substring(index, index+=1);
        re_evaluation_reason_code = msg.substring(index, index+=2);
        base_price_change = msg.substring(index, index+=1);
        random_end_trigger_code = msg.substring(index, index+=1);
        market_alert = msg.substring(index, index+=1);
        market_alert_type_code = msg.substring(index, index+=2);
        kogi = msg.substring(index, index+=1);
        issue_for_admin = msg.substring(index, index+=1);
        unfaithful_disclosure = msg.substring(index, index+=1);
        back_door_listing = msg.substring(index, index+=1);
        trading_halt = msg.substring(index, index+=1);
        industry_id = msg.substring(index, index+=10);
        small_med_sized_business = msg.substring(index, index+=1);
        section_type_code = msg.substring(index, index+=1);
        investment_institution_type_code = msg.substring(index, index+=1);
        base_price = Double.parseDouble(msg.substring(index, index+=11));
        yes_closing_price_type_code = msg.substring(index, index+=1);
        yes_closing_price = Double.parseDouble(msg.substring(index, index+=11));
        yes_accu_trading_amount = Long.parseLong(msg.substring(index, index+=12));
        yes_accu_trading_value = Float.parseFloat(msg.substring(index, index+=22));
        upper_limit_price = Double.parseDouble(msg.substring(index, index+=11));
        lower_limit_price = Double.parseDouble(msg.substring(index, index+=11));
        sub_price_of_sec = Double.parseDouble(msg.substring(index, index+=11));
        par_value = Double.parseDouble(msg.substring(index, index+=11));
        issuing_price = Double.parseDouble(msg.substring(index, index+=11));
        listing_date = msg.substring(index, index+=8);
        number_of_listed_shares = Long.parseLong(msg.substring(index, index+=16));
        liquidation_trade = msg.substring(index, index+=1);
        the_establishment_date = msg.substring(index, index+=8);
        maturity_date = msg.substring(index, index+=8);
        exercising_period = msg.substring(index, index+=8);
        expiration_date_for_right = msg.substring(index, index+=8);
        exercise_price_of_elw_or_bw = Double.parseDouble(msg.substring(index, index+=11));
        capital = Float.parseFloat(msg.substring(index, index+=22));
        credit_order_possibillity = msg.substring(index, index+=1);
        limit_order_permission_type_code = Integer.valueOf(msg.substring(index, index+=5));
        market_price_order_permission_type_code = Integer.valueOf(msg.substring(index, index+=5));
        conditioned_order_permission_type_code = Integer.valueOf(msg.substring(index, index+=5));
        best_favorable_order_permission_type_code = Integer.valueOf(msg.substring(index, index+=5));
        first_best_order_permission_type = Integer.valueOf(msg.substring(index, index+=5));
        capital_increase_type_code = msg.substring(index, index+=2);
        other_stock_type_code = msg.substring(index, index+=1);
        national_stock = msg.substring(index, index+=1);
        appr_price = Double.parseDouble(msg.substring(index, index+=11));
        loweest_order_price = Double.parseDouble(msg.substring(index, index+=11));
        highest_order_price = Double.parseDouble(msg.substring(index, index+=11));
        unit_of_valume_in_main_board = Long.parseLong(msg.substring(index, index+=11));
        lot_size = Long.parseLong(msg.substring(index, index+=11));
        reits_type_code = msg.substring(index, index+=1);
        target_stock_isin_code = msg.substring(index, index+=12);
        currency_iso_code = msg.substring(index, index+=3);
        country_code = msg.substring(index, index+=3);
        market_making_possibillity = msg.substring(index, index+=1);
        trading_possibillity_in_the_after_hours = msg.substring(index, index+=1);
        closing_price_trading_in_the_pre_opening_market = msg.substring(index, index+=1);
        block_trading_in_the_pre_opening_market = msg.substring(index, index+=1);
        basket_trading_in_the_pre_opening_market = msg.substring(index, index+=1);
        anno_of_est_trading_price = msg.substring(index, index+=1);
        short_selling = msg.substring(index, index+=1);
        etf_tracking_difference = Double.parseDouble(msg.substring(index, index+=13));
        regs = msg.substring(index, index+=1);
        spac = msg.substring(index, index+=1);
        tax_type_code = msg.substring(index, index+=1);
        appr_ratio_of_sub_price = Double.parseDouble(msg.substring(index, index+=13));
        investment_caution_issue = msg.substring(index, index+=1);
        delisting_date = msg.substring(index, index+=8);
        short_term_overheat_issue_type_code = msg.substring(index, index+=1);
        etf_replication_methods_type_code = msg.substring(index, index+=1);
        expireation_date = msg.substring(index, index+=8);
        dis_type_code = msg.substring(index, index+=2);
        calculation_of_red_price_start_date = msg.substring(index, index+=8);
        calculation_of_red_price_end_date = msg.substring(index, index+=8);
        etp_prod_type_code = msg.substring(index, index+=1);
        index_calculation_inst_type_code = msg.substring(index, index+=2);
        index_market_class_id = msg.substring(index, index+=6);
        index_seq_number = msg.substring(index, index+=3);
        tracking_index_type_code = msg.substring(index, index+=2);
        refer_index_type_code = msg.substring(index, index+=2);
        index_asset_class_id1 = msg.substring(index, index+=6);
        index_asset_class_id2 = msg.substring(index, index+=6);
        ipo_under_member_number = msg.substring(index, index+=5);
        lp_order = msg.substring(index, index+=1);
        low_liquidity = msg.substring(index, index+=1);
        abnormal_rise = msg.substring(index, index+=1);
        upper_limit_quantity = Float.parseFloat(msg.substring(index, index+=23));
        investment_pre_issue = msg.substring(index, index+=1);
        prefer_sttocks_with_lesser_shares = msg.substring(index, index+=1);
        spac_merger = msg.substring(index, index+=1);
        segment_type_code = msg.substring(index, index+=1);
        end_keyword = msg.substring(index, index+1);
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
    public Map<String, Object> toSocket(Product prod) {
        final Map<String, Object> response = new HashMap<>();
        response.put("response_type", 3);

        return response;
    }
}
