package com.quant_socket.services;

import com.quant_socket.models.Logs.*;
import com.quant_socket.repos.SocketLogRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocketLogService extends SocketService{

    @Autowired
    private SocketLogRepo repo;
    private final List<SocketLog> logs = new CopyOnWriteArrayList<>();

    @Autowired
    private EquitiesSnapshotService equitiesSnapshotService;

    @Autowired
    private SecuritiesQuoteService securitiesQuoteService;

    @Autowired
    private SecuritiesOrderFilledService securitiesOrderFilledService;

    @Autowired
    private EquitiesBatchDataService equitiesBatchDataService;

    @Autowired
    private IssueClosingService issueClosingService;

    private final LocalTime hour3half = LocalTime.of(15, 31);
    private final LocalTime hour9 = LocalTime.of(9, 0);

    public void esHandler(String msg) {
        final LocalTime now = LocalTime.now();
        final boolean isCompare = now.isBefore(hour3half) && now.isAfter(hour9);

        if(msg.length() >= 5) {
            final String dataCategory = msg.substring(0, 2);
            final String infoCategory = msg.substring(2, 5);
            switch (infoCategory) {
                case "01S", "01Q", "01X", "03S":
                    switch (dataCategory) {
                        case "A0":
                            final EquitiesBatchData ebd = new EquitiesBatchData(msg);
                            equitiesBatchDataService.dataHandler(ebd);
                            break;
                        case "A3":
                            if(isCompare) securitiesOrderFilledService.dataHandler(new SecOrderFilled(msg));
                            break;
                        case "B2":
                            if(isCompare) {
                                final EquitiesSnapshot es;
                                if(infoCategory.equals("03S")) {
                                    es = new EquitiesSnapshot(msg, true);
                                } else {
                                    es = new EquitiesSnapshot(msg);
                                }
                                if(es.isRealBoard()) equitiesSnapshotService.dataHandler(es);
                            }
                            break;
                        case "B6":
                            if(isCompare) {
                                final SecuritiesQuote sq = new SecuritiesQuote(msg);
                                if(sq.isRealBoard()) securitiesQuoteService.dataHandler(sq);
                            }
                            break;
                        case "B7":
                            if(isCompare) {
                                final SecuritiesQuote sq = new SecuritiesQuote(msg, true);
                                if(sq.isRealBoard()) securitiesQuoteService.dataHandler(sq);
                            }
                            break;
                        case "A6":
                            final SocketLog sl = new SocketLog();
                            sl.setLog(msg);
                            sl.setPort(8080);
                            sl.setRemote_url("127.0.0.1");
                            addLog(sl);
                            final IssueClosing ic = new IssueClosing(msg);
                            if(ic.isRealBoard()) issueClosingService.dataHandler(ic);
                    }
            }
        }
    }

    private String insertSql(String[] cols) {
        return "INSERT IGNORE INTO socket_log(" +
                String.join(",", cols) + ")" +
                "VALUES(" + String.join(",", Arrays.stream(cols).map(col -> "?").toList()) + ")";
    }

    @Transactional
    public void insertLogs(String[] cols) {
        if (!logs.isEmpty()) {
            final int result = repo.insertMany(insertSql(cols), new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) {
                    final SocketLog data = logs.get(i);
                    data.setPreparedStatement(ps);
                }

                @Override
                public int getBatchSize() {
                    return logs.size();
                }
            });
            if (result > 0) logs.clear();
        }
    }

    public void addLog(SocketLog sl) {
        logs.add(sl);
    }
}