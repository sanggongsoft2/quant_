package com.quant_socket.services;

import com.quant_socket.models.Logs.EquitiesBatchData;
import com.quant_socket.models.Product;
import com.quant_socket.repos.EquitiesBatchDataRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquitiesBatchDataService extends SocketService{
    private final List<EquitiesBatchData> logs = new CopyOnWriteArrayList<>();
    @Autowired
    private EquitiesBatchDataRepo repo;
    @Autowired
    private ProductService productService;

    private String insertSql() {
        final String[] cols = new String[] {
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
        };

        return "INSERT INTO equities_batch_data(" +
                String.join(",", cols) + ")" +
                "VALUES(" + String.join(",", Arrays.stream(cols).map(col -> "?").toList()) + ")";
    }

    public void addLog(EquitiesBatchData data) {
        logs.add(data);

        final Product prod = productService.productFromIsinCode(data.getIsin_code());
        if(prod != null) {
            productService.update(data);
            sendMessage(data.toSocket(prod));
            sendMessage(data.toSocket(prod), data.getIsin_code());
        } else {
            productService.addProduct(new Product(data));
        }
    }

    public void insertLogs() {
        if(!logs.isEmpty()) {
            final int result = repo.insertMany(insertSql(), new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) {
                    final EquitiesBatchData data = logs.get(i);
                    data.setPreparedStatement(ps);
                }

                @Override
                public int getBatchSize() {
                    return logs.size();
                }
            });
            if(result > 0) logs.clear();
            log.debug("EQUITIES BATCH DATA INSERT COUNT : {}", result);
        }
    }
}
