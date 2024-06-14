package com.quant_socket.models.Logs;
import com.quant_socket.annotations.SG_substring;
import com.quant_socket.models.SG_substring_model;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class EquitiesBatchData extends SG_substring_model {
    @SG_substring(start = 2, end = 5)
    private String isin_code;
    @SG_substring(start = 27, end = 39)
    private String abbr_issue_name;
    @SG_substring(start = 54, end = 94)
    private String info_category;
    @SG_substring(start = 142, end = 144)
    private String sec_group_id;
    @SG_substring(start = 152, end = 153)
    private String base_price_change;
    @SG_substring(start = 173, end = 174)
    private String section_type_code;
    @SG_substring(start = 407, end = 408)
    private String other_stock_type_code;
    @SG_substring(start = 265, end = 276)
    private Double par_value;
    @SG_substring(start = 295, end = 311)
    private Long number_of_listed_shares;
    @SG_substring(start = 187, end = 198)
    private BigDecimal yes_closing_price;
    @SG_substring(start = 210, end = 232)
    private Float yes_accu_trading_value;

    public EquitiesBatchData(String msg) {
        super(msg);
        /*info_category = msg.substring(2, 5);
        isin_code = msg.substring(27, 39).trim();
        abbr_issue_name = msg.substring(54, 94).trim();
        sec_group_id = msg.substring(142, 144);
        base_price_change = msg.substring(152, 153);
        section_type_code = msg.substring(173, 174);
        if(!msg.substring(187, 198).isBlank()) yes_closing_price = BigDecimal.valueOf(Double.parseDouble(msg.substring(187, 198)));
        if(!msg.substring(210, 232).isBlank()) yes_accu_trading_value = Float.parseFloat(msg.substring(210, 232));
        if(!msg.substring(265, 276).isBlank()) par_value = Double.parseDouble(msg.substring(265, 276));
        if(!msg.substring(295, 311).isBlank()) number_of_listed_shares = Long.parseLong(msg.substring(295, 311));
        other_stock_type_code = msg.substring(407, 408);*/
    }
}
