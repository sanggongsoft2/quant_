package com.quant_socket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.SG_model;
import com.quant_socket.repos.SG_repo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.URI;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@Slf4j
public abstract class SocketService<T extends SG_model>{
    private final ObjectMapper mapper = new ObjectMapper();
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final Map<String, Set<WebSocketSession>> isinSessions = new ConcurrentHashMap<>();
    private final List<T> logs = new CopyOnWriteArrayList<>();
    public void addSession(WebSocketSession ws) {
        sessions.add(ws);
    }
    public void addSession(WebSocketSession ws, String... isinCodes) {
        for(String isinCode : isinCodes) {
            isinSessions.computeIfAbsent(isinCode, k -> new HashSet<>());
            isinSessions.get(isinCode).add(ws);
        }
    }

    public void removeSession(WebSocketSession ws) {
        sessions.remove(ws);
    }
    public void removeSession(WebSocketSession ws, String... isinCodes) {
        for(String isinCode : isinCodes) {
            if(isinSessions.get(isinCode) != null) isinSessions.get(isinCode).remove(ws);
            if(isinSessions.get(isinCode).isEmpty()) isinSessions.remove(isinCode);
        }
    }

    public <T> void sendMessage(T message){
        sendMessage(message, this.sessions);
    }
    public <T> void sendMessage(T message, String... isinCodes){
        for(String isinCode : isinCodes) {
            Set<WebSocketSession> sessions = isinSessions.get(isinCode);
            if(sessions != null) sendMessage(message, sessions);
        }
    }

    public Map<String, String> extractQueryParams(String queryString) {
        Map<String, String> queryPairs = new LinkedHashMap<>();
        String[] pairs = queryString.split("&");

        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = pair.substring(0, idx);
            String value = pair.substring(idx + 1);

            queryPairs.put(key, value);
        }
        return queryPairs;
    }

    public String getQueryValue(String queryString, String key) {
        final Map<String, String> data = extractQueryParams(queryString);
        return data.get(key);
    }

    private <T> void sendMessage(T message, Set<WebSocketSession> sessions){
        sessions.removeIf(ws -> {
            if(ws.isOpen()) {
                try {
                    ws.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return false;
            } else {
                return true;
            }
        });
    }

    public void afterConnectionEstablished(WebSocketSession ws) throws IOException {
        final URI uri = ws.getUri();
        if(uri != null && uri.getQuery() != null) {
            final String isinCodeString = getQueryValue(uri.getQuery(), "isin_code");
            if(isinCodeString != null) {
                final String[] isinCodes = isinCodeString.split(",");
                addSession(ws, isinCodes);
            }
        } else {
            ws.close();
        }
    }

    public void afterConnectionClosed(WebSocketSession ws, CloseStatus status) throws Exception {
        final URI uri = ws.getUri();
        if(uri != null && uri.getQuery() != null) {
            final String isinCodeString = getQueryValue(uri.getQuery(), "isin_code");
            if(isinCodeString != null) {
                final String[] isinCodes = isinCodeString.split(",");
                if (isinCodes.length > 0) removeSession(ws, isinCodes);

            }
        }
    }

    protected String insertSql(Class<? extends SG_model> clazz, String[] cols) {
        final String tableName = getTableName(clazz);

        return "INSERT INTO "+tableName+"(" +
                String.join(",", cols) + ")" +
                "VALUES(" + String.join(",", Arrays.stream(cols).map(col -> "?").toList()) + ")";
    }

    protected String getTableName(Class<? extends SG_model> clazz) {
        String tableName = clazz.getName();
        if(clazz.isAnnotationPresent(SG_table.class)) {
            final SG_table sgTable = clazz.getAnnotation(SG_table.class);
            tableName = sgTable.name();
        }
        return tableName;
    }

    @Transactional
    public void insertLogs(String[] cols, Class<T> clazz, SG_repo<T> repo) {
        if(!logs.isEmpty()) {
            final int result = repo.insertMany(insertSql(clazz, cols), new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) {
                    final T data = logs.get(i);
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

    protected void addLog(T log) {
        logs.add(log);
    }
}
