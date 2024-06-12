package com.quant_socket.services;

import com.quant_socket.models.Logs.*;
import com.quant_socket.models.SG_model;
import com.quant_socket.repos.SG_repo;
import com.quant_socket.repos.SocketLogRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDateTime;
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
    private SecuritiesOrderFilledService securitiesOrderFilledService;

    @Autowired
    private EquitiesBatchDataService equitiesBatchDataService;

    @Autowired
    private InvestActivitiesEODService investActivitiesEODService;

    private final LocalTime hour3half = LocalTime.of(15, 31);

    public void esHandler(SocketLog sl) {
        final String msg = sl.getLog();
        final LocalTime now = LocalTime.now();
        final boolean isBefore = now.isBefore(hour3half);

        if(msg.length() >= 5) {
            final String prodCode = msg.substring(0, 5);
            switch (prodCode) {
                case "A001S", "A002S", "A003S", "A004S", "A001Q", "A001X":
                    equities_batch_data_handler(msg);
                    break;
                case "C101S", "C102S", "C103S", "C104S", "C101Q", "C101X", "C101G":
                    investor_activities_per_an_issue_EOD_handler(msg);
                    break;
                case "A301S", "A301Q", "A301X":
                    if(isBefore) securities_order_filled_handler(msg);
                    break;
                case "B201X", "B201Q", "B201S", "B202S", "B203S", "B204S":
                    /*equities_snapshot_handler(msg);*/
                    if(isBefore) equities_snapshot_handler(msg);
                    break;
            }
        }
    }
    //종목별 투자자별 종가통계
    private void investor_activities_per_an_issue_EOD_handler(String msg) {
        final InvestorActivitiesEOD iae = new InvestorActivitiesEOD(msg);
        investActivitiesEODService.dataHandler(iae);
    }

    //증권 체결
    private void securities_order_filled_handler(String msg) {
        final SecOrderFilled sef = new SecOrderFilled(msg);
        securitiesOrderFilledService.dataHandler(sef);
    }

    //증권 Snapshot (MM/LP호가 제외)
    private void equities_snapshot_handler(String msg) {
        final EquitiesSnapshot es = new EquitiesSnapshot(msg);
        equitiesSnapshotService.dataHandler(es);
    }

    //증권 종목 정보
    private void equities_batch_data_handler(String msg) {
        final EquitiesBatchData ebd = new EquitiesBatchData(msg);
        equitiesBatchDataService.dataHandler(ebd);
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