package com.quant_socket.models;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SG_table(name = "product")
@Getter
@Setter
@RequiredArgsConstructor
public class Product extends SG_model<Product>{

    @SG_idx
    @SG_column(dbField = "p_idx")
    private Long idx;
    @SG_column(dbField = "p_code")
    private String code;
    @SG_column(dbField = "p_short_code")
    private String short_code;
    @SG_column(dbField = "p_name_kr")
    private String name_kr;
    @SG_column(dbField = "p_name_kr_abbr")
    private String name_kr_abbr;
    @SG_column(dbField = "p_name_en")
    private String name_en;
    @SG_column(dbField = "p_class")
    private String gubun;
    @SG_column(dbField = "p_seq_class")
    private String seq_gubun;
    @SG_column(dbField = "p_team")
    private String team;
    @SG_column(dbField = "p_type")
    private String type;
    @SG_column(dbField = "p_face_value")
    private Double face_value;
    @SG_column(dbField = "p_having_count")
    private Long having_count;
    @SG_crdt
    @SG_column(dbField = "p_crdt")
    private String crdt;

    private long todayBidCount;
    private long todayAskCount;
    private long todayTradingCount = 0;
    private long yesTradingCount = 0;

    private Map<Double, Long> tradingList = new HashMap<>();

    public long getTodayTradingCount() {
        return todayBidCount + todayAskCount;
    }

    public void updateTodayCount(String isinCode, String type, long count) {
        if (code.equals(isinCode)) {
            switch (type) {
                case "0":
                    break;
                case "1":
                    todayAskCount += count;
                    break;
                case "2":
                    todayBidCount += count;
            }
        }
    }

    public void updateTradingList(Double price, long count) {
        long _count = 0;
        if(tradingList.get(price) != null) _count = tradingList.get(price);
        tradingList.put(price, _count + count);
    }

    public void refreshProduct() {
        yesTradingCount = getTodayTradingCount();
        todayBidCount = 0;
        todayAskCount = 0;
        tradingList.clear();
    }

    public Product(ResultSet rs) {
        super.resultSetToClass(rs);
    }
}
