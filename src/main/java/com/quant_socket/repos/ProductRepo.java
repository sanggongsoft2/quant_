package com.quant_socket.repos;

import com.quant_socket.models.Logs.prod.ProductMinute;
import com.quant_socket.models.Product;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepo extends SG_repo<Product>{

    private final String productUpdateSql = "UPDATE product SET\n" +
            "                p_face_value = ?,\n" +
            "                p_having_count = ?,\n" +
            "                p_yesterday_price = ?,\n" +
            "                p_yesterday_trading_count = ?,\n" +
            "                p_yesterday_value = ?,\n" +
            "                p_name_kr = ?,\n" +
            "                p_name_kr_abbr = ?,\n" +
            "                p_name_en = ?\n" +
            "                WHERE p_idx = ?";

    private final String productMinuteSql = "INSERT INTO product_minute (p_code, m_close, m_high, m_low, m_open, m_volume, m_pre_close)\n" +
            "VALUES(?, ?, ?, ?, ?, ?, ?)";

    private final String productDaySql = "INSERT INTO product_day (p_code, d_close, d_high, d_low, d_open, d_volume, d_pre_close, d_date)\n" +
            "SELECT p_code, m_close, m_high, m_low, m_open, (SELECT SUM(product_minute.m_volume) FROM product_minute WHERE m_date = CURDATE() AND product_minute.p_code = pm.p_code), m_pre_close, m_date FROM product_minute pm\n" +
            "WHERE (p_code, m_idx) IN (\n" +
            "    SELECT p_code, MAX(m_idx)\n" +
            "    FROM product_minute pm2\n" +
            "    GROUP BY p_code\n" +
            ")";

    private final String productWeekSql = """
INSERT INTO product_week (p_code, w_close, w_high, w_low, w_open, w_volume, w_pre_close, w_date)
SELECT p_code, d_close, d_high, d_low, d_open,
       (SELECT SUM(pd.d_volume)
        FROM product_day pd
        WHERE pd.p_code = product_day.p_code
          AND d_date BETWEEN DATE_SUB(CURDATE(), INTERVAL 1 WEEK) AND CURDATE()),
       d_pre_close, d_date
FROM product_day
WHERE (p_code, d_idx) IN (
    SELECT p_code, MAX(d_idx)
    FROM product_day
    GROUP BY p_code
);
""";

    private final String productMonthSql = "INSERT INTO product_month (p_code, m_close, m_high, m_low, m_open, m_volume, m_pre_close, m_date)\n" +
            "SELECT p_code, d_close, d_high, d_low, d_open,\n" +
            "    (SELECT SUM(d_volume) FROM product_day pd\n" +
            "        WHERE pd.p_code = product_day.p_code\n" +
            "        AND YEAR(pd.d_date) = YEAR(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))\n" +
            "        AND MONTH(pd.d_date) = MONTH(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))\n" +
            "    ), d_pre_close, d_date FROM product_day\n" +
            "WHERE (p_code, d_idx) IN (SELECT p_code, MAX(d_idx)\n" +
            "FROM product_day\n" +
            "GROUP BY p_code)";

    private final String deleteProductDaySql = "DELETE FROM product_day\n" +
            "WHERE d_crdt < DATE_SUB(CURDATE(), INTERVAL 3 MONTH);";

    public List<Product> findAll() {
        final String sql = """
WITH RecentData AS (
        SELECT p_code, w_high, w_low,
               ROW_NUMBER() OVER (PARTITION BY p_code ORDER BY w_idx DESC) AS rn
        FROM product_week
    ),
     MaxMinWClose AS (
         SELECT p_code,
                MAX(w_high) AS Max_w_high,
                MIN(w_low) AS Min_w_low
         FROM RecentData
         WHERE rn <= 52
         GROUP BY p_code
     ),
     LatestEquitiesSnapshot AS (
         SELECT *
         FROM equities_snapshot
         WHERE (eq_isin_code, eq_idx) IN (
             SELECT eq_isin_code, MAX(eq_idx) AS max_eq_idx
             FROM equities_snapshot
             WHERE eq_board_id = 'G1'
             GROUP BY eq_isin_code
         )
     ),
     LatestSecuritiesQuote AS (
         SELECT *
         FROM securities_quote
         WHERE (sq_isin_code, sq_idx) IN (
             SELECT sq_isin_code, MAX(sq_idx) AS max_sq_idx
             FROM securities_quote
             GROUP BY sq_isin_code
         )
     )
SELECT p.*,
       mmw.Max_w_high AS max_52_price,
       mmw.Min_w_low AS min_52_price,
       es.*,
       sq.*
FROM product p
         LEFT JOIN MaxMinWClose mmw ON p.p_code = mmw.p_code
         LEFT JOIN LatestEquitiesSnapshot es ON es.eq_isin_code = p.p_code
         LEFT JOIN LatestSecuritiesQuote sq ON sq.sq_isin_code = p.p_code;
""";
        return super.jt.query(sql, (rs, rn) -> new Product(rs));
    }

    public boolean update(Product prod) {
        return super.jt.update(productUpdateSql, prod.getFace_value(), prod.getHaving_count(), prod.getCurrentPrice(), prod.getTodayTradingCount(), prod.getTodayTradingValue(), prod.getName_kr(), prod.getName_kr_abbr(), prod.getName_en(), prod.getIdx()) > 0;
    }

    @Transactional
    public synchronized void insertProductMinute(BatchPreparedStatementSetter setter) {
        jt.batchUpdate(productMinuteSql, setter);
    }

    public int insertProductDay() {
        return jt.update(productDaySql);
    }

    public synchronized void insertProductWeek() {
        jt.update(productWeekSql);
    }

    public synchronized void insertProductMonth() {
        jt.update(productMonthSql);
    }

    public synchronized void deleteProductMinuteFrom3Month() {
        jt.update(deleteProductDaySql);
    }
}
