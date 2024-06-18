package com.quant_socket.services;

import com.quant_socket.models.Logs.*;
import com.quant_socket.repos.SocketLogRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final LocalTime hour3half = LocalTime.of(15, 31);
    /*private final LocalTime hour3half = LocalTime.of(23, 31);*/

    public void esHandler(SocketLog sl) {
        final String msg = sl.getLog();
        final LocalTime now = LocalTime.now();
        final boolean isBefore = now.isBefore(hour3half);

        if(msg.length() >= 5) {
            final String prodCode = msg.substring(0, 5);
            switch (prodCode) {
                case "A001S", "A002S", "A003S", "A004S", "A001Q", "A001X":
                    final EquitiesBatchData data = new EquitiesBatchData(msg);
                    equitiesBatchDataService.dataHandler(data);
                    break;
                case "A301S", "A301Q", "A301X":
                    if(isBefore) securitiesOrderFilledService.dataHandler(new SecOrderFilled(msg));
                    break;
                case "B201X", "B201Q", "B201S", "B202S", "B203S", "B204S":
                    if(isBefore) equities_snapshot_handler(msg);
                    break;
                case "B601S", "B601Q", "B601X", "B702S", "B703S", "B704S":
                    if(isBefore) securitiesQuoteService.dataHandler(new SecuritiesQuote(msg));
                    break;
            }
        }
    }

    //증권 Snapshot (MM/LP호가 제외)
    private void equities_snapshot_handler(String msg) {
        final EquitiesSnapshot es = new EquitiesSnapshot(msg);
        if(es.isRealBoard()) equitiesSnapshotService.dataHandler(new EquitiesSnapshot(msg));
    }

    private String insertSql(String[] cols) {
        return "INSERT INTO socket_log(" +
                String.join(",", cols) + ")" +
                "VALUES(" + String.join(",", Arrays.stream(cols).map(col -> "?").toList()) + ")";
    }

    @Transactional
    public void insertLogs(String[] cols) {
        if(!logs.isEmpty()) {
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
            if(result > 0) logs.clear();
        }
    }

    public void addLog(SocketLog log) {
        logs.add(log);
    }
}