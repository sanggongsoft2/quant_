package com.quant_socket.services;

import com.quant_socket.models.Logs.*;
import com.quant_socket.models.Product;
import com.quant_socket.repos.SocketLogRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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
    private ProductService productService;

    private final List<SocketLog> logs = new CopyOnWriteArrayList<>();
    private AtomicInteger snapshotIdx = new AtomicInteger(12285033);
    private AtomicInteger secorderIdx = new AtomicInteger(12285033);
    private AtomicInteger batchDataIdx = new AtomicInteger(12285033);
    private AtomicInteger investorEODIdx = new AtomicInteger(12285033);

    public void addLog(SocketLog sl) {
        logs.add(sl);
    }

    private String insertSql() {
        return "INSERT INTO socket_log(SL_log, SL_remote_url, SL_port)" +
                "VALUES(?, ?, ?)";
    }

    @Scheduled(fixedRate = 60000)
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

    /*@Scheduled(fixedRate = 2000)
    public void sendMessage() {
        snapshotHandler();
        secOrderHandler();
        batchDataHandler();
    }*/


    private void snapshotHandler() {
        final SocketLog data = repo.findOne("B2", "KR7005930003", snapshotIdx.longValue());
        if(data != null) {
            log.info(data.toString());
            try {
                final EquitiesSnapshot es = new EquitiesSnapshot(data.getLog());
                final Product prod = productService.productFromIsinCode(es.getIsin_code());
                if(prod != null) {
                    equitiesSnapshotService.sendMessage(es.toSocket(prod));
                    equitiesSnapshotService.sendMessage(es.toDetailsSocket(prod), es.getIsin_code());
                }
            } catch (Exception ignore) {

            } finally {
                snapshotIdx.set(Math.toIntExact(data.getIdx()));
            }
        } else {
            snapshotIdx.set(12285033);
        }
    }

    private void secOrderHandler() {
        final SocketLog data = repo.findOne("A3", secorderIdx.longValue());

        if(data != null) {
            log.info(data.toString());
            try {
                final SecOrderFilled sof = new SecOrderFilled(data.getLog());
                final Product prod = productService.productFromIsinCode(sof.getIsin_code());
                if(prod != null) {
                    securitiesOrderFilledService.sendMessage(sof.toSocket(prod));
                    securitiesOrderFilledService.sendMessage(sof.toSocket(prod), sof.getIsin_code());
                }
            } catch (Exception ignore) {

            } finally {
                secorderIdx.set(Math.toIntExact(data.getIdx()));
            }
        } else {
            secorderIdx.set(12285033);
        }
    }

    private void batchDataHandler() {
        final SocketLog data = repo.findOne("A00", batchDataIdx.longValue());

        if(data != null) {
            log.info(data.toString());
            try {
                final EquitiesBatchData ebd = new EquitiesBatchData(data.getLog());
                final Product prod = productService.productFromIsinCode(ebd.getIsin_code());
                if(prod != null) {
                    equitiesBatchDataService.sendMessage(ebd.toSocket(prod));
                    equitiesBatchDataService.sendMessage(ebd.toSocket(prod), ebd.getIsin_code());
                }
            } catch (Exception ignore) {

            } finally {
                batchDataIdx.set(Math.toIntExact(data.getIdx()));
            }
        } else {
            batchDataIdx.set(12285033);
        }
    }

    private void investorEODHandler() {
        final SocketLog data = repo.findOne("C10", investorEODIdx.longValue());

        if(data != null) {
            try {
                final InvestorActivitiesEOD eod = new InvestorActivitiesEOD(data.getLog());
                final Product prod = productService.productFromIsinCode(eod.getIsin_code());
                if(prod != null) {
                    investActivitiesEODService.sendMessage(eod.toSocket(prod));
                    investActivitiesEODService.sendMessage(eod.toSocket(prod), eod.getIsin_code());
                }
            } catch (Exception ignore) {

            } finally {
                investorEODIdx.set(Math.toIntExact(data.getIdx()));
            }
        } else {
            investorEODIdx.set(12285033);
        }
    }
}
