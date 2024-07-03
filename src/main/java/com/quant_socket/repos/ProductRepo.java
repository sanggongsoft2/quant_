package com.quant_socket.repos;

import com.quant_socket.models.Product;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepo extends SG_repo<Product>{

    public List<Product> findAll() {
        final String sql = """
    SELECT p.*,
          c52w.max_52_price,
          c52w.min_52_price,
          a5d.closing_price avg_5_day,
          a20d.closing_price avg_20_day,
          es.*,
          sq.*,
          rcp.closing_price,
          rcp.trading_volume
   FROM product p
            LEFT JOIN recent_equities_snapshot es ON eq_isin_code = p_code
            LEFT JOIN recent_securities_quote sq ON sq_isin_code = p_code
            LEFT JOIN compare_52_week c52w ON c52w.isin_code = p_code
            LEFT JOIN recent_closing_price rcp ON rcp.isin_code = p_code
            LEFT JOIN avg_5_day a5d ON a5d.isin_code = p_code
         LEFT JOIN avg_20_day a20d ON a20d.isin_code = p_code
""";
        return super.jt.query(sql, (rs, rn) -> new Product(rs));
    }

    public boolean update(Product prod) {
        String productUpdateSql = "UPDATE product SET\n" +
                "                p_face_value = ?,\n" +
                "                p_having_count = ?,\n" +
                "                p_yesterday_price = ?,\n" +
                "                p_yesterday_trading_count = ?,\n" +
                "                p_yesterday_value = ?,\n" +
                "                p_name_kr = ?,\n" +
                "                p_name_kr_abbr = ?,\n" +
                "                p_name_en = ?\n" +
                "                WHERE p_idx = ?";
        return super.jt.update(productUpdateSql, prod.getFace_value(), prod.getHaving_count(), prod.getCurrentPrice(), prod.getTodayTradingCount(), prod.getTodayTradingValue(), prod.getName_kr(), prod.getName_kr_abbr(), prod.getName_en(), prod.getIdx()) > 0;
    }

    @Transactional
    public void insertProductMinute(BatchPreparedStatementSetter setter) {
        String productMinuteSql = "INSERT IGNORE INTO product_minute (p_code, m_close, m_high, m_low, m_open, m_volume, m_pre_close)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        jt.batchUpdate(productMinuteSql, setter);
    }

    public int insertProductDay() {
        String productDaySql = "INSERT IGNORE INTO product_day (p_code, d_close, d_high, d_low, d_open, d_volume, d_pre_close, d_date)\n" +
                "SELECT p_code, m_close, m_high, m_low, m_open, (SELECT SUM(product_minute.m_volume) FROM product_minute WHERE m_date = CURDATE() AND product_minute.p_code = pm.p_code), m_pre_close, m_date FROM product_minute pm\n" +
                "WHERE (p_code, m_idx) IN (\n" +
                "    SELECT p_code, MAX(m_idx)\n" +
                "    FROM product_minute pm2\n" +
                "    GROUP BY p_code\n" +
                ")";
        return jt.update(productDaySql);
    }

    public void insertProductWeek() {
        String productWeekSql = """
                INSERT IGNORE INTO product_week (p_code, w_close, w_high, w_low, w_open, w_volume, w_pre_close, w_date)
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
        jt.update(productWeekSql);
    }

    public void insertProductMonth() {
        String productMonthSql = "INSERT IGNORE INTO product_month (p_code, m_close, m_high, m_low, m_open, m_volume, m_pre_close, m_date)\n" +
                "SELECT p_code, d_close, d_high, d_low, d_open,\n" +
                "    (SELECT SUM(d_volume) FROM product_day pd\n" +
                "        WHERE pd.p_code = product_day.p_code\n" +
                "        AND YEAR(pd.d_date) = YEAR(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))\n" +
                "        AND MONTH(pd.d_date) = MONTH(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))\n" +
                "    ), d_pre_close, d_date FROM product_day\n" +
                "WHERE (p_code, d_idx) IN (SELECT p_code, MAX(d_idx)\n" +
                "FROM product_day\n" +
                "GROUP BY p_code)";
        jt.update(productMonthSql);
    }

    public void deleteProductMinuteFrom3Month() {
        String deleteProductDaySql = "DELETE FROM product_day\n" +
                "WHERE d_crdt < DATE_SUB(CURDATE(), INTERVAL 3 MONTH);";
        jt.update(deleteProductDaySql);
    }

    public List<Map<String, Object>> getMinuteCharts(String isinCode) {
        final String sql = """
                SELECT
                    *,
                    UNIX_TIMESTAMP(CONCAT(m_date, ' ', m_time)) * 1000 datetime
                FROM product_minute
                WHERE p_code = ?
                """;
        return jt.query(sql, (rs, rn) -> {
            final Map<String, Object> data = new LinkedHashMap<>();
            data.put("Date", rs.getLong("datetime"));
            data.put("Close", rs.getDouble("m_close"));
            data.put("High", rs.getDouble("m_high"));
            data.put("Low", rs.getDouble("m_low"));
            data.put("Open", rs.getDouble("m_pre_close"));
            data.put("Volume", rs.getLong("m_volume"));
            return data;
        }, isinCode);
    }

    public List<Map<String, Object>> getDayChart(String code) {
        final String sql = """
                select *, UNIX_TIMESTAMP(ic_date) * 1000 datetime
                from issue_closing
                where ic_isin_code = ? order by ic_idx DESC
                """;
        return jt.query(sql, (rs, rn) -> {
            final Map<String, Object> data = new LinkedHashMap<>();
            data.put("Date", rs.getLong("datetime"));
            data.put("Close", rs.getDouble("ic_closing_price"));
            data.put("High", rs.getDouble("ic_high_price"));
            data.put("Low", rs.getDouble("ic_low_price"));
            data.put("Open", rs.getDouble("ic_open_price"));
            data.put("Volume", rs.getLong("ic_trading_volume"));
            return data;
        }, code);
    }

    public List<Map<String, Object>> getWeekChart(String code) {
        final String sql = """
               
SELECT UNIX_TIMESTAMP(max_date) * 1000 AS datetime,
           ic_date,
           week_number,
           ic_closing_price,
           high_price,
           low_price,
           first_ic_open_price AS ic_open_price,
           volume
    FROM issue_closing ic
             JOIN (
        SELECT ic_isin_code,
               WEEK(ic_date, 1) AS week_number,
               MAX(ic_date) AS max_date,
               MAX(ic_high_price) AS high_price,
               MIN(ic_low_price) AS low_price,
               SUM(ic_trading_volume) AS volume,
               MIN(ic_date) AS min_date
        FROM issue_closing
        WHERE ic_isin_code = ?
        GROUP BY ic_isin_code, week_number
    ) sub ON ic.ic_isin_code = sub.ic_isin_code AND ic.ic_date = sub.max_date
             JOIN (
        SELECT ic_isin_code, ic_date AS first_date, ic_open_price AS first_ic_open_price
        FROM issue_closing
    ) first_day ON sub.ic_isin_code = first_day.ic_isin_code AND sub.min_date = first_day.first_date
    WHERE ic.ic_isin_code = ?;
                                        
                """;
        return jt.query(sql, (rs, rn) -> {
            final Map<String, Object> data = new LinkedHashMap<>();
            data.put("Date", rs.getLong("datetime"));
            data.put("Close", rs.getDouble("ic_closing_price"));
            data.put("High", rs.getDouble("high_price"));
            data.put("Low", rs.getDouble("low_price"));
            data.put("Open", rs.getDouble("ic_open_price"));
            data.put("Volume", rs.getLong("volume"));
            return data;
        }, code, code);
    }

    public List<Map<String, Object>> getMonthChart(String code) {
        final String sql = """
               
SELECT UNIX_TIMESTAMP(max_date) * 1000 AS datetime,
       ic_date,
       ic_closing_price,
       high_price,
       low_price,
       first_ic_open_price AS ic_open_price,
       volume
FROM issue_closing ic
         JOIN (
    SELECT ic_isin_code,
           MONTH(ic_date) AS month_number,
           MAX(ic_date) AS max_date,
           MAX(ic_high_price) AS high_price,
           MIN(ic_low_price) AS low_price,
           SUM(ic_trading_volume) AS volume,
           MIN(ic_date) AS min_date
    FROM issue_closing
    WHERE ic_isin_code = ?
    GROUP BY ic_isin_code, month_number
) sub ON ic.ic_isin_code = sub.ic_isin_code AND ic.ic_date = sub.max_date
         JOIN (
    SELECT ic_isin_code, ic_date AS first_date, ic_open_price AS first_ic_open_price
    FROM issue_closing
) first_day ON sub.ic_isin_code = first_day.ic_isin_code AND sub.min_date = first_day.first_date
WHERE ic.ic_isin_code = ?;
                """;
        return jt.query(sql, (rs, rn) -> {
            final Map<String, Object> data = new LinkedHashMap<>();
            data.put("Date", rs.getLong("datetime"));
            data.put("Close", rs.getDouble("ic_closing_price"));
            data.put("High", rs.getDouble("high_price"));
            data.put("Low", rs.getDouble("low_price"));
            data.put("Open", rs.getDouble("ic_open_price"));
            data.put("Volume", rs.getLong("volume"));
            return data;
        }, code, code);
    }
}

