package com.quant_socket.models.Logs.prod;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.SG_model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;

@SG_table(name = "product_end_day")
public class ProductEndDay extends SG_model {
    @SG_idx
    @SG_column(dbField = "e_idx")
    private Long idx;
    @SG_column(dbField = "p_code")
    private String isinCode;
    @SG_column(dbField = "e_price")
    private Integer price;
    @SG_column(dbField = "e_end_date")
    private Date endDate;
    @SG_crdt
    @SG_column(dbField = "e_crdt")
    private Timestamp createdAt;

    public ProductEndDay(ResultSet rs) {
        resultSetToClass(rs);
    }
}
