package com.quant_socket.models;

import com.quant_socket.annotations.*;
import com.quant_socket.models.Logs.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SG_table(name = "product")
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
@ToString
public class Product extends SG_model{

    @SG_idx
    @SG_column(dbField = "p_idx")
    private Integer idx;
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
    private BigDecimal yesterday_price;
    @SG_column(dbField = "p_yesterday_value")
    private Float yesterday_value;
    @SG_column(dbField = "p_yesterday_trading_count")
    private Long yesterday_trading_count;

    @SG_column(dbField = "p_status")
    private String status;

    @SG_crdt
    @SG_column(dbField = "p_crdt")
    private Date createdAt;

    @SG_column(dbField = "max_52_price")
    private BigDecimal max_52_price;

    @SG_column(dbField = "min_52_price")
    private BigDecimal min_52_price;

    private double currentPrice = 0;
    private double comparePriceRate = 0;
    private double highPrice = 0;
    private double lowPrice = 0;
    private double openPrice = 0;
    private long tradingVolume = 0;

    private long todayBidCount = 0;
    private long todayAskCount = 0;
    private long todayTradingCount = 0;
    private float todayTradingValue = 0;

    @SG_join(clazz = EquitiesSnapshot.class)
    private EquitiesSnapshot latestSnapshot;

    @SG_join(clazz = SecuritiesQuote.class)
    private SecuritiesQuote latestSecuritiesQuote;

    private List<SecOrderFilled> orders = new CopyOnWriteArrayList<>();

    private synchronized void updateTodayCount(String type, long count) {
        if(type != null) switch (type) {
            case "1":
                todayAskCount += count;
                break;
            case "2":
                todayBidCount += count;
        }
    }

    public synchronized void refreshTradingVolumeFrom1Minute() {
        tradingVolume = 0;
    }

    public synchronized void refreshEveryday() {
        todayBidCount = 0;
        todayAskCount = 0;
        todayTradingCount = 0;
        todayTradingValue = 0;
    }

    public synchronized void update(EquitiesBatchData data) {
        if(data.getIsin_code() != null) this.code = data.getIsin_code();
        if(data.getShort_code() != null) this.short_code = data.getShort_code();
        if(data.getAbbr_issue_name_kr() != null) this.name_kr = data.getAbbr_issue_name_kr();
        if(data.getAbbr_issue_name_kr() != null) this.name_kr_abbr = data.getAbbr_issue_name_kr();
        if(data.getAbbr_issue_name_en() != null) this.name_en = data.getAbbr_issue_name_en();
        if(data.getInfo_category() != null) this.gubun = infoCategoryToClass(data.getInfo_category());
        if(data.getSec_group_id() != null) this.seq_gubun = groupIdToSeqClass(data.getSec_group_id());
        if(data.getSection_type_code() != null) this.team = sectionTypeCodeToTeam(data.getSection_type_code());
        if(data.getOther_stock_type_code() != null) this.type = stockTypeToType(data.getOther_stock_type_code());
        if(data.getPar_value() != null) this.face_value = data.getPar_value();
        if(data.getNumber_of_listed_shares() != null) this.having_count = data.getNumber_of_listed_shares();
        if(data.getYesterday_closing_price() != null) this.yesterday_price = data.getYesterday_closing_price();
        if(data.getYesterday_trading_volume() != null) this.yesterday_trading_count = data.getYesterday_trading_volume();
        if(data.getYesterday_trading_value() != null) this.yesterday_value = data.getYesterday_trading_value();
    }

    public synchronized void update(SecuritiesQuote data) {
        if(data != null) this.latestSecuritiesQuote = data;
    }

    public synchronized void update(EquitiesSnapshot data) {
        if(data != null && data.isRealBoard()) {
            if(data.getCurrent_price() != null) this.currentPrice = data.getCurrent_price().doubleValue();
            if(data.getComparePriceRate() != null) this.comparePriceRate = data.getComparePriceRate();
            if(data.getTodays_high() != null) this.highPrice = data.getTodays_high().doubleValue();
            if(data.getTodays_low() != null) this.lowPrice = data.getTodays_low().doubleValue();
            if(data.getOpening_price() != null) this.openPrice = data.getOpening_price().doubleValue();
            if(data.getYesterdayPrice() != null) this.yesterday_price = data.getYesterdayPrice();
            this.latestSnapshot = data;
        }
    }

