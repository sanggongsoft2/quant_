package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.Product;
import com.quant_socket.models.SG_model;
import com.quant_socket.services.ProductService;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SG_table(name = "equities_batch_data")
@Getter
@Setter
public class InvestorActivitiesEOD extends SG_model {

    @SG_idx
    @SG_column(dbField = "iae_idx")
    private Long idx;
    @SG_column(dbField = "iae_data_category")
    private String data_category;
    @SG_column(dbField = "iae_info_category")
    private String info_category;
    @SG_column(dbField = "iae_isin_code")
    private String isin_code;
    @SG_column(dbField = "iae_a_designated_number_for_an_issue")
    private int a_designated_number_for_an_issue = 0;
    @SG_column(dbField = "iae_investor_code")
    private String investor_code;
    @SG_column(dbField = "iae_accu_ask_trading_volume")
    private long accu_ask_trading_volume = 0;
    @SG_column(dbField = "iae_accu_ask_trading_value")
    private float accu_ask_trading_value = 0;
    @SG_column(dbField = "iae_accu_bid_trading_volume")
    private long accu_bid_trading_volume = 0;
    @SG_column(dbField = "iae_accu_bid_trading_value")
    private float accu_bid_trading_value = 0;
    @SG_column(dbField = "iae_end_keyword")
    private String end_keyword;
    @SG_column(dbField = "iae_crdt")
    private Timestamp createdAt;

    public InvestorActivitiesEOD(String msg) throws NumberFormatException {

        Instant now = Instant.now();
        ZonedDateTime koreaTime = now.atZone(ZoneId.of("Asia/Seoul"));
        createdAt = Timestamp.from(koreaTime.toInstant());

        data_category = msg.substring(0, 2);
        info_category = msg.substring(2, 5);
        isin_code = msg.substring(5, 17);
        if(!msg.substring(17, 23).isBlank()) a_designated_number_for_an_issue = Integer.parseInt(msg.substring(17, 23));
        investor_code = msg.substring(23, 27);
        if(!msg.substring(27, 39).isBlank()) accu_ask_trading_volume = Long.parseLong(msg.substring(27, 39));
        if(!msg.substring(39, 61).isBlank()) accu_ask_trading_value = Float.parseFloat(msg.substring(39, 61));
        if(!msg.substring(61, 73).isBlank()) accu_bid_trading_volume = Long.parseLong(msg.substring(61, 73));
        if(!msg.substring(73, 95).isBlank()) accu_bid_trading_value = Float.parseFloat(msg.substring(73, 95));
        end_keyword = msg.substring(95, 96);
    }

    public Map<String, Object> toMap() {
        final Map<String, Object> data = new HashMap<>();
        for(final Field f: this.getClass().getDeclaredFields()) {
            if(f.isAnnotationPresent(SG_column.class)) {
                final SG_column sc = f.getAnnotation(SG_column.class);
                try {
                    data.put(sc.dbField(), f.get(this));
                } catch (Exception ignore) {
                }
            }
        }
        return data;
    }

    public Map<String, Object> toSocket(Product prod) {
        final Map<String, Object> res = new HashMap<>();
        res.put("isin_code", isin_code);
        res.put("name_kr", prod.getName_kr());
        res.put("name_kr_abbr", prod.getName_kr_abbr());
        res.put("name_en", prod.getName_en());
        res.put("market_total_price", prod.getHaving_count() * prod.getCurrentPrice());
        res.put("compare_rate", prod.getComparePriceRate());
        res.put("foreigner_ask_count", prod.getForeignerAskCount());
        res.put("foreigner_bid_count", prod.getForeignerBidCount());
        res.put("facility_ask_count", prod.getFacilityAskCount());
        res.put("facility_bid_count", prod.getFacilityBidCount());
        return res;
    }

    static public String[] insertCols() {
        return new String[] {
                "iae_data_category",
                "iae_info_category",
                "iae_isin_code",
                "iae_a_designated_number_for_an_issue",
                "iae_investor_code",
                "iae_accu_ask_trading_volume",
                "iae_accu_ask_trading_value",
                "iae_accu_bid_trading_volume",
                "iae_accu_bid_trading_value",
                "iae_end_keyword",
                "iae_crdt",
        };
    }
}
