package com.quant_socket.services;
import com.quant_socket.models.Logs.EquitiesSnapshot;
import com.quant_socket.models.Product;
import com.quant_socket.repos.EquitiesSnapshotRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class EquitiesSnapshotService extends SocketService{

    private final List<EquitiesSnapshot> logs = new CopyOnWriteArrayList<>();
    @Autowired
    private EquitiesSnapshotRepo repo;
    @Autowired
    private ProductService productService;
    public void dataHandler(EquitiesSnapshot data) {
        if(data.getIsin_code() != null) {
            logs.add(data);
            final Product product = productService.productFromIsinCode(data.getIsin_code());
            if(product != null && data.isRealBoard()) {
                productService.update(data);
                sendMessage(data.toSocket(product), data.getIsin_code());
            }
        }
    }

    @Override
    public void addSession(WebSocketSession ws, String... isinCodes) {
        super.addSession(ws, isinCodes);
        for(String isinCode : isinCodes) {
            final Product prod = productService.productFromIsinCode(isinCode);
            if(prod != null) {
                final EquitiesSnapshot initData = prod.getLatestSnapshot();
                if(initData != null) sendMessage(initData.toSocket(prod), ws);
            }
        }
    }

    @Transactional
    public void insertLogs() {
        final int result = repo.insertMany(insertSql(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                final EquitiesSnapshot eq = logs.get(i);
                eq.setPreparedStatement(ps);
            }

            @Override
            public int getBatchSize() {
                return logs.size();
            }
        });
        if(result > 0) logs.clear();
    }

    private String insertSql() {
        final StringBuilder sb = new StringBuilder();

        String insertCols = """
                eq_board_id,
                eq_isin_code,
                eq_price_change_against_previous_day,
                eq_price_change_against_the_previous_day,
                eq_upper_limit_price,
                eq_lower_limit_price,
                eq_current_price,
                eq_opening_price,
                eq_todays_high,
                eq_todays_low,
                eq_accumulated_trading_volume,
                eq_accumulated_trading_value,
                eq_final_ask_bid_type_code,
                eq_total_ask_volume,
                eq_total_bid_volume,
                eq_estimated_trading_price,
                eq_estimated_trading_volume,
                eq_closing_price_type_code,
                eq_trading_halt
                """;

        final List<String> columns = List.of(insertCols.split(","));

        sb.append("INSERT INTO equities_snapshot(")
                .append(insertCols)
                .append(")")
                .append("\nVALUES(");
        sb.append(String.join(",", columns.stream().map(column -> "?").toList()));
        sb.append(")");
        return sb.toString();
    }


}
