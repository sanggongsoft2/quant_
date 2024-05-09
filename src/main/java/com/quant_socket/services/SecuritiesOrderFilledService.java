package com.quant_socket.services;

import com.quant_socket.models.Logs.SecOrderFilled;
import com.quant_socket.models.Product;
import com.quant_socket.repos.SecOrderFilledRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecuritiesOrderFilledService extends SocketService{
    private final List<SecOrderFilled> logs = new CopyOnWriteArrayList<>();
    @Autowired
    private SecOrderFilledRepo repo;
    @Autowired
    private ProductService productService;

    private String insertSql() {
        final String[] cols = new String[] {
                "sof_data_category",
                "sof_info_category",
                "sof_message_seq_number",
                "sof_board_id",
                "sof_session_id",
                "sof_isin_code",
                "sof_a_des_number_for_an_issue",
                "sof_processing_time_of_trading_system",
                "sof_price_change_against_previous_day",
                "sof_price_change_against_the_pre_day",
                "sof_trading_price",
                "sof_trading_volume",
                "sof_opening_price",
                "sof_todays_high",
                "sof_todays_low",
                "sof_accu_trading_volume",
                "sof_accu_trading_value",
                "sof_final_askbid_type_code",
                "sof_lp_holding_quantity",
                "sof_the_best_ask",
                "sof_the_best_bid",
                "sof_end_keyword"
        };

        return "INSERT INTO sec_order_filled(" +
                String.join(",", cols) + ")" +
                "VALUES(" + String.join(",", Arrays.stream(cols).map(col -> "?").toList()) + ")";
    }

    public void addLog(SecOrderFilled data) {
        logs.add(data);

        final Product prod = productService.productFromIsinCode(data.getIsin_code());

        if(prod != null) {
            productService.update(data);
            sendMessage(data.toSocket(prod));
            sendMessage(data.toSocket(prod), data.getIsin_code());
        }
    }

    public void insertLogs() {
        if(!logs.isEmpty()) {
            final int result = repo.insertMany(insertSql(), new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) {
                    final SecOrderFilled data = logs.get(i);
                    data.setPreparedStatement(ps);
                }

                @Override
                public int getBatchSize() {
                    return logs.size();
                }
            });
            if(result > 0) logs.clear();
            log.debug("SEC ORDER FILLED INSERT COUNT : {}", result);
        }
    }
}
