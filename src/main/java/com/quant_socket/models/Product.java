package com.quant_socket.models;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.Logs.*;
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
    @SG_column(dbField = "p_yesterday_price")
    private Double yesterday_price;
    @SG_column(dbField = "p_yesterday_value")
    private Float yesterday_value;
    @SG_column(dbField = "p_yesterday_trading_count")
    private Long yesterday_trading_count;

    @SG_crdt
    @SG_column(dbField = "p_crdt")
    private String crdt;

    private double currentPrice = 0;
    private double comparePriceRate = 0;

    private long todayBidCount = 0;
    private long todayAskCount = 0;
    private long todayTradingCount = 0;

    private long foreignerAskCount = 0;
    private long foreignerBidCount = 0;
    private long facilityAskCount = 0;
    private long facilityBidCount = 0;

    private Map<Double, Long> tradingList = new HashMap<>();

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

    private void updateTradingList(Double price, long count) {
        long _count = 0;
        if(tradingList.get(price) != null) _count = tradingList.get(price);
        tradingList.put(price, _count + count);
    }

    public void refresh() {
        todayBidCount = 0;
        todayAskCount = 0;
        tradingList.clear();
    }

    public void update(EquitiesBatchData data) {
        if(data.getBase_price_change().equals("Y")) {
            face_value = data.getPar_value();
            yesterday_price = data.getYes_closing_price();
            yesterday_trading_count = data.getYes_accu_trading_amount();
            yesterday_value = data.getYes_accu_trading_value();
            having_count = data.getNumber_of_listed_shares();
        }
    }

    public void update(EquitiesSnapshot data) {
        this.currentPrice = data.getCurrent_price();
        this.comparePriceRate = data.getComparePriceRate();
        this.yesterday_price = data.getYesterdayPrice();
    }

    public void update(EquityIndexIndicator data) {

    }

    public void update(InvestorActivitiesEOD data) {
        switch (data.getInvestor_code()) {
            case "9000":
            case "9001":
                foreignerBidCount = data.getAccu_bid_trading_volume();
                foreignerAskCount = data.getAccu_ask_trading_volume();
                break;
            default:
                facilityBidCount = data.getAccu_bid_trading_volume();
                facilityAskCount = data.getAccu_ask_trading_volume();
        }
    }

    public void update(SecOrderFilled data) {
        currentPrice = data.getTrading_price();
        updateTradingList(currentPrice, data.getTrading_volume());
    }

    public Product(ResultSet rs) {
        super.resultSetToClass(rs);
    }
}
