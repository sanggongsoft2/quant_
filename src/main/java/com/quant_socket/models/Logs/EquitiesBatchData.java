package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.Product;
import com.quant_socket.models.SG_model;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SG_table(name = "equities_batch_data")
@Getter
@Setter
public class EquitiesBatchData extends SG_model{

    @SG_idx
    @SG_column(dbField = "ebd_idx")
    private Long idx;
    @SG_column(dbField = "ebd_data_category")
    private String data_category;
    @SG_column(dbField = "ebd_info_category")
    private String info_category;
    @SG_column(dbField = "ebd_msg_seq_number")
    private int msg_seq_number = 0;
    @SG_column(dbField = "ebd_total_number_contract")
    private int total_number_contract = 0;
    @SG_column(dbField = "ebd_business_date")
    private String business_date;
    @SG_column(dbField = "ebd_isin_code")
    private String isin_code;
    @SG_column(dbField = "ebd_a_designated_number_for_an_issue")
    private int a_designated_number_for_an_issue = 0;
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
    private double base_price = 0;
    @SG_column(dbField = "ebd_yes_closing_price_type_code")
    private String yes_closing_price_type_code;
    @SG_column(dbField = "ebd_yes_closing_price")
    private double yes_closing_price = 0;
    @SG_column(dbField = "ebd_yes_accu_trading_amount")
    private long yes_accu_trading_amount = 0;
    @SG_column(dbField = "ebd_yes_accu_trading_value")
    private float yes_accu_trading_value = 0;
    @SG_column(dbField = "ebd_upper_limit_price")
    private double upper_limit_price = 0;
    @SG_column(dbField = "ebd_lower_limit_price")
    private double lower_limit_price = 0;
    @SG_column(dbField = "ebd_sub_price_of_sec")
    private double sub_price_of_sec = 0;
    @SG_column(dbField = "ebd_par_value")
    private double par_value = 0;
    @SG_column(dbField = "ebd_issuing_price")
    private double issuing_price = 0;
    @SG_column(dbField = "ebd_listing_date")
    private String listing_date;
    @SG_column(dbField = "ebd_number_of_listed_shares")
    private long number_of_listed_shares = 0;
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
    private double exercise_price_of_elw_or_bw = 0;
    @SG_column(dbField = "ebd_capital")
    private float capital = 0;
    @SG_column(dbField = "ebd_credit_order_possibillity")
    private String credit_order_possibillity;
    @SG_column(dbField = "ebd_limit_order_permission_type_code")
    private int limit_order_permission_type_code = 0;
    @SG_column(dbField = "ebd_market_price_order_permission_type_code")
    private int market_price_order_permission_type_code = 0;
    @SG_column(dbField = "ebd_conditioned_order_permission_type_code")
    private int conditioned_order_permission_type_code = 0;
    @SG_column(dbField = "ebd_best_favorable_order_permission_type_code")
    private int best_favorable_order_permission_type_code = 0;
    @SG_column(dbField = "ebd_first_best_order_permission_type")
    private int first_best_order_permission_type = 0;
    @SG_column(dbField = "ebd_capital_increase_type_code")
    private String capital_increase_type_code;
    @SG_column(dbField = "ebd_other_stock_type_code")
    private String other_stock_type_code;
    @SG_column(dbField = "ebd_national_stock")
    private String national_stock;
    @SG_column(dbField = "ebd_appr_price")
    private double appr_price = 0;
    @SG_column(dbField = "ebd_loweest_order_price")
    private double loweest_order_price = 0;
    @SG_column(dbField = "ebd_highest_order_price")
    private double highest_order_price = 0;
    @SG_column(dbField = "ebd_unit_of_valume_in_main_board")
    private long unit_of_valume_in_main_board = 0;
    @SG_column(dbField = "ebd_lot_size")
    private long lot_size = 0;
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
    private double etf_tracking_difference = 0;
    @SG_column(dbField = "ebd_regs")
    private String regs;
    @SG_column(dbField = "ebd_spac")
    private String spac;
    @SG_column(dbField = "ebd_tax_type_code")
    private String tax_type_code;
    @SG_column(dbField = "ebd_appr_ratio_of_sub_price")
    private double appr_ratio_of_sub_price = 0;
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
    private float upper_limit_quantity = 0;
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
    @SG_column(dbField = "ebd_crdt")
    private Timestamp createdAt = Timestamp.from(Instant.now());

