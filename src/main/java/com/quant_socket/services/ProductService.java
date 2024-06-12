package com.quant_socket.services;

import com.quant_socket.models.Logs.*;
import com.quant_socket.models.Logs.prod.ProductMinute;
import com.quant_socket.models.Product;
import com.quant_socket.repos.ProductMinuteRepo;
import com.quant_socket.repos.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService extends SocketService{

    @Autowired
    private ProductMinuteRepo productMinuteRepo;
    @Autowired
    private ProductRepo repo;

    private final List<Product> logs = new CopyOnWriteArrayList<>();
    private final List<Product> products = new CopyOnWriteArrayList<>();

    public boolean refreshProducts(ProductRepo repo) {
        products.clear();
        return products.addAll(repo.findAll());
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
    public void updateProducts() {
        for(final Product prod: products) {
            if(repo.update(prod)) prod.refreshEveryday();
        }
    }

    @Transactional
    public void updateProductMinute() {
        final List<ProductMinute> minutes = logs.stream().map(ProductMinute::new).toList();
        final String sql = insertSql(ProductMinute.insertCols());
        final int result = productMinuteRepo.insertMany(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                final ProductMinute pm = minutes.get(i);
                pm.setPreparedStatement(ps);
                logs.get(i).refreshTradingVolumeFrom1Minute();
            }

            @Override
            public int getBatchSize() {
                return minutes.size();
            }
        });
        if(result > 0) refreshProductItems();
    }

    public void updateProductDay() {
        if(repo.insertProductDay() > 0) {
            for(Product product : products){
                repo.updateProductDay(product);
            }
        }
    }

    public void refreshProductItems() {
        products.forEach(Product::refreshTradingVolumeFrom1Minute);
    }

    public void update(EquitiesBatchData data) {
        for(Product prod : products) {
            if(prod.getCode().equals(data.getIsin_code())) {
                prod.update(data);
                break;
            }
        }
    }
    public void update(EquitiesSnapshot data) {
        for(Product prod : products) {
            if(prod.getCode().equals(data.getIsin_code())) {
                prod.update(data);
                break;
            }
        }
    }
    public void update(InvestorActivitiesEOD data) {

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

    public List<Map<String, Object>> orderAskCount(String type) {
        final List<Product> list = factoryProducts(type);
        return list.stream()
                .sorted(Comparator.comparingLong(Product::getForeignerAskCount).reversed())
                .limit(10)
                .map(this::getProduct2)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> orderBidCount(String type) {
        final List<Product> list = factoryProducts(type);
        return list.stream()
                .sorted(Comparator.comparingLong(Product::getForeignerBidCount).reversed())
                .limit(10)
                .map(this::getProduct2)
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
        return data;
    }

    private Map<String, Object> getProduct2(Product prod) {
        final Map<String, Object> data = new LinkedHashMap<>();
        data.put("name", prod.getName_kr_abbr());
        data.put("isin_code", prod.getCode());
        data.put("total_price", prod.getTotalPrice());
        data.put("compare_price_rate", prod.getComparePriceRate());
        data.put("foreign_count", prod.getForeignerBidCount());
        data.put("facility_count", prod.getFacilityBidCount());
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

    public void addLog(Product log) {
        logs.add(log);
    }
}
