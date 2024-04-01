package com.quant_socket.services;

import com.quant_socket.models.Logs.SocketLog;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class SocketLogService {

    @Autowired
    private SocketLogRepo repo;

    private final List<SocketLog> logs = new CopyOnWriteArrayList<>();

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
            log.debug("INSERT COUNT : {}", result);
        }
    }
}
