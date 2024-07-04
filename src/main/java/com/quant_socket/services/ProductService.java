package com.quant_socket.services;

import com.quant_socket.models.Logs.*;
import com.quant_socket.models.Logs.prod.ProductMinute;
import com.quant_socket.models.Product;
import com.quant_socket.repos.ProductRepo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

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
    @Getter
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

    public void updateProducts() {
        synchronized (products) {
            products.forEach(prod -> {
                if(repo.update(prod)) prod.refreshEveryday();
            });
        }
    }

    public void updateProductMinute() {
        synchronized (products) {
            final List<ProductMinute> minutes = products.stream().map(Product::getCurrentPM).toList();
            repo.insertProductMinute(new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) {
                    final ProductMinute pm = minutes.get(i);
                    if(pm.setPreparedStatement(ps)) {
                        pm.reset();
                        sendMessage(pm.toSocket(), pm.getIsinCode());
                    }
                }

                @Override
                public int getBatchSize() {
                    return minutes.size();
                }
            });
        }
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

    public void update(SecuritiesQuote data) {
        synchronized (products) {
            for(Product prod : products) {
                if(prod.getCode().equals(data.getIsin_code())) {
                    prod.update(data);
                    break;
                }
            }
        }
    }

    public void update(EquitiesSnapshot data) {
        synchronized(products) {
            for(Product prod : products) {
                if(prod.getCode().equals(data.getIsin_code())) {
                    prod.update(data);
                    break;
                }
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

    public List<Map<String, Object>> fromIsinCodes(List<String> isinCodes) {
        final List<Product> list = products.stream().filter(p -> isinCodes.contains(p.getCode())).toList();
        return list.stream()
                .sorted(Comparator.comparingLong(Product::getTodayTradingCount).reversed())
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
        data.putAll(prod.signalToMap());
        return data;
    }

    private List<Product> factoryProducts(String type) {
        List<Product> list = products;
        if(type != null) list = products.stream().filter(prod-> Objects.equals(prod.getGubun(), type)).toList();
        return list;
    }

    private String insertSql() {
        final String[] cols = new String[] {
                "p_code",
                "p_short_code",
                "p_name_kr",
                "p_name_kr_abbr",
                "p_name_en",
                "p_class",
                "p_seq_class",
                "p_team",
                "p_type",
                "p_face_value",
                "p_having_count",
        };
        return "INSERT IGNORE INTO product(" +
                String.join(",", cols) + ")" +
                "VALUES(" + String.join(",", Arrays.stream(cols).map(col -> "?").toList()) + ")";
    }

    @Transactional
    public <T> void insertLogs() {
        synchronized (logs) {
            if(!logs.isEmpty()) {
                final int result = repo.insertMany(insertSql(), new BatchPreparedStatementSetter() {
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

    public void addLog(Product prod) {
        synchronized (logs) {
            logs.add(prod);
        }
    }

    @Override
    public void addSession(WebSocketSession ws, String... isinCodes) {
        super.addSession(ws, isinCodes);
    }

    public List<Map<String, Object>> minuteChartFromCode(String isinCode) {
        return repo.getMinuteCharts(isinCode);
    }

    public List<Map<String, Object>> dayChartFromCode(String isinCode) {
        return repo.getDayChart(isinCode);
    }

    public List<Map<String, Object>> weekChartFromCode(String isinCode) {
        return repo.getWeekChart(isinCode);
    }

    public List<Map<String, Object>> monthChartFromCode(String isinCode) {
        return repo.getMonthChart(isinCode);
    }
}
