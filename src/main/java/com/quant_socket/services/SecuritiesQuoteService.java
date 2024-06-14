package com.quant_socket.services;
import com.quant_socket.models.Logs.SecuritiesQuote;
import com.quant_socket.models.Product;
import com.quant_socket.repos.SecuritiesQuoteRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class SecuritiesQuoteService extends SocketService{

    private final List<SecuritiesQuote> logs = new CopyOnWriteArrayList<>();
    @Autowired
    private SecuritiesQuoteRepo repo;
    @Autowired
    private ProductService productService;
    public void dataHandler(SecuritiesQuote data) {
       logs.add(data);
        final Product product = productService.productFromIsinCode(data.getIsin_code());
        if(product != null) {
            productService.update(data);
            sendMessage(data.toSocket(product), data.getIsin_code());
        }
    }

    @Override
    public void addSession(WebSocketSession ws, String... isinCodes) {
        super.addSession(ws, isinCodes);
        for(String isinCode : isinCodes) {
            final Product prod = productService.productFromIsinCode(isinCode);
            if(prod != null) {
                final SecuritiesQuote initData = prod.getLatestSecuritiesQuote();
                if(initData != null) sendMessage(initData.toSocket(prod), isinCode);
            }
        }
    }

    @Transactional
    public void insertLogs() {
        final int result = repo.insertMany(insertSql(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                final SecuritiesQuote sq = logs.get(i);
                sq.setPreparedStatement(ps);
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

        String insertCols = "sq_board_id," +
                "sq_isin_code," +
                "sq_ask_level_1_price," +
                "sq_ask_level_2_price," +
                "sq_ask_level_3_price," +
                "sq_ask_level_4_price," +
                "sq_ask_level_5_price," +
                "sq_ask_level_6_price," +
                "sq_ask_level_7_price," +
                "sq_ask_level_8_price," +
                "sq_ask_level_9_price," +
                "sq_ask_level_10_price," +
                "sq_bid_level_1_price," +
                "sq_bid_level_2_price," +
                "sq_bid_level_3_price," +
                "sq_bid_level_4_price," +
                "sq_bid_level_5_price," +
                "sq_bid_level_6_price," +
                "sq_bid_level_7_price," +
                "sq_bid_level_8_price," +
                "sq_bid_level_9_price," +
                "sq_bid_level_10_price," +
                "sq_ask_level_1_volume," +
                "sq_ask_level_2_volume," +
                "sq_ask_level_3_volume," +
                "sq_ask_level_4_volume," +
                "sq_ask_level_5_volume," +
                "sq_ask_level_6_volume," +
                "sq_ask_level_7_volume," +
                "sq_ask_level_8_volume," +
                "sq_ask_level_9_volume," +
                "sq_ask_level_10_volume," +
                "sq_bid_level_1_volume," +
                "sq_bid_level_2_volume," +
                "sq_bid_level_3_volume," +
                "sq_bid_level_4_volume," +
                "sq_bid_level_5_volume," +
                "sq_bid_level_6_volume," +
                "sq_bid_level_7_volume," +
                "sq_bid_level_8_volume," +
                "sq_bid_level_9_volume," +
                "sq_bid_level_10_volume," +
                "sq_lp_ask_level_1_volume," +
                "sq_lp_ask_level_2_volume," +
                "sq_lp_ask_level_3_volume," +
                "sq_lp_ask_level_4_volume," +
                "sq_lp_ask_level_5_volume," +
                "sq_lp_ask_level_6_volume," +
                "sq_lp_ask_level_7_volume," +
                "sq_lp_ask_level_8_volume," +
                "sq_lp_ask_level_9_volume," +
                "sq_lp_ask_level_10_volume," +
                "sq_lp_bid_level_1_volume," +
                "sq_lp_bid_level_2_volume," +
                "sq_lp_bid_level_3_volume," +
                "sq_lp_bid_level_4_volume," +
                "sq_lp_bid_level_5_volume," +
                "sq_lp_bid_level_6_volume," +
                "sq_lp_bid_level_7_volume," +
                "sq_lp_bid_level_8_volume," +
                "sq_lp_bid_level_9_volume," +
                "sq_lp_bid_level_10_volume," +
                "sq_total_ask_volume," +
                "sq_total_bid_volume," +
                "sq_estimated_trading_price," +
                "sq_estimated_trading_volume";

        final List<String> columns = List.of(insertCols.split(","));

        sb.append("INSERT INTO securities_quote(")
                .append(insertCols)
                .append(")")
                .append("\nVALUES(");
        sb.append(String.join(",", columns.stream().map(column -> "?").toList()));
        sb.append(")");
        return sb.toString();
    }
}
