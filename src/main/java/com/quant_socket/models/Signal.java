package com.quant_socket.models;


import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;

@ToString
@SG_table(name = "signal_value")
@Getter
@Setter
public class Signal extends SG_model{

    @SG_idx
    @SG_column(dbField = "s_idx")
    private Integer idx;
    @SG_column(dbField = "s_high_value")
    private BigDecimal high_value;
    @SG_column(dbField = "s_low_value")
    private BigDecimal low_value;

    public Signal(ResultSet rs) {
        super(rs);
    }
}
