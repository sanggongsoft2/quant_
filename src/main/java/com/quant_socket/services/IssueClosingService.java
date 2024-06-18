package com.quant_socket.services;
import com.quant_socket.models.Logs.IssueClosing;
import com.quant_socket.models.Product;
import com.quant_socket.repos.EquitiesSnapshotRepo;
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
public class IssueClosingService extends SocketService{

    private final List<IssueClosing> logs = new CopyOnWriteArrayList<>();
    @Autowired
    private EquitiesSnapshotRepo repo;
    public void dataHandler(IssueClosing data) {
       logs.add(data);
    }

    @Transactional
    public void insertLogs() {
        final int result = repo.insertMany(insertSql(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                final IssueClosing ic = logs.get(i);
                ic.setPreparedStatement(ps);
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

        String insertCols = "";

        final List<String> columns = List.of(insertCols.split(","));

        sb.append("INSERT INTO issue_closing(")
                .append(insertCols)
                .append(")")
                .append("\nVALUES(");
        sb.append(String.join(",", columns.stream().map(column -> "?").toList()));
        sb.append(")");
        return sb.toString();
    }
}
