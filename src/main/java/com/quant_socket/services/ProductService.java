package com.quant_socket.services;

import com.quant_socket.models.Logs.*;
import com.quant_socket.models.Product;
import com.quant_socket.repos.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService extends SocketService{
    @Autowired
    private final ProductRepo repo;

    private final List<Product> products = new CopyOnWriteArrayList<>();

    private final List<Product> insertProducts = new CopyOnWriteArrayList<>();

    public boolean refreshProducts() {
        products.clear();
        return this.products.addAll(repo.findAll());
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

    public void refreshProductItems() {
        products.forEach(Product::refresh);
    }
    public void update(EquitiesBatchData data) {
        for(Product prod : products) {
            if(prod.getCode().equals(data.getIsin_code())) {
                prod.update(data);
                repo.update(prod);
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
    public void update(EquityIndexIndicator data) {
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
    public void update(SecOrderFilled data) {
        for(Product prod : products) {
            if(prod.getCode().equals(data.getIsin_code())) {
                prod.update(data);
                break;
            }
        }
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
                "p_yesterday_price",
                "p_yesterday_value",
                "p_yesterday_trading_count",
                "p_status"
        };

        String[] questionMarks = new String[cols.length];
        Arrays.fill(questionMarks, "?");

        return "INSERT INTO product(" +
                String.join(",", cols) + ")" +
                "VALUES(" + String.join(",", questionMarks) + ")";
    }

    public void insertProducts() {
        if(!insertProducts.isEmpty()) {
            final int result = repo.insertMany(insertSql(), new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) {
                    final Product data = insertProducts.get(i);
                    data.setPreparedStatement(ps);
                }

                @Override
                public int getBatchSize() {
                    return insertProducts.size();
                }
            });
            if(result > 0) {
                insertProducts.clear();
                refreshProducts();
            }
            log.info("PRODUCT INSERT COUNT : {}", result);
        }
    }

    public void addProduct(Product product) {
        insertProducts.add(product);
    }
}
