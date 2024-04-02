package com.quant_socket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quant_socket.models.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquitiesSnapshotService {
    private final ObjectMapper mapper;
    private final List<Product> products = new CopyOnWriteArrayList<>();
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final Map<String, Set<WebSocketSession>> isinSessions = new ConcurrentHashMap<>();

    public boolean addProducts(List<Product> products) {
        return this.products.addAll(products);
    }

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
    public <T> void sendMessage(T message, Set<WebSocketSession> sessions){
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

    public Product productFromIsinCode(String isinCode) {
        Product prod = null;
        for(Product _prod : products) {
            if(_prod.getCode().equals(isinCode)) {
                prod = _prod;
                break;
            }
        }
        return prod;
    }

    public void refreshProducts() {
        products.forEach(prod -> {
            prod.setTodayBidCount(0);
            prod.setTodayAskCount(0);
        });
    }

    public void updateProductCount(String isinCode, String type, long count) {
        for(Product prod : products) if(prod.getCode().equals(isinCode)) {
            switch (type) {
                case "0":
                    break;
                case "1":
                    prod.setTodayAskCount(prod.getTodayAskCount() + count);
                    break;
                case "2":
                    prod.setTodayBidCount(prod.getTodayBidCount() + count);
            }
            break;
        }
    }
}
