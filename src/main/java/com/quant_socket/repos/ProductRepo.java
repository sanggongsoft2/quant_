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
}
