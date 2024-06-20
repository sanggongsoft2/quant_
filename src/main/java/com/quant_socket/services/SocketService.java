package com.quant_socket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.SG_model;
import com.quant_socket.repos.SG_repo;
import lombok.Getter;
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
public abstract class SocketService{
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, Set<WebSocketSession>> isinSessions = new ConcurrentHashMap<>();
    public void addSession(WebSocketSession ws, String... isinCodes) {
        for(String isinCode : isinCodes) {
            isinSessions.computeIfAbsent(isinCode, k -> new HashSet<>());
            isinSessions.get(isinCode).add(ws);
        }
    }
    public <T> void sendMessage(T message, String... isinCodes){
        for(String isinCode : isinCodes) {
            final Set<WebSocketSession> sessions = isinSessions.get(isinCode);
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
            synchronized (ws) {
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
            }
        });
    }
}
