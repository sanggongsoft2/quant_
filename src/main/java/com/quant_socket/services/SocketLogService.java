package com.quant_socket.services;

import com.quant_socket.models.Logs.*;
import com.quant_socket.repos.SocketLogRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocketLogService {

    @Autowired
    private SocketLogRepo repo;

    @Autowired
    private EquitiesSnapshotService equitiesSnapshotService;

    @Autowired
    private SecuritiesOrderFilledService securitiesOrderFilledService;

    @Autowired
    private EquitiesBatchDataService equitiesBatchDataService;

    @Autowired
    private InvestActivitiesEODService investActivitiesEODService;

    @Autowired
    private EquityIndexIndicatorService equityIndexIndicatorService;

    @Autowired
    private SeqQuoteService seqQuoteService;

    private final List<SocketLog> logs = new CopyOnWriteArrayList<>();

    public void addLog(SocketLog sl) {
        logs.add(sl);
    }

    private String insertSql() {
        return "INSERT INTO socket_log(SL_log, SL_remote_url, SL_port, SL_error)" +
                "VALUES(?, ?, ?, ?)";
    }

    public void insertLogs() {
        if(!logs.isEmpty()) {
            final int result = repo.insertMany(insertSql(), new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    final SocketLog sl = logs.get(i);
                    ps.setString(1, sl.getLog());
                    ps.setString(2, sl.getRemote_url());
                    ps.setInt(3, sl.getPort());
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

    public void esHandler(String msg) {
        if(msg.length() >= 5) {
            final String prodCode = msg.substring(0, 5);
            switch (prodCode) {
                case "A001S":
                case "A002S":
                case "A003S":
                case "A004S":
                case "A001Q":
                case "A001X":
                    equities_batch_data_handler(msg);
                    break;
                case "C101S":
                case "C102S":
                case "C103S":
                case "C104S":
                case "C101Q":
                case "C101X":
                case "C101G":
                    investor_activities_per_an_issue_EOD_handler(msg);
                    break;
                case "A301S":
                case "A301Q":
                case "A301X":
                    securities_order_filled_handler(msg);
                    break;
                case "B201X":
                case "B201Q":
                case "B201S":
                    equities_snapshot_handler(msg);
                    break;
                case "B202S":
                case "B203S":
                case "B204S":
                    equities_snapshot_handler2(msg);
                    break;
                case "CA01S":
                case "CA01Q":
                    equity_index_indicator_handler(msg);
                    break;
                case "B702S":
                case "B703S":
                case "B704S":
                    seq_quote_handler(msg);
                    break;
            }
        }
    }

    //종목별 투자자별 종가통계
    private void investor_activities_per_an_issue_EOD_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{96})")) {
            if (chunk.length() >= 96) {
                final InvestorActivitiesEOD iae = new InvestorActivitiesEOD(msg);
                investActivitiesEODService.addLog(iae);
            }
        }
    }

    //증권 체결
    private void securities_order_filled_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{186})")) if(chunk.length() >= 186) {
            final SecOrderFilled sef = new SecOrderFilled(msg);
            securitiesOrderFilledService.addLog(sef);
        }
    }

    //증권 Snapshot (MM/LP호가 제외)
    private void equities_snapshot_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{650})")) {
            if(chunk.length() >= 650) {
                final EquitiesSnapshot es = new EquitiesSnapshot(chunk);
                equitiesSnapshotService.addLog(es);
            }
        }
    }
    //증권 Snapshot (MM/LP호가 포함)
    private void equities_snapshot_handler2(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{900})")) {
            if(chunk.length() >= 900) {
                final EquitiesSnapshot es = new EquitiesSnapshot(chunk);
                equitiesSnapshotService.addLog(es);
            }
        }
    }

    //증권 지수지표
    private void equity_index_indicator_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{185})")) {
            if (chunk.length() >= 185) {
                final EquityIndexIndicator eii = new EquityIndexIndicator(chunk);
                equityIndexIndicatorService.addLog(eii);
            }
        }
    }

    //증권 종목 정보
    private void equities_batch_data_handler(String msg) {
        log.info("MSG LENGTH: {}", msg.length());
        for(String chunk : msg.split("(?<=\\\\G.{620})")) {
            if(chunk.length() >= 620) {
                final EquitiesBatchData ebd = new EquitiesBatchData(chunk);
                equitiesBatchDataService.addLog(ebd);
            }
        }
    }

    private void seq_quote_handler(String msg) {
        for(String chunk : msg.split("(?<=\\\\G.{795})")) {
            if(chunk.length() >= 795) {
                final SeqQuote data = new SeqQuote(chunk);
                seqQuoteService.addLog(data);
            }
        }
    }
}
