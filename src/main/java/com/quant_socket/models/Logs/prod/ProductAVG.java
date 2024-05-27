package com.quant_socket.models.Logs.prod;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.SG_model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@ToString
@SG_table(name = "product_avg")
public class ProductAVG extends SG_model {
    @SG_idx
    @SG_column(dbField = "a_idx")
    private Long idx;
    @SG_column(dbField = "p_code")
    private String isinCode;
    @SG_column(dbField = "a_price5")
    private Integer price5;
    @SG_column(dbField = "a_price20")
    private Integer price20;
    @SG_column(dbField = "a_avg_date")
    private Date avg_date;
    @SG_column(dbField = "a_crdt")
    private Timestamp createdAt;

    public ProductAVG(ResultSet rs) {
        resultSetToClass(rs);
    }
}
