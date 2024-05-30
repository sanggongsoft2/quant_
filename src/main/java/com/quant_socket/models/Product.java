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

@SG_table(name = "product")
@Getter
@Setter
@RequiredArgsConstructor
public class Product extends SG_model{

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

    @SG_column(dbField = "p_status")
    private String status;

    @SG_crdt
    @SG_column(dbField = "p_crdt")
    private String crdt;

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

    private long foreignerAskCount = 0;
    private long foreignerBidCount = 0;
    private long facilityAskCount = 0;
    private long facilityBidCount = 0;

    private void updateTodayCount(String isinCode, String type, long count) {
        if (code.equals(isinCode)) {
            switch (type) {
                case "1":
                    todayAskCount += count;
                    break;
                case "2":
                    todayBidCount += count;
            }
        }
    }

    public void refreshTradingVolumeFrom1Minute() {
        tradingVolume = 0;
    }

    public void refreshEveryday() {
        todayBidCount = 0;
        todayAskCount = 0;
        todayTradingCount = 0;
        todayTradingValue = 0;
    }

    public void update(EquitiesBatchData data) {
        if(data.getBase_price_change().equals("Y")) {
            this.name_en = data.getAbbr_issue_name();
            this.gubun = infoCategoryToClass(data.getInfo_category());
            this.seq_gubun = groupIdToSeqClass(data.getSec_group_id());
            this.team = sectionTypeCodeToTeam(data.getSection_type_code());
            this.type = stockTypeToType(data.getOther_stock_type_code());
            this.face_value = data.getPar_value();
            this.having_count = data.getNumber_of_listed_shares();
            this.yesterday_price = data.getYes_closing_price();
            this.yesterday_value = data.getYes_accu_trading_value();
        }
    }

    public void update(EquitiesSnapshot data) {
        this.currentPrice = data.getCurrent_price();
        this.comparePriceRate = data.getComparePriceRate();
        this.yesterday_price = data.getYesterdayPrice();
        this.highPrice = data.getTodays_high();
        this.lowPrice = data.getTodays_low();
        this.openPrice = data.getOpening_price();
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
        this.currentPrice = data.getTrading_price();
        this.highPrice = data.getTodays_high();
        this.lowPrice = data.getTodays_low();
        this.openPrice = data.getOpening_price();
        this.todayTradingCount = data.getTrading_volume();
        this.todayTradingValue = data.getAccu_trading_value();
        updateTodayCount(data.getIsin_code(), data.getFinal_askbid_type_code(), data.getTrading_volume());
    }

    public Product(ResultSet rs) {
        super.resultSetToClass(rs);
    }

    public Product(EquitiesBatchData data) {
        this.code = data.getIsin_code();
        this.name_kr = data.getAbbr_issue_name();
        this.name_kr_abbr = data.getAbbr_issue_name();
        this.name_en = data.getAbbr_issue_name();
        this.gubun = infoCategoryToClass(data.getInfo_category());
        this.seq_gubun = groupIdToSeqClass(data.getSec_group_id());
        this.team = sectionTypeCodeToTeam(data.getSection_type_code());
        this.type = stockTypeToType(data.getOther_stock_type_code());
        this.face_value = data.getPar_value();
        this.having_count = data.getNumber_of_listed_shares();
        this.yesterday_price = data.getYes_closing_price();
        this.yesterday_value = data.getYes_accu_trading_value();
    }

    private String infoCategoryToClass(String value) {
        return switch (value) {
            case "01S" -> "KOSPI";
            case "01X" -> "KONEX";
            case "01Q" -> "KOSDAQ";
            case "A002S","A003S","A004S" -> "ETF";
            default -> null;
        };
    }

    private String groupIdToSeqClass(String value) {
        return switch (value) {
            case "MF" -> "투자회사";
            case "RT" -> "부동산투자회사";
            case "SC" -> "선박투자회사";
            case "IF" -> "사회간접자본투융자회사";
            case "BC" -> "수익증권";
            case "ST" -> "주권";
            case "FS" -> "외국주권";
            case "DR" -> "주식예탁증권";
            default -> null;
        };
    }

    private String sectionTypeCodeToTeam(String value) {
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
            case "Z" -> "기타(소속부없음)";
            default -> null;
        };
    }

    private String stockTypeToType(String value) {
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
}
