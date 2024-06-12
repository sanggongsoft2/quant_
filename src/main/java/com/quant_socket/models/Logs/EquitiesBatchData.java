package com.quant_socket.models.Logs;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class EquitiesBatchData{
    private String isin_code;
    private String abbr_issue_name;
    private String info_category;
    private String sec_group_id;
    private String base_price_change;
    private String section_type_code;
    private String other_stock_type_code;
    private Double par_value;
    private Long number_of_listed_shares;
    private BigDecimal yes_closing_price;
    private Float yes_accu_trading_value;

    public EquitiesBatchData(String msg) throws NumberFormatException {
        info_category = msg.substring(2, 5);
        isin_code = msg.substring(27, 39).trim();
        abbr_issue_name = msg.substring(54, 94).trim();
        sec_group_id = msg.substring(142, 144);
        base_price_change = msg.substring(152, 153);
        section_type_code = msg.substring(173, 174);
        if(!msg.substring(187, 198).isBlank()) yes_closing_price = BigDecimal.valueOf(Double.parseDouble(msg.substring(187, 198)));
        if(!msg.substring(210, 232).isBlank()) yes_accu_trading_value = Float.parseFloat(msg.substring(210, 232));
        if(!msg.substring(265, 276).isBlank()) par_value = Double.parseDouble(msg.substring(265, 276));
        if(!msg.substring(295, 311).isBlank()) number_of_listed_shares = Long.parseLong(msg.substring(295, 311));
        other_stock_type_code = msg.substring(407, 408);
    }
}
