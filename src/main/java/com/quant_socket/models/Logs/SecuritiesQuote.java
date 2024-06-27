package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_substring;
import com.quant_socket.annotations.SG_substring_lp;
import com.quant_socket.models.Product;
import com.quant_socket.models.SG_model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
@ToString
public class SecuritiesQuote extends SG_model {

    @SG_substring(start = 13, end = 15)
    @SG_substring_lp(start = 13, end = 15)
    @SG_column(dbField = "sq_board_id")
    private String board_id;

    @SG_substring(start = 17, end = 29)
    @SG_substring_lp(start = 17, end = 29)
    @SG_column(dbField = "sq_isin_code")
    private String isin_code;

    @SG_substring(start = 47, end = 58)
    @SG_substring_lp(start = 47, end = 58)
    @SG_column(dbField = "sq_ask_level_1_price")
    private BigDecimal ask_level_1_price;
    @SG_substring(start = 93, end = 104)
    @SG_substring_lp(start = 117, end = 128)
    @SG_column(dbField = "sq_ask_level_2_price")
    private BigDecimal ask_level_2_price;
    @SG_substring(start = 139, end = 150)
    @SG_substring_lp(start = 187, end = 198)
    @SG_column(dbField = "sq_ask_level_3_price")
    private BigDecimal ask_level_3_price;
    @SG_substring(start = 185, end = 196)
    @SG_substring_lp(start = 257, end = 268)
    @SG_column(dbField = "sq_ask_level_4_price")
    private BigDecimal ask_level_4_price;
    @SG_substring(start = 231, end = 242)
    @SG_substring_lp(start = 327, end = 338)
    @SG_column(dbField = "sq_ask_level_5_price")
    private BigDecimal ask_level_5_price;
    @SG_substring(start = 277, end = 288)
    @SG_substring_lp(start = 397, end = 408)
    @SG_column(dbField = "sq_ask_level_6_price")
    private BigDecimal ask_level_6_price;
    @SG_substring(start = 323, end = 334)
    @SG_substring_lp(start = 467, end = 478)
    @SG_column(dbField = "sq_ask_level_7_price")
    private BigDecimal ask_level_7_price;
    @SG_substring(start = 369, end = 380)
    @SG_substring_lp(start = 537, end = 548)
    @SG_column(dbField = "sq_ask_level_8_price")
    private BigDecimal ask_level_8_price;
    @SG_substring(start = 415, end = 426)
    @SG_substring_lp(start = 607, end = 618)
    @SG_column(dbField = "sq_ask_level_9_price")
    private BigDecimal ask_level_9_price;
    @SG_substring(start = 461, end = 472)
    @SG_substring_lp(start = 677, end = 688)
    @SG_column(dbField = "sq_ask_level_10_price")
    private BigDecimal ask_level_10_price;

    @SG_substring(start = 58, end = 69)
    @SG_substring_lp(start = 58, end = 69)
    @SG_column(dbField = "sq_ask_level_1_price")
    private BigDecimal bid_level_1_price;
    @SG_substring(start = 104, end = 115)
    @SG_substring_lp(start = 128, end = 139)
    @SG_column(dbField = "sq_bid_level_2_price")
    private BigDecimal bid_level_2_price;
    @SG_substring(start = 150, end = 161)
    @SG_substring_lp(start = 198, end = 209)
    @SG_column(dbField = "sq_bid_level_3_price")
    private BigDecimal bid_level_3_price;
    @SG_substring(start = 196, end = 207)
    @SG_substring_lp(start = 268, end = 279)
    @SG_column(dbField = "sq_bid_level_4_price")
    private BigDecimal bid_level_4_price;
    @SG_substring(start = 242, end = 253)
    @SG_substring_lp(start = 338, end = 349)
    @SG_column(dbField = "sq_bid_level_5_price")
    private BigDecimal bid_level_5_price;
    @SG_substring(start = 288, end = 299)
    @SG_substring_lp(start = 408, end = 419)
    @SG_column(dbField = "sq_bid_level_6_price")
    private BigDecimal bid_level_6_price;
    @SG_substring(start = 334, end = 345)
    @SG_substring_lp(start = 478, end = 489)
    @SG_column(dbField = "sq_bid_level_7_price")
    private BigDecimal bid_level_7_price;
    @SG_substring(start = 380, end = 391)
    @SG_substring_lp(start = 548, end = 559)
    @SG_column(dbField = "sq_bid_level_8_price")
    private BigDecimal bid_level_8_price;
    @SG_substring(start = 426, end = 437)
    @SG_substring_lp(start = 618, end = 629)
    @SG_column(dbField = "sq_bid_level_9_price")
    private BigDecimal bid_level_9_price;
    @SG_substring(start = 472, end = 483)
    @SG_substring_lp(start = 688, end = 699)
    @SG_column(dbField = "sq_bid_level_10_price")
    private BigDecimal bid_level_10_price;


