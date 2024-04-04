package com.quant_socket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quant_socket.models.Logs.EquitiesBatchData;
import com.quant_socket.models.Product;
import com.quant_socket.repos.EquitiesBatchDataRepo;
import com.quant_socket.repos.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquitiesSnapshotService {

    private final ProductRepo productRepo;

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

    @Scheduled(cron = "0 0 9 * * ?")
    public void refreshProductItems() {
        products.forEach(Product::refreshProduct);
    }

    public void updateProductCount(String isinCode, String type, long count) {
        for(Product prod : products) {
            if(prod.getCode().equals(isinCode)) {
                prod.updateTodayCount(isinCode, type, count);
                break;
            }
        }
    }

    public void updateProductTradingList(Double tradingPrice, long tradingCount) {
        for(Product prod : products) {
            prod.updateTradingList(tradingPrice, tradingCount);
            break;
        }
    }

    public void updateProductFromBatchData(EquitiesBatchData ebd) {
        for(Product prod : products) {
            if(prod.getCode().equals(ebd.getIsin_code())) {
                prod.setFace_value(ebd.getPar_value());
                prod.setHaving_count(ebd.getNumber_of_listed_shares());
                prod.setYesterday_price(ebd.getYes_closing_price());
                prod.setYesterday_trading_count(ebd.getYes_accu_trading_amount());
                prod.setYesterday_value(ebd.getYes_accu_trading_value());
                productRepo.update(prod);
                break;
            }
        }
    }
}
