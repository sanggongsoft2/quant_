package com.quant_socket.services;

import com.quant_socket.models.Logs.SeqQuote;
import com.quant_socket.models.Product;
import com.quant_socket.repos.SeqQuoteRepo;
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
public class SeqQuoteService extends SocketService{
    private final List<SeqQuote> logs = new CopyOnWriteArrayList<>();
    @Autowired
    private SeqQuoteRepo repo;

    private String insertSql() {
        final String[] cols = new String[] {
                "sq_data_category",
                "sq_info_category",
                "sq_message_seq_number",
                "sq_board_id",
                "sq_session_id",
                "sq_isin_code",
                "sq_a_designated_number_for_an_issue",
                "sq_processing_time_of_trading_system",
                "sq_ask_level1_price",
                "sq_bid_level1_price",
                "sq_ask_level1_volume",
                "sq_bid_level1_volume",
                "sq_ask_level2_price",
                "sq_bid_level2_price",
                "sq_ask_level2_volume",
                "sq_bid_level2_volume",
                "sq_ask_level3_price",
                "sq_bid_level3_price",
                "sq_ask_level3_volume",
                "sq_bid_level3_volume",
                "sq_ask_level4_price",
                "sq_bid_level4_price",
                "sq_ask_level4_volume",
                "sq_bid_level4_volume",
                "sq_ask_level5_price",
                "sq_bid_level5_price",
                "sq_ask_level5_volume",
                "sq_bid_level5_volume",
                "sq_ask_level6_price",
                "sq_bid_level6_price",
                "sq_ask_level6_volume",
                "sq_bid_level6_volume",
                "sq_ask_level7_price",
                "sq_bid_level7_price",
                "sq_ask_level7_volume",
                "sq_bid_level7_volume",
                "sq_ask_level8_price",
                "sq_bid_level8_price",
                "sq_ask_level8_volume",
                "sq_bid_level8_volume",
                "sq_ask_level9_price",
                "sq_bid_level9_price",
                "sq_ask_level9_volume",
                "sq_bid_level9_volume",
                "sq_ask_level10_price",
                "sq_bid_level10_price",
                "sq_ask_level10_volume",
                "sq_bid_level10_volume",
                "sq_total_ask_volume",
                "sq_total_bid_volume",
                "sq_estimated_tading_price",
                "sq_estimated_trading_volume",
                "sq_end_keyword"
        };

        return "INSERT INTO seq_quote(" +
                String.join(",", cols) + ") " +
                "VALUES(" + String.join(",", Arrays.stream(cols).map(col -> "?").toList()) + ")";
    }

    public void addLog(SeqQuote data) {
        logs.add(data);

        sendMessage(data.toSocket());
        sendMessage(data.toSocket(), data.getIsin_code());
    }

    public void insertLogs() {
        if(!logs.isEmpty()) {
            final int result = repo.insertMany(insertSql(), new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) {
                    final SeqQuote data = logs.get(i);
                    data.setPreparedStatement(ps);
                }

                @Override
                public int getBatchSize() {
                    return logs.size();
                }
            });
            if(result > 0) logs.clear();
            log.debug("SEQ QUOTE INSERT COUNT : {}", result);
        }
    }
}
