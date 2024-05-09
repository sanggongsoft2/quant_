package com.quant_socket.services;

import com.quant_socket.models.Logs.EquityIndexIndicator;
import com.quant_socket.models.Product;
import com.quant_socket.repos.EquityIndexIndicatorRepo;
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
public class EquityIndexIndicatorService extends SocketService{
    private final List<EquityIndexIndicator> logs = new CopyOnWriteArrayList<>();
    @Autowired
    private EquityIndexIndicatorRepo repo;
    @Autowired
    private ProductService productService;

    private String insertSql() {
        final String[] cols = new String[] {
                "eii_data_category",
                "eii_info_category",
                "eii_message_seq_number",
                "eii_isin_code",
                "eii_a_designated_number_for_an_issue",
                "eii_sec_group_id",
                "eii_eps_calculation",
                "eii_eps",
                "eii_loss_category",
                "eii_per",
                "eii_bps_calculation",
                "eii_bps",
                "eii_pbr",
                "eii_dps_calculation",
                "eii_dps",
                "eii_dividend_yield",
                "eii_market_capitalization",
                "eii_manufacturing",
                "eii_index_classification_level1",
                "eii_index_classification_level2",
                "eii_index_classification_level3",
                "eii_kospi200_sector_code1",
                "eii_kospi200_sector_code2",
                "eii_is_kospi",
                "eii_is_kosdaq",
                "eii_is_kospi100",
                "eii_is_kospi50",
                "eii_kosdaq150",
                "eii_krx100",
                "eii_krx300",
                "eii_kospi200_high_dividend_yield_index",
                "eii_krx_bbig_knew_deal_index",
                "eii_krx_second_battery_knew_deal_index",
                "eii_krx_bio_knew_deal_index",
                "eii_filler",
                "eii_end_keyword",
        };

        return "INSERT INTO equities_batch_data(" +
                String.join(",", cols) + ")" +
                "VALUES(" + String.join(",", Arrays.stream(cols).map(col -> "?").toList()) + ")";
    }

    public void addLog(EquityIndexIndicator data) {
        logs.add(data);
        final Product prod = productService.productFromIsinCode(data.getIsin_code());
        if(prod != null) {
            productService.update(data);
            sendMessage(data);
            sendMessage(data, data.getIsin_code());
        }
    }

    public void insertLogs() {
        if(!logs.isEmpty()) {
            final int result = repo.insertMany(insertSql(), new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) {
                    final EquityIndexIndicator data = logs.get(i);
                    data.setPreparedStatement(ps);
                }

                @Override
                public int getBatchSize() {
                    return logs.size();
                }
            });
            if(result > 0) logs.clear();
            log.debug("EQUITY INDEX INDICATOR INSERT COUNT : {}", result);
        }
    }
}
