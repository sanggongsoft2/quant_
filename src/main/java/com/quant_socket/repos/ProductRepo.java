package com.quant_socket.repos;

import com.quant_socket.models.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepo extends SG_repo<Product>{

    public List<Product> findAll() {
        final String sql = "SELECT * FROM product";
        return super.jt.query(sql, (rs, rn) -> new Product(rs));
    }

    public boolean update(Product prod) {
        final String sql = "UPDATE product SET " +
                "p_face_value = ?, " +
                "p_having_count = ?, " +
                "p_yesterday_price = ?, " +
                "p_yesterday_trading_count = ?, " +
                "p_yesterday_value = ?, " +
                "p_name_en = ?" +
                " WHERE p_idx = ?";
        return super.jt.update(sql, prod.getFace_value(), prod.getHaving_count(), prod.getYesterday_price(), prod.getYesterday_trading_count(), prod.getYesterday_value(), prod.getName_en(), prod.getIdx()) > 0;
    }
}
