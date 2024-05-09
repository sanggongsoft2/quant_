package com.quant_socket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quant_socket.models.Logs.InvestorActivitiesEOD;
import com.quant_socket.models.Logs.SocketLog;
import com.quant_socket.models.Product;
import com.quant_socket.repos.InvestActivitiesEODRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvestActivitiesEODService extends SocketService{

    private final List<InvestorActivitiesEOD> logs = new CopyOnWriteArrayList<>();
    @Autowired
    private InvestActivitiesEODRepo repo;
    @Autowired
    private ProductService productService;

    public void addLog(InvestorActivitiesEOD data) {
        logs.add(data);
        final Product prod = productService.productFromIsinCode(data.getIsin_code());
        if(prod != null) {
            productService.update(data);
            sendMessage(data.toSocket(prod));
            sendMessage(data.toSocket(prod), data.getIsin_code());
        }
    }
    private String insertSql() {
        final String[] cols = new String[] {
                "iae_data_category",
                "iae_info_category",
                "iae_isin_code",
                "iae_a_designated_number_for_an_issue",
                "iae_investor_code",
                "iae_accu_ask_trading_volume",
                "iae_accu_ask_trading_value",
                "iae_accu_bid_trading_volume",
                "iae_accu_bid_trading_value",
                "iae_end_keyword",
        };

        return "INSERT INTO investor_activities_eod(" +
                String.join(",", cols) + ")" +
                "VALUES(" + String.join(",", Arrays.stream(cols).map(col -> "?").toList()) + ")";
    }

    public void insertLogs() {
        if(!logs.isEmpty()) {
            final int result = repo.insertMany(insertSql(), new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    final InvestorActivitiesEOD data = logs.get(i);
                    data.setPreparedStatement(ps);
                }

                @Override
                public int getBatchSize() {
                    return logs.size();
                }
            });
            if(result > 0) logs.clear();
            log.info("INSERT COUNT : {}", result);
        }
    }
}
