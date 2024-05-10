package com.quant_socket.services;

import com.quant_socket.models.Logs.*;
import com.quant_socket.models.Logs.prod.ProductMinute;
import com.quant_socket.models.Product;
import com.quant_socket.repos.ProductMinuteRepo;
import com.quant_socket.repos.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class ProductService extends SocketService<Product>{

    @Autowired
    private ProductMinuteRepo productMinuteRepo;

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

    public void updateProductMinute() {
        final List<ProductMinute> minutes = products.stream().map(ProductMinute::new).toList();
        final int result = productMinuteRepo.insertMany(insertSql(ProductMinute.class, ProductMinute.insertCols()), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                final ProductMinute pm = minutes.get(i);
                pm.setPreparedStatement(ps);
                products.get(i).refreshTradingVolumeFrom1Minute();
            }

            @Override
            public int getBatchSize() {
                return minutes.size();
            }
        });
    }

    public void refreshProductItems() {
        products.forEach(Product::refresh);
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
}
