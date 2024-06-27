package com.quant_socket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@Slf4j
public abstract class SocketService{
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, Set<WebSocketSession>> isinSessions = new ConcurrentHashMap<>();

    public void addSession(WebSocketSession ws, String... isinCodes) {
        for(String isinCode : isinCodes) {
            isinSessions.computeIfAbsent(isinCode, k -> new CopyOnWriteArraySet<>());
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
            if (ws.isOpen()) {
                try {
                    ws.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
                } catch (Exception e) {
                    return true; // Remove session if sending fails
                }
                return false;
            } else {
                return true;
            }
        });

    }

    public <T> void sendMessage(T message, WebSocketSession session){
        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
            }
        } catch (Exception ignore) {
        }
    }
}
