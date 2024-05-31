package com.quant_socket.repos;

import com.quant_socket.models.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        return super.jt.update(sql, prod.getFace_value(), prod.getHaving_count(), prod.getCurrentPrice(), prod.getTodayTradingCount(), prod.getTodayTradingValue(), prod.getName_en(), prod.getIdx()) > 0;
    }

    public void updateProductDay() {
        final String sql = "INSERT INTO product_day (p_code, d_close, d_high, d_low, d_open, d_volume, d_pre_close, d_date)\n" +
                "SELECT p_code, m_close, m_high, m_low, m_open, (SELECT SUM(product_minute.m_volume) FROM product_minute WHERE m_date = CURDATE() AND product_minute.p_code = pm.p_code), m_pre_close, m_date FROM product_minute pm\n" +
                "WHERE (p_code, m_idx) IN (\n" +
                "    SELECT p_code, MAX(m_idx)\n" +
                "    FROM product_minute pm2\n" +
                "    GROUP BY p_code\n" +
                ")";
        jt.update(sql);
    }

    public void updateProductWeek() {
        final String sql = "INSERT INTO product_week (p_code, w_close, w_high, w_low, w_open, w_volume, w_pre_close, w_date)\n" +
                "SELECT p_code, d_close, d_high, d_low, d_open, (SELECT SUM(pd.d_volume) FROM product_day pd WHERE pd.p_code = product_day.p_code AND d_date BETWEEN DATE_SUB(CURDATE(), INTERVAL 1 WEEK) AND CURDATE()), d_pre_close, d_date FROM product_day\n" +
                "WHERE (p_code, d_idx) IN (\n" +
                "    SELECT p_code, MAX(d_idx)\n" +
                "    FROM product_day\n" +
                "    GROUP BY p_code\n" +
                ")";
        jt.update(sql);
    }

    public void updateProductMonth() {
        final String sql = "INSERT INTO product_month (p_code, m_close, m_high, m_low, m_open, m_volume, m_pre_close, m_date)\n" +
                "SELECT p_code, d_close, d_high, d_low, d_open,\n" +
                "    (SELECT SUM(d_volume) FROM product_day pd\n" +
                "        WHERE pd.p_code = product_day.p_code\n" +
                "        AND YEAR(pd.d_date) = YEAR(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))\n" +
                "        AND MONTH(pd.d_date) = MONTH(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))\n" +
                "    ), d_pre_close, d_date FROM product_day\n" +
                "WHERE (p_code, d_idx) IN (SELECT p_code, MAX(d_idx)\n" +
                "FROM product_day\n" +
                "GROUP BY p_code)";
        jt.update(sql);
    }

    public void deleteProductMinuteFrom3Month() {
        final String sql = "DELETE FROM product_day\n" +
                "WHERE d_crdt < DATE_SUB(CURDATE(), INTERVAL 3 MONTH);";
        jt.update(sql);
    }
}
