package com.quant_socket.services;
import com.quant_socket.models.Logs.EquitiesSnapshot;
import com.quant_socket.models.Product;
import com.quant_socket.models.response.Response;
import com.quant_socket.repos.EquitiesSnapshotRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
@Service
@Slf4j
@RequiredArgsConstructor
public class EquitiesSnapshotService extends SocketService{
    private final List<EquitiesSnapshot> logs = new CopyOnWriteArrayList<>();
    @Autowired
    private EquitiesSnapshotRepo repo;
    @Autowired
    private ProductService productService;

    private String insertSql() {
        final String[] cols = new String[] {
                "es_data_category",
                "es_info_category",
                "es_board_id",
                "es_session_id",
                "es_isin_code",
                "es_a_designated_number_for_an_issue",
                "es_price_change_against_previous_day",
                "es_price_change_against_the_previous_day",
                "es_upper_limit_price",
                "es_lower_limit_price",
                "es_current_price",
                "es_opening_price",
                "es_todays_high",
                "es_todays_low",
                "es_accumulated_trading_volume",
                "es_accumulated_trading_value",
                "es_final_ask_bid_type_code",
                "es_ask_level_1_price",
                "es_bid_level_1_price",
                "es_ask_level_1_volume",
                "es_bid_level_1_volume",
                "es_lp_ask_level_1_volume",
                "es_lp_bid_level_1_volume",
                "es_ask_level_2_price",
                "es_bid_level_2_price",
                "es_ask_level_2_volume",
                "es_bid_level_2_volume",
                "es_lp_bid_level_2_volume",
                "es_lp_ask_level_2_volume",
                "es_ask_level_3_price",
                "es_bid_level_3_price",
                "es_ask_level_3_volume",
                "es_bid_level_3_volume",
                "es_lp_ask_level_3_volume",
                "es_lp_bid_level_3_volume",
                "es_ask_level_4_price",
                "es_bid_level_4_price",
                "es_ask_level_4_volume",
                "es_bid_level_4_volume",
                "es_lp_ask_level_4_volume",
                "es_lp_bid_level_4_volume",
                "es_ask_level_5_price",
                "es_bid_level_5_price",
                "es_ask_level_5_volume",
                "es_bid_level_5_volume",
                "es_lp_ask_level_5_volume",
                "es_lp_bid_level_5_volume",
                "es_ask_level_6_price",
                "es_bid_level_6_price",
                "es_ask_level_6_volume",
                "es_bid_level_6_volume",
                "es_lp_ask_level_6_volume",
                "es_lp_bid_level_6_volume",
                "es_ask_level_7_price",
                "es_bid_level_7_price",
                "es_ask_level_7_volume",
                "es_bid_level_7_volume",
                "es_lp_ask_level_7_volume",
                "es_lp_bid_level_7_volume",
                "es_ask_level_8_price",
                "es_bid_level_8_price",
                "es_ask_level_8_volume",
                "es_bid_level_8_volume",
                "es_lp_ask_level_8_volume",
                "es_lp_bid_level_8_volume",
                "es_ask_level_9_price",
                "es_bid_level_9_price",
                "es_ask_level_9_volume",
                "es_bid_level_9_volume",
                "es_lp_ask_level_9_volume",
                "es_lp_bid_level_9_volume",
                "es_ask_level_10_price",
                "es_bid_level_10_price",
                "es_ask_level_10_volume",
                "es_bid_level_10_volume",
                "es_lp_ask_level_10_volume",
                "es_lp_bid_level_10_volume",
                "es_total_ask_volume",
                "es_total_bid_volume",
                "es_estimated_trading_price",
                "es_estimated_trading_volume",
                "es_closing_price_type_code",
                "es_trading_halt",
                "es_is_fast_close",
                "es_fast_close_time",
                "es_end_keyword"
        };

        String[] questionMarks = new String[cols.length];
        Arrays.fill(questionMarks, "?");

        return "INSERT INTO equities_snapshot(" +
                String.join(",", cols) + ")" +
                "VALUES(" + String.join(",", questionMarks) + ")";

    }

    public void addLog(EquitiesSnapshot data) {
        logs.add(data);

        final Product prod = productService.productFromIsinCode(data.getIsin_code());
        if(prod != null) {
            productService.update(data);
            sendMessage(data.toSocket(prod));
            sendMessage(data.toDetailsSocket(prod), data.getIsin_code());
        }
    }

    public void insertLogs() {
        if(!logs.isEmpty()) {
            final int result = repo.insertMany(insertSql(), new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) {
                    final EquitiesSnapshot data = logs.get(i);
                    data.setPreparedStatement(ps);
                }

                @Override
                public int getBatchSize() {
                    return logs.size();
                }
            });
            if(result > 0) logs.clear();
            log.debug("EQUITIES SNAPSHOT INSERT COUNT : {}", result);
        }
    }
}
