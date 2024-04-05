package com.quant_socket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@RequiredArgsConstructor
public abstract class SocketService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final Map<String, Set<WebSocketSession>> isinSessions = new ConcurrentHashMap<>();

    public void addSession(WebSocketSession ws) {
        sessions.add(ws);
    }
    public void addSession(WebSocketSession ws, String isinCode) {
        isinSessions.computeIfAbsent(isinCode, k -> new HashSet<>());
        isinSessions.get(isinCode).add(ws);
    }

    public void removeSession(WebSocketSession ws) {
        sessions.remove(ws);
    }
    public void removeSession(WebSocketSession ws, String isinCode) {
        if(isinSessions.get(isinCode) != null) isinSessions.get(isinCode).remove(ws);
        if(isinSessions.get(isinCode).isEmpty()) isinSessions.remove(isinCode);
    }

    public <T> void sendMessage(T message){
        sendMessage(message, this.sessions);
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
    public <T> void sendMessage(T message, String isinCode){
        final Set<WebSocketSession> sessions = isinSessions.get(isinCode);
        if(sessions != null) sendMessage(message, sessions);
    }

    public Map<String, String> extractQueryParams(String queryString) {
        Map<String, String> queryPairs = new HashMap<>();
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
}