    @SG_substring(start = 69, end = 81)
    @SG_substring_lp(start = 69, end = 81)
    @SG_column(dbField = "sq_ask_level_1_volume")
    private Long ask_level_1_volume;
    @SG_substring(start = 115, end = 127)
    @SG_substring_lp(start = 139, end = 151)
    @SG_column(dbField = "sq_ask_level_2_volume")
    private Long ask_level_2_volume;
    @SG_substring(start = 161, end = 173)
    @SG_substring_lp(start = 209, end = 221)
    @SG_column(dbField = "sq_ask_level_3_volume")
    private Long ask_level_3_volume;
    @SG_substring(start = 207, end = 219)
    @SG_substring_lp(start = 279, end = 291)
    @SG_column(dbField = "sq_ask_level_4_volume")
    private Long ask_level_4_volume;
    @SG_substring(start = 253, end = 265)
    @SG_substring_lp(start = 349, end = 361)
    @SG_column(dbField = "sq_ask_level_5_volume")
    private Long ask_level_5_volume;
    @SG_substring(start = 299, end = 311)
    @SG_substring_lp(start = 419, end = 431)
    @SG_column(dbField = "sq_ask_level_6_volume")
    private Long ask_level_6_volume;
    @SG_substring(start = 345, end = 357)
    @SG_substring_lp(start = 489, end = 501)
    @SG_column(dbField = "sq_ask_level_7_volume")
    private Long ask_level_7_volume;
    @SG_substring(start = 391, end = 403)
    @SG_substring_lp(start = 559, end = 571)
    @SG_column(dbField = "sq_ask_level_8_volume")
    private Long ask_level_8_volume;
    @SG_substring(start = 437, end = 449)
    @SG_substring_lp(start = 629, end = 641)
    @SG_column(dbField = "sq_ask_level_9_volume")
    private Long ask_level_9_volume;
    @SG_substring(start = 483, end = 495)
    @SG_substring_lp(start = 699, end = 711)
    @SG_column(dbField = "sq_ask_level_10_volume")
    private Long ask_level_10_volume;

    @SG_substring(start = 81, end = 93)
    @SG_substring_lp(start = 81, end = 93)
    @SG_column(dbField = "sq_bid_level_1_volume")
    private Long bid_level_1_volume;
    @SG_substring(start = 127, end = 139)
    @SG_substring_lp(start = 151, end = 163)
    @SG_column(dbField = "sq_bid_level_2_volume")
    private Long bid_level_2_volume;
    @SG_substring(start = 173, end = 185)
    @SG_substring_lp(start = 221, end = 233)
    @SG_column(dbField = "sq_bid_level_3_volume")
    private Long bid_level_3_volume;
    @SG_substring(start = 219, end = 231)
    @SG_substring_lp(start = 291, end = 303)
    @SG_column(dbField = "sq_bid_level_4_volume")
    private Long bid_level_4_volume;
    @SG_substring(start = 265, end = 277)
    @SG_substring_lp(start = 361, end = 373)
    @SG_column(dbField = "sq_bid_level_5_volume")
    private Long bid_level_5_volume;
    @SG_substring(start = 311, end = 323)
    @SG_substring_lp(start = 431, end = 443)
    @SG_column(dbField = "sq_bid_level_6_volume")
    private Long bid_level_6_volume;
    @SG_substring(start = 357, end = 369)
    @SG_substring_lp(start = 501, end = 513)
    @SG_column(dbField = "sq_bid_level_7_volume")
    private Long bid_level_7_volume;
    @SG_substring(start = 403, end = 415)
    @SG_substring_lp(start = 571, end = 583)
    @SG_column(dbField = "sq_bid_level_8_volume")
    private Long bid_level_8_volume;
    @SG_substring(start = 449, end = 461)
    @SG_substring_lp(start = 641, end = 653)
    @SG_column(dbField = "sq_bid_level_9_volume")
    private Long bid_level_9_volume;
    @SG_substring(start = 495, end = 507)
    @SG_substring_lp(start = 711, end = 723)
    @SG_column(dbField = "sq_bid_level_10_volume")
    private Long bid_level_10_volume;