    public EquitiesBatchData(String msg) throws NumberFormatException {
        data_category = msg.substring(0, 2);
        info_category = msg.substring(2, 5);
        if(!msg.substring(5, 13).isBlank()) msg_seq_number = Integer.parseInt(msg.substring(5, 13));
        if(!msg.substring(13, 19).isBlank()) total_number_contract = Integer.parseInt(msg.substring(13, 19));
        business_date = msg.substring(19, 27);
        isin_code = msg.substring(27, 39);
        if(!msg.substring(39, 45).isBlank()) a_designated_number_for_an_issue = Integer.parseInt(msg.substring(39, 45));
        abbr_issue_code = msg.substring(45, 54);
        abbr_issue_name = msg.substring(54, 94).trim();
        abbr_issue_name_en = msg.substring(94, 134).trim();
        group_number = msg.substring(134, 139);
        market_oper_prod_id = msg.substring(139, 142);
        sec_group_id = msg.substring(142, 144);
        unit_trading = msg.substring(144, 145);
        rights_type_code = msg.substring(145, 147);
        par_value_type_code = msg.substring(147, 149);
        aiowbpiswatsp = msg.substring(149, 150);
        re_evaluation_reason_code = msg.substring(150, 152);
        base_price_change = msg.substring(152, 153);
        random_end_trigger_code = msg.substring(153, 154);
        market_alert = msg.substring(154, 155);
        market_alert_type_code = msg.substring(155, 157);
        kogi = msg.substring(157, 158);
        issue_for_admin = msg.substring(158, 159);
        unfaithful_disclosure = msg.substring(159, 160);
        back_door_listing = msg.substring(160, 161);
        trading_halt = msg.substring(161, 162);
        industry_id = msg.substring(162, 172);
        small_med_sized_business = msg.substring(172, 173);
        section_type_code = msg.substring(173, 174);
        investment_institution_type_code = msg.substring(174, 175);
        if(!msg.substring(175, 186).isBlank()) base_price = Double.parseDouble(msg.substring(175, 186));
        yes_closing_price_type_code = msg.substring(186, 187);
        if(!msg.substring(187, 198).isBlank()) yes_closing_price = Double.parseDouble(msg.substring(187, 198));
        if(!msg.substring(198, 210).isBlank()) yes_accu_trading_amount = Long.parseLong(msg.substring(198, 210));
        if(!msg.substring(210, 232).isBlank()) yes_accu_trading_value = Float.parseFloat(msg.substring(210, 232));
        if(!msg.substring(232, 243).isBlank()) upper_limit_price = Double.parseDouble(msg.substring(232, 243));
        if(!msg.substring(243, 254).isBlank()) lower_limit_price = Double.parseDouble(msg.substring(243, 254));
        if(!msg.substring(254, 265).isBlank()) sub_price_of_sec = Double.parseDouble(msg.substring(254, 265));
        if(!msg.substring(265, 276).isBlank()) par_value = Double.parseDouble(msg.substring(265, 276));
        if(!msg.substring(276, 287).isBlank()) issuing_price = Double.parseDouble(msg.substring(276, 287));
        listing_date = msg.substring(287, 295);
        if(!msg.substring(295, 311).isBlank()) number_of_listed_shares = Long.parseLong(msg.substring(295, 311));
        liquidation_trade = msg.substring(311, 312);
        the_establishment_date = msg.substring(312, 320);
        maturity_date = msg.substring(320, 328);
        exercising_period = msg.substring(328, 336);
        expiration_date_for_right = msg.substring(336, 344);
        if(!msg.substring(344, 357).isBlank()) exercise_price_of_elw_or_bw = Double.parseDouble(msg.substring(344, 357));
        if(!msg.substring(357, 379).isBlank()) capital = Float.parseFloat(msg.substring(357, 379));
        credit_order_possibillity = msg.substring(379, 380);
        if(!msg.substring(380, 385).isBlank()) limit_order_permission_type_code = Integer.parseInt(msg.substring(380, 385));
        if(!msg.substring(385, 390).isBlank()) market_price_order_permission_type_code = Integer.parseInt(msg.substring(385, 390));
        if(!msg.substring(390, 395).isBlank()) conditioned_order_permission_type_code = Integer.parseInt(msg.substring(390, 395));
        if(!msg.substring(395, 400).isBlank()) best_favorable_order_permission_type_code = Integer.parseInt(msg.substring(395, 400));
        if(!msg.substring(400, 405).isBlank()) first_best_order_permission_type = Integer.parseInt(msg.substring(400, 405));
        capital_increase_type_code = msg.substring(405, 407);
        other_stock_type_code = msg.substring(407, 408);
        national_stock = msg.substring(408, 409);
        if(!msg.substring(409, 420).isBlank()) appr_price = Double.parseDouble(msg.substring(409, 420));
        if(!msg.substring(420, 431).isBlank()) loweest_order_price = Double.parseDouble(msg.substring(420, 431));
        if(!msg.substring(431, 442).isBlank()) highest_order_price = Double.parseDouble(msg.substring(431, 442));
        if(!msg.substring(442, 453).isBlank()) unit_of_valume_in_main_board = Long.parseLong(msg.substring(442, 453));
        if(!msg.substring(453, 464).isBlank()) lot_size = Long.parseLong(msg.substring(453, 464));
        reits_type_code = msg.substring(464, 465);
        target_stock_isin_code = msg.substring(465, 477);
        currency_iso_code = msg.substring(477, 480);
        country_code = msg.substring(480, 483);
        market_making_possibillity = msg.substring(483, 484);
        trading_possibillity_in_the_after_hours = msg.substring(484, 485);
        closing_price_trading_in_the_pre_opening_market = msg.substring(485, 486);
        block_trading_in_the_pre_opening_market = msg.substring(486, 487);
        basket_trading_in_the_pre_opening_market = msg.substring(487, 488);
        anno_of_est_trading_price = msg.substring(488, 489);
        short_selling = msg.substring(489, 490);
        if(!msg.substring(490, 503).isBlank()) etf_tracking_difference = Double.parseDouble(msg.substring(490, 503));
        regs = msg.substring(503, 504);
        spac = msg.substring(504, 505);
        tax_type_code = msg.substring(505, 506);
        if(!msg.substring(506, 519).isBlank()) appr_ratio_of_sub_price = Double.parseDouble(msg.substring(506, 519));
        investment_caution_issue = msg.substring(519, 520);
        delisting_date = msg.substring(520, 528);
        short_term_overheat_issue_type_code = msg.substring(528, 529);
        etf_replication_methods_type_code = msg.substring(529, 530);
        expireation_date = msg.substring(530, 538);
        dis_type_code = msg.substring(538, 540);
        calculation_of_red_price_start_date = msg.substring(540, 548);
        calculation_of_red_price_end_date = msg.substring(548, 556);
        etp_prod_type_code = msg.substring(556, 557);
        index_calculation_inst_type_code = msg.substring(557, 559);
        index_market_class_id = msg.substring(559, 565);
        index_seq_number = msg.substring(565, 568);
        tracking_index_type_code = msg.substring(568, 570);
        refer_index_type_code = msg.substring(570, 572);
        index_asset_class_id1 = msg.substring(572, 578);
        index_asset_class_id2 = msg.substring(578, 584);
        ipo_under_member_number = msg.substring(584, 589);
        lp_order = msg.substring(589, 590);
        low_liquidity = msg.substring(590, 591);
        abnormal_rise = msg.substring(591, 592);
        if(!msg.substring(592, 615).isBlank()) upper_limit_quantity = Float.parseFloat(msg.substring(592, 615));
        investment_pre_issue = msg.substring(615, 616);
        prefer_sttocks_with_lesser_shares = msg.substring(616, 617);
        spac_merger = msg.substring(617, 618);
        segment_type_code = msg.substring(618, 619);
        end_keyword = msg.substring(619, 620);
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
        response.put("face_value", par_value);
        //상장 주식 수
        response.put("having_count", prod.getHaving_count());
        //8. 거래량(전일)
        response.put("yes_trading_count", prod.getYesterday_trading_count());
        //16. 액면가, 전일가
        response.put("yesterday_price", yes_closing_price);
        response.put("isin_code", isin_code);
        return response;
    }

