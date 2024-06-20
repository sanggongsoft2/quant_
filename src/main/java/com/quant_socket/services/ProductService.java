package com.quant_socket.services;

import com.quant_socket.models.Logs.*;
import com.quant_socket.models.Logs.prod.ProductMinute;
import com.quant_socket.models.Product;
import com.quant_socket.repos.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService extends SocketService{

    @Autowired
    private ProductRepo repo;

    private final List<Product> logs = new CopyOnWriteArrayList<>();
    private final List<Product> products = new CopyOnWriteArrayList<>();

    public boolean refreshProducts() {
        synchronized (products) {
            products.clear();
            return products.addAll(repo.findAll());
        }
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

    @Transactional
    public synchronized void updateProducts() {
        synchronized (products) {
            for(final Product prod: products) {
                if(repo.update(prod)) prod.refreshEveryday();
            }
        }
    }

    public synchronized void updateProductMinute() {
        synchronized (products) {
            final List<ProductMinute> minutes = products.stream().map(ProductMinute::new).toList();
            repo.insertProductMinute(new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) {
                    final ProductMinute pm = minutes.get(i);
                    if(pm.setPreparedStatement(ps)) products.get(i).refreshTradingVolumeFrom1Minute();
                }

                @Override
                public int getBatchSize() {
                    return minutes.size();
                }
            });
        }
    }

    @Transactional
    public void updateProductDay() {
        repo.insertProductDay();
    }

    public void update(EquitiesBatchData data) {
        synchronized (products) {
            for(Product prod : products) {
                if(prod.getCode().equals(data.getIsin_code())) {
                    prod.update(data);
                    break;
                }
            }
        }
    }

    public synchronized void update(SecuritiesQuote data) {
        for(Product prod : products) {
            if(prod.getCode().equals(data.getIsin_code())) {
                prod.update(data);
                break;
            }
        }
    }

    public synchronized void update(EquitiesSnapshot data) {
        for(Product prod : products) {
            if(prod.getCode().equals(data.getIsin_code())) {
                prod.update(data);
                break;
            }
        }
    }

    public List<Map<String, Object>> orderHigher(String type) {
        final List<Product> list = factoryProducts(type);
        return list.stream()
                .sorted(Comparator.comparingDouble(Product::getComparePriceRate).reversed())
                .limit(10)
                .map(this::getProduct1)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> orderLower(String type) {
        final List<Product> list = factoryProducts(type);
        return list.stream()
                .sorted(Comparator.comparingDouble(Product::getComparePriceRate))
                .limit(10)
                .map(this::getProduct1)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> orderHavingCount(String type) {
        final List<Product> list = factoryProducts(type);
        return list.stream()
                .sorted(Comparator.comparingDouble((Product prod) -> prod.getHaving_count() * prod.getCurrentPrice()).reversed())
                .limit(10)
                .map(this::getProduct1)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> orderTradingCount(String type) {
        final List<Product> list = factoryProducts(type);
        return list.stream()
                .sorted(Comparator.comparingLong(Product::getTodayTradingCount).reversed())
                .limit(10)
                .map(this::getProduct1)
                .collect(Collectors.toList());
    }

    private Map<String, Object> getProduct1(Product prod) {
        final Map<String, Object> data = new LinkedHashMap<>();
        data.put("name", prod.getName_kr_abbr());
        data.put("isin_code", prod.getCode());
        data.put("total_price", prod.getTotalPrice());
        data.put("current_price", prod.getCurrentPrice());
        data.put("open_price", prod.getOpenPrice());
        data.put("compare_price_rate", prod.getComparePriceRate());
        data.put("yesterday_price", prod.getYesterday_price());
        return data;
    }

    private List<Product> factoryProducts(String type) {
        List<Product> list = products;
        if(type != null) list = products.stream().filter(prod->prod.getGubun().equals(type)).toList();
        return list;
    }

    private String insertSql(String[] cols) {
        return "INSERT INTO product(" +
                String.join(",", cols) + ")" +
                "VALUES(" + String.join(",", Arrays.stream(cols).map(col -> "?").toList()) + ")";
    }

    @Transactional
    public <T> void insertLogs(String[] cols) {
        synchronized (logs) {
            if(!logs.isEmpty()) {
                final int result = repo.insertMany(insertSql(cols), new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) {
                        final Product data = logs.get(i);
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
    }

    public void addLog(Product log) {
        synchronized (logs) {
            logs.add(log);
        }
    }
}