    @SG_substring(start = 507, end = 519)
    @SG_substring_lp(start = 747, end = 759)
    @SG_column(dbField = "sq_total_ask_volume")
    private Long total_ask_volume;
    @SG_substring(start = 519, end = 531)
    @SG_substring_lp(start = 759, end = 771)
    @SG_column(dbField = "sq_total_bid_volume")
    private Long total_bid_volume;
    @SG_substring(start = 531, end = 542)
    @SG_substring_lp(start = 771, end = 782)
    @SG_column(dbField = "sq_estimated_trading_price")
    private BigDecimal estimated_trading_price;
    @SG_substring(start = 542, end = 554)
    @SG_substring_lp(start = 782, end = 894)
    @SG_column(dbField = "sq_estimated_trading_volume")
    private Long estimated_trading_volume;

    public SecuritiesQuote(ResultSet rs) {
        super(rs);
    }

    public SecuritiesQuote(String msg) {
        this(msg, false);
    }
    public SecuritiesQuote(String msg, boolean withLP) {
        super(msg, withLP);
    }
    public Map<String, Object> toSocket(Product prod) {
        final Map<String, Object> response = new LinkedHashMap<>();
        response.put("isin_code", isin_code);
        //9. 매도 호가 수량
        response.put("ask_level_1_volume", ask_level_1_volume);
        response.put("ask_level_2_volume", ask_level_2_volume);
        response.put("ask_level_3_volume", ask_level_3_volume);
        response.put("ask_level_4_volume", ask_level_4_volume);
        response.put("ask_level_5_volume", ask_level_5_volume);
        response.put("ask_level_6_volume", ask_level_6_volume);
        response.put("ask_level_7_volume", ask_level_7_volume);
        response.put("ask_level_8_volume", ask_level_8_volume);
        response.put("ask_level_9_volume", ask_level_9_volume);
        response.put("ask_level_10_volume", ask_level_10_volume);
        //10. 매도 호가 가격
        response.put("ask_level_1_price", ask_level_1_price);
        response.put("ask_level_2_price", ask_level_2_price);
        response.put("ask_level_3_price", ask_level_3_price);
        response.put("ask_level_4_price", ask_level_4_price);
        response.put("ask_level_5_price", ask_level_5_price);
        response.put("ask_level_6_price", ask_level_6_price);
        response.put("ask_level_7_price", ask_level_7_price);
        response.put("ask_level_8_price", ask_level_8_price);
        response.put("ask_level_9_price", ask_level_9_price);
        response.put("ask_level_10_price", ask_level_10_price);

        response.put("ask_level_1_rate", getComparePriceRate(prod.getYesterday_price(), ask_level_1_price));
        response.put("ask_level_2_rate", getComparePriceRate(prod.getYesterday_price(), ask_level_2_price));
        response.put("ask_level_3_rate", getComparePriceRate(prod.getYesterday_price(), ask_level_3_price));
        response.put("ask_level_4_rate", getComparePriceRate(prod.getYesterday_price(), ask_level_4_price));
        response.put("ask_level_5_rate", getComparePriceRate(prod.getYesterday_price(), ask_level_5_price));
        response.put("ask_level_6_rate", getComparePriceRate(prod.getYesterday_price(), ask_level_6_price));
        response.put("ask_level_7_rate", getComparePriceRate(prod.getYesterday_price(), ask_level_7_price));
        response.put("ask_level_8_rate", getComparePriceRate(prod.getYesterday_price(), ask_level_8_price));
        response.put("ask_level_9_rate", getComparePriceRate(prod.getYesterday_price(), ask_level_9_price));
        response.put("ask_level_10_rate", getComparePriceRate(prod.getYesterday_price(), ask_level_10_price));

        //20. 매수 호가 수량, 가격, 등락률
        response.put("bid_level_1_volume", bid_level_1_volume);
        response.put("bid_level_2_volume", bid_level_2_volume);
        response.put("bid_level_3_volume", bid_level_3_volume);
        response.put("bid_level_4_volume", bid_level_4_volume);
        response.put("bid_level_5_volume", bid_level_5_volume);
        response.put("bid_level_6_volume", bid_level_6_volume);
        response.put("bid_level_7_volume", bid_level_7_volume);
        response.put("bid_level_8_volume", bid_level_8_volume);
        response.put("bid_level_9_volume", bid_level_9_volume);
        response.put("bid_level_10_volume", bid_level_10_volume);

        response.put("bid_level_1_price", bid_level_1_price);
        response.put("bid_level_2_price", bid_level_2_price);
        response.put("bid_level_3_price", bid_level_3_price);
        response.put("bid_level_4_price", bid_level_4_price);
        response.put("bid_level_5_price", bid_level_5_price);
        response.put("bid_level_6_price", bid_level_6_price);
        response.put("bid_level_7_price", bid_level_7_price);
        response.put("bid_level_8_price", bid_level_8_price);
        response.put("bid_level_9_price", bid_level_9_price);
        response.put("bid_level_10_price", bid_level_10_price);

        response.put("bid_level_1_rate", getComparePriceRate(prod.getYesterday_price(), bid_level_1_price));
        response.put("bid_level_2_rate", getComparePriceRate(prod.getYesterday_price(), bid_level_2_price));
        response.put("bid_level_3_rate", getComparePriceRate(prod.getYesterday_price(), bid_level_3_price));
        response.put("bid_level_4_rate", getComparePriceRate(prod.getYesterday_price(), bid_level_4_price));
        response.put("bid_level_5_rate", getComparePriceRate(prod.getYesterday_price(), bid_level_5_price));
        response.put("bid_level_6_rate", getComparePriceRate(prod.getYesterday_price(), bid_level_6_price));
        response.put("bid_level_7_rate", getComparePriceRate(prod.getYesterday_price(), bid_level_7_price));
        response.put("bid_level_8_rate", getComparePriceRate(prod.getYesterday_price(), bid_level_8_price));
        response.put("bid_level_9_rate", getComparePriceRate(prod.getYesterday_price(), bid_level_9_price));
        response.put("bid_level_10_rate", getComparePriceRate(prod.getYesterday_price(), bid_level_10_price));

        response.put("ask_total_count", total_ask_volume);
        response.put("bid_total_count", total_bid_volume);
        response.put("estimated_trading_price", total_bid_volume);
        response.put("estimated_trading_volume", estimated_trading_volume);
        response.put("yesterday_price", prod.getYesterday_price());
        return response;
    }
    public double getComparePriceRate(BigDecimal yesterday_price, BigDecimal price) {

        if(price == null ||  yesterday_price == null) return 0;
        if (yesterday_price.compareTo(BigDecimal.ZERO) == 0) return 0;
        // (B - A) / A * 100
        BigDecimal increase = price.subtract(yesterday_price); // B - A
        BigDecimal percentageIncrease = increase.divide(yesterday_price, 4, RoundingMode.HALF_UP) // (B - A) / A
                .multiply(new BigDecimal("100")); // ((B - A) / A) * 100

        return percentageIncrease.doubleValue();

        /*try {
            double value = 0;
            if(yesterday_price != null && yesterday_price.doubleValue() != 0) value = (yesterday_price.doubleValue() - price.doubleValue()) / yesterday_price.doubleValue() * 100;
            return value;
        } catch (Exception e) {
            return 0;
        }*/
    }

    public boolean isRealBoard() {
        return board_id != null && board_id.equals("G1");
    }
}