    static public String[] insertCols() {
        return new String[] {
                "ebd_data_category",
                "ebd_info_category",
                "ebd_msg_seq_number",
                "ebd_total_number_contract",
                "ebd_business_date",
                "ebd_isin_code",
                "ebd_a_designated_number_for_an_issue",
                "ebd_abbr_issue_code",
                "ebd_abbr_issue_name",
                "ebd_abbr_issue_name_en",
                "ebd_group_number",
                "ebd_market_oper_prod_id",
                "ebd_sec_group_id",
                "ebd_unit_trading",
                "ebd_rights_type_code",
                "ebd_par_value_type_code",
                "ebd_aiowbpiswatsp",
                "ebd_re_evaluation_reason_code",
                "ebd_base_price_change",
                "ebd_random_end_trigger_code",
                "ebd_market_alert",
                "ebd_market_alert_type_code",
                "ebd_kogi",
                "ebd_issue_for_admin",
                "ebd_unfaithful_disclosure",
                "ebd_back_door_listing",
                "ebd_trading_halt",
                "ebd_industry_id",
                "ebd_small_med_sized_business",
                "ebd_section_type_code",
                "ebd_investment_institution_type_code",
                "ebd_base_price",
                "ebd_yes_closing_price_type_code",
                "ebd_yes_closing_price",
                "ebd_yes_accu_trading_amount",
                "ebd_yes_accu_trading_value",
                "ebd_upper_limit_price",
                "ebd_lower_limit_price",
                "ebd_sub_price_of_sec",
                "ebd_par_value",
                "ebd_issuing_price",
                "ebd_listing_date",
                "ebd_number_of_listed_shares",
                "ebd_liquidation_trade",
                "ebd_the_establishment_date",
                "ebd_maturity_date",
                "ebd_exercising_period",
                "ebd_expiration_date_for_right",
                "ebd_exercise_price_of_elw_or_bw",
                "ebd_capital",
                "ebd_credit_order_possibillity",
                "ebd_limit_order_permission_type_code",
                "ebd_market_price_order_permission_type_code",
                "ebd_conditioned_order_permission_type_code",
                "ebd_best_favorable_order_permission_type_code",
                "ebd_first_best_order_permission_type",
                "ebd_capital_increase_type_code",
                "ebd_other_stock_type_code",
                "ebd_national_stock",
                "ebd_appr_price",
                "ebd_loweest_order_price",
                "ebd_highest_order_price",
                "ebd_unit_of_valume_in_main_board",
                "ebd_lot_size",
                "ebd_reits_type_code",
                "ebd_target_stock_isin_code",
                "ebd_currency_iso_code",
                "ebd_country_code",
                "ebd_market_making_possibillity",
                "ebd_trading_possibillity_in_the_after_hours",
                "ebd_closing_price_trading_in_the_pre_opening_market",
                "ebd_block_trading_in_the_pre_opening_market",
                "ebd_basket_trading_in_the_pre_opening_market",
                "ebd_anno_of_est_trading_price",
                "ebd_short_selling",
                "ebd_etf_tracking_difference",
                "ebd_regs",
                "ebd_spac",
                "ebd_tax_type_code",
                "ebd_appr_ratio_of_sub_price",
                "ebd_investment_caution_issue",
                "ebd_delisting_date",
                "ebd_short_term_overheat_issue_type_code",
                "ebd_etf_replication_methods_type_code",
                "ebd_expireation_date",
                "ebd_dis_type_code",
                "ebd_calculation_of_red_price_start_date",
                "ebd_calculation_of_red_price_end_date",
                "ebd_etp_prod_type_code",
                "ebd_index_calculation_inst_type_code",
                "ebd_index_market_class_id",
                "ebd_index_seq_number",
                "ebd_tracking_index_type_code",
                "ebd_refer_index_type_code",
                "ebd_index_asset_class_id1",
                "ebd_index_asset_class_id2",
                "ebd_ipo_under_member_number",
                "ebd_lp_order",
                "ebd_low_liquidity",
                "ebd_abnormal_rise",
                "ebd_upper_limit_quantity",
                "ebd_investment_pre_issue",
                "ebd_prefer_sttocks_with_lesser_shares",
                "ebd_spac_merger",
                "ebd_segment_type_code",
                "ebd_end_keyword",
                "ebd_crdt",
        };
    }
}