    public synchronized void update(SecOrderFilled data) {
        this.currentPrice = data.getTrading_price();
        this.highPrice = data.getTodays_high();
        this.lowPrice = data.getTodays_low();
        this.openPrice = data.getOpening_price();
        this.todayTradingCount = data.getTrading_volume();
        this.todayTradingValue = data.getAccu_trading_value();
        this.tradingVolume += data.getTrading_volume();
        this.yesterday_price = BigDecimal.valueOf(data.getYesterdayPrice());
        updateTodayCount(data.getFinal_askbid_type_code(), data.getTrading_volume());
        if(data.getCompareRate() != null) comparePriceRate = data.getCompareRate();
        if(orders.size() == 20) {
            this.orders.remove(19);
            this.orders.add(data);
        } else {
            this.orders.add(data);
        }
        if(highPrice > max_52_price.doubleValue()) max_52_price = new BigDecimal(highPrice);
        if(lowPrice < min_52_price.doubleValue()) min_52_price = new BigDecimal(lowPrice);
    }

    public Product(ResultSet rs) {
        super(rs);
        update(new EquitiesSnapshot(rs));
        update(new SecuritiesQuote(rs));
    }

    public Product(EquitiesBatchData data) {
        try {
            if(data.getIsin_code() != null) this.code = data.getIsin_code();
            if(data.getShort_code() != null) this.short_code = data.getShort_code();
            if(data.getAbbr_issue_name_kr() != null) this.name_kr = data.getAbbr_issue_name_kr();
            if(data.getAbbr_issue_name_kr() != null) this.name_kr_abbr = data.getAbbr_issue_name_kr();
            if(data.getAbbr_issue_name_en() != null) this.name_en = data.getAbbr_issue_name_en();
            if(data.getInfo_category() != null) this.gubun = infoCategoryToClass(data.getInfo_category());
            if(data.getSec_group_id() != null) this.seq_gubun = groupIdToSeqClass(data.getSec_group_id());
            if(data.getSection_type_code() != null) this.team = sectionTypeCodeToTeam(data.getSection_type_code());
            if(data.getOther_stock_type_code() != null) this.type = stockTypeToType(data.getOther_stock_type_code());
            if(data.getPar_value() != null) this.face_value = data.getPar_value();
            if(data.getNumber_of_listed_shares() != null) this.having_count = data.getNumber_of_listed_shares();
            if(data.getYesterday_closing_price() != null) this.yesterday_price = data.getYesterday_closing_price();
            if(data.getYesterday_trading_volume() != null) this.yesterday_trading_count = data.getYesterday_trading_volume();
            if(data.getYesterday_trading_value() != null) this.yesterday_value = data.getYesterday_trading_value();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String infoCategoryToClass(String value) {
        return switch (value) {
            case "01S" -> "KOSPI";
            case "01X" -> "KONEX";
            case "01Q" -> "KOSDAQ";
            case "03S" -> "ETF";
            default -> null;
        };
    }

    private String groupIdToSeqClass(String value) {
        if(value == null) return null;
        return switch (value) {
            case "MF" -> "투자회사";
            case "RT" -> "부동산투자회사";
            case "SC" -> "선박투자회사";
            case "IF" -> "사회간접자본투융자회사";
            case "BC" -> "수익증권";
            case "ST" -> "주권";
            case "FS" -> "외국주권";
            case "DR" -> "주식예탁증권";
            case "EF" -> "ETF";
            case "ET" -> "ETN";
            case "FE" -> "외국ETF";
            case "EN" -> "ETN";
            default -> value;
        };
    }

    private String sectionTypeCodeToTeam(String value) {
        if(value == null) return "기타(소속부없음)";
        return switch (value) {
            case "1" -> "우량기업부";
            case "2" -> "벤처기업부";
            case "3" -> "중견기업부";
            case "4" -> "신성장기업부";
            case "A" -> "외국기업(소속부없음)";
            case "B" -> "투자회사(소속부없음)";
            case "C" -> "SPAC(소속부없음)";
            case "D" -> "ETF(소속부없음)";
            case "E" -> "관리종목(소속부없음)";
            case "F" -> "투자주의환기종목(소속부없음)";
            case "J" -> "일반(구 소속부)";
            case "K" -> "벤처(구 소속부)";
            case "L" -> "MF(구 소속부)";
            case "M" -> "ETF(구 소속부)";
            case "N" -> "외국기업(구 소속부)";
            case "W" -> "크라우드펀딩기업부";
            case "X" -> "일반기업부";
            case "Y" -> "스타트업기업부";
            default -> "기타(소속부없음)";
        };
    }

    private String stockTypeToType(String value) {
        if(value == null) return null;
        return switch (value) {
            case "0" -> "보통주";
            case "1" -> "구형우선주";
            case "2" -> "신형우선주";
            case "9" -> "종류주권";
            default -> null;
        };
    }

    static public String[] insertCols() {
        return new String[] {
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
        };
    }

    public Double getTotalPrice() {
        return currentPrice * having_count;
    }
}
