package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_substring;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.Product;
import com.quant_socket.models.SG_model;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@SG_table(name = "issue_closing")
@ToString
@Getter
public class IssueClosing extends SG_model {

    /**
     * 보드ID
     * */
    @SG_column(dbField = "ic_board_id")
    @SG_substring(start = 13, end = 15)
    private String board_id;
    /**
     * 종목코드
     * */
    @SG_column(dbField = "ic_isin_code")
    @SG_substring(start = 15, end = 27)
    private String isin_code;
    /**
     * 종목마감종가
     * */
    @SG_column(dbField = "ic_closing_price")
    @SG_substring(start = 33, end = 44)
    private BigDecimal closing_price;
    /**
     * 1:실세
     * 2:기세
     * 3:거래무
     * 4:시가기준가종목의 기세
     * */
    @SG_column(dbField = "ic_closing_type")
    @SG_substring(start = 44, end = 45)
    private String closing_type;
    /**
     * 종목마감시간외단일가상한가
     * */
    @SG_column(dbField = "ic_upper_limit_price_out")
    @SG_substring(start = 45, end = 56)
    private BigDecimal upper_limit_price_out;
    /**
     * 종목마감시간외단일가하한가
     * */
    @SG_column(dbField = "ic_lower_limit_price_out")
    @SG_substring(start = 56, end = 67)
    private BigDecimal lower_limit_price_out;
    /**
     * 종목마감평균가중주가
     * */
    @SG_column(dbField = "ic_closing_price_avg")
    @SG_substring(start = 67, end = 78)
    private BigDecimal closing_price_avg;
    /**
     * 종목마감매입인도기준가격
     * */
    @SG_column(dbField = "ic_base_price")
    @SG_substring(start = 78, end = 89)
    private BigDecimal base_price;
    /**
     * 종목마감매입인도상한가
     * */
    @SG_column(dbField = "ic_upper_limit_price")
    @SG_substring(start = 89, end = 100)
    private BigDecimal upper_limit_price;
    /**
     * 종목마감매입인도하한가
     * */
    @SG_column(dbField = "ic_lower_limit_price")
    @SG_substring(start = 100, end = 111)
    private BigDecimal lower_limit_price;

    @SG_column(dbField = "ic_yesterday_price")
    private BigDecimal yesterday_price;

    @SG_column(dbField = "ic_trading_volume")
    private Long trading_volume;

    @SG_column(dbField = "ic_trading_value")
    private Float trading_value;

    public IssueClosing(String msg) {
        super(msg);
    }

    public void setFromProduct(Product prod) {
        this.yesterday_price = prod.getYesterday_price();
        this.trading_volume = prod.getTradingVolume();
        this.trading_value = prod.getYesterday_value();
    }
}
