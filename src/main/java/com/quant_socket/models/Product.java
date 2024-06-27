package com.quant_socket.models;

import com.quant_socket.annotations.*;
import com.quant_socket.models.Logs.*;
import com.quant_socket.models.Logs.prod.ProductMinute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    @SG_column(dbField = "closing_price", useInsert = false)
    private BigDecimal yesterday_price;
    @SG_column(dbField = "p_yesterday_value", useInsert = false)
    private Float yesterday_value;
    @SG_column(dbField = "trading_volume", useInsert = false)
    private Long yesterday_trading_count;

    @SG_column(dbField = "p_status", useInsert = false)
    private String status;

    @SG_crdt
    @SG_column(dbField = "p_crdt")
    private Date createdAt;

    @SG_column(dbField = "max_52_price", useInsert = false)
    private BigDecimal max_52_price;

    @SG_column(dbField = "min_52_price", useInsert = false)
    private BigDecimal min_52_price;

    @SG_column(dbField = "avg_5_day", useInsert = false)
    private BigDecimal avg_5_day;

    @SG_column(dbField = "avg_20_day", useInsert = false)
    private BigDecimal avg_20_day;

    private double currentPrice = 0;
    private double comparePriceRate = 0;
    private double highPrice = 0;
    private double lowPrice = 0;
    private double openPrice = 0;

    private long todayBidCount = 0;
    private long todayAskCount = 0;
    private long todayTradingCount = 0;
    private float todayTradingValue = 0;

    private ProductMinute currentPM;

    @SG_join(clazz = EquitiesSnapshot.class)
    private EquitiesSnapshot latestSnapshot;

    @SG_join(clazz = SecuritiesQuote.class)
    private SecuritiesQuote latestSecuritiesQuote;

    private List<SecOrderFilled> orders = new CopyOnWriteArrayList<>();

    private int maxOrderSize = 20;

    private void updateTodayCount(String type, long count) {
        if(type != null) switch (type) {
            case "1":
                todayAskCount += count;
                break;
            case "2":
                todayBidCount += count;
        }
    }

    public void refreshEveryday() {
        todayBidCount = 0;
        todayAskCount = 0;
    }

    public void update(EquitiesBatchData data) {
        synchronized (this) {
            if(data.getIsin_code() != null) this.code = data.getIsin_code();
            if(data.getShort_code() != null) this.short_code = data.getShort_code();
            if(data.getAbbr_issue_name_kr() != null) {
                this.name_kr = data.getAbbr_issue_name_kr();
                this.name_kr_abbr = data.getAbbr_issue_name_kr();
            }
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
    }

    public void update(SecuritiesQuote data) {
        synchronized (this) {
            if(data != null) this.latestSecuritiesQuote = data;
        }
    }

    public void update(EquitiesSnapshot data) {
        synchronized (this) {
            if(data != null && data.isRealBoard()) {
                if(data.getCurrent_price() != null) this.currentPrice = data.getCurrent_price().doubleValue();
                this.comparePriceRate = data.getComparePriceRate();
                if (data.getTodays_high() != null) this.highPrice = data.getTodays_high().doubleValue();
                if (data.getTodays_low() != null) this.lowPrice = data.getTodays_low().doubleValue();
                if (data.getOpening_price() != null) this.openPrice = data.getOpening_price().doubleValue();
                if(data.getYesterdayPrice() != null) this.yesterday_price = data.getYesterdayPrice();
                this.latestSnapshot = data;
            }
        }
    }

    public void update(SecOrderFilled data) {
        synchronized (this) {
            this.currentPrice = data.getTrading_price();
            this.highPrice = data.getTodays_high();
            this.lowPrice = data.getTodays_low();
            this.openPrice = data.getOpening_price();
            this.todayTradingCount = data.getTrading_volume();
            this.todayTradingValue = data.getAccu_trading_value();
            this.yesterday_price = BigDecimal.valueOf(data.getYesterdayPrice());
            updateTodayCount(data.getFinal_askbid_type_code(), data.getTrading_volume());
            comparePriceRate = data.getCompareRate();
            if(orders.size() == maxOrderSize) {
                this.orders.remove(0);
            }
            this.orders.add(data);
            if(max_52_price != null && highPrice > max_52_price.doubleValue()) max_52_price = new BigDecimal(highPrice);
            if(min_52_price != null && lowPrice < min_52_price.doubleValue()) min_52_price = new BigDecimal(lowPrice);

            currentPM.update(data);
        }
    }

    public Product(ResultSet rs) {
        super(rs);
        update(new EquitiesSnapshot(rs));
        update(new SecuritiesQuote(rs));
        currentPM = new ProductMinute(this);
    }

    public Product(EquitiesBatchData data) {
        try {
            if(data.getIsin_code() != null) this.code = data.getIsin_code();
            if(data.getShort_code() != null) this.short_code = data.getShort_code();
            if(data.getAbbr_issue_name_kr() != null) {
                this.name_kr = data.getAbbr_issue_name_kr();
                this.name_kr_abbr = data.getAbbr_issue_name_kr();
            }
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
        } catch (Exception ignore) {}
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

    public Double getTotalPrice() {
        return currentPrice * having_count;
    }

    public Double getSignal5DayMinPrice() {
        if(avg_5_day == null) return null;
        else if(avg_5_day.doubleValue() == 0) return currentPrice * 0.98;
        else return avg_5_day.doubleValue() * 0.98;
    }

    public Double getSignal5DayMaxPrice() {
        if(avg_5_day == null) return null;
        else if(avg_5_day.doubleValue() == 0) return currentPrice * 1.02;
        else return avg_5_day.doubleValue() * 1.02;
    }

    public String getSignal5DayText() {
        try {
            Double minPrice = getSignal5DayMinPrice();
            Double maxPrice = getSignal5DayMaxPrice();
            double avgPrice = avg_5_day.doubleValue();

            if(avgPrice < currentPrice && maxPrice > currentPrice) {
                return "매수";
            } else if(minPrice * 0.98 < currentPrice && minPrice > currentPrice) {
                return "매도";
            } else if(minPrice > currentPrice) {
                return "관망";
            } else {
                return "보유";
            }
        } catch (Exception e) {
            return "보유";
        }
    }

    public Double getSignal20DayMinPrice() {
        if(avg_20_day == null) return null;
        else if(avg_20_day.doubleValue() == 0) return currentPrice * 0.98;
        else return avg_20_day.doubleValue() * 0.98;
    }

    public Double getSignal20DayMaxPrice() {
        if(avg_20_day == null) return null;
        else if(avg_20_day.doubleValue() == 0) return currentPrice * 1.02;
        else return avg_20_day.doubleValue() * 1.02;
    }

    public String getSignal20DayText() {
        try {
            Double minPrice = getSignal20DayMinPrice();
            Double maxPrice = getSignal20DayMaxPrice();
            double avgPrice = avg_20_day.doubleValue();

            if(avgPrice < currentPrice && maxPrice >= currentPrice) {
                return "매수";
            } else if(minPrice * 0.98 <= currentPrice && minPrice > currentPrice) {
                return "매도";
            } else if(minPrice > currentPrice) {
                return "관망";
            } else {
                return "보유";
            }
        } catch (Exception e) {
            return "보유";
        }
    }

    public Map<String, Object> signalToMap() {
        final Map<String, Object> response = new LinkedHashMap<>();
        response.put("avg_5_day_price", avg_5_day);
        response.put("signal_5_day_text", getSignal5DayText());
        response.put("signal_5_day_min_price", getSignal5DayMinPrice());
        response.put("signal_5_day_max_price", getSignal5DayMaxPrice());
        response.put("avg_20_day_price", avg_20_day);
        response.put("signal_20_day_text", getSignal20DayText());
        response.put("signal_20_day_min_price", getSignal20DayMinPrice());
        response.put("signal_20_day_max_price", getSignal20DayMaxPrice());
        return response;
    }
}
