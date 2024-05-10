package com.quant_socket.models.Logs.prod;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.SG_model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;

@SG_table(name = "product_day")
public class ProductDay extends SG_model {
    @SG_idx
    @SG_column(dbField = "d_idx")
    private Long idx;
    @SG_column(dbField = "p_code")
    private String p_code;
    @SG_column(dbField = "d_close")
    private Integer close;
    @SG_column(dbField = "d_high")
    private Integer high;
    @SG_column(dbField = "d_low")
    private Integer low;
    @SG_column(dbField = "d_open")
    private Integer open;
    @SG_column(dbField = "d_volume")
    private Integer volume;
    @SG_column(dbField = "d_pre_close")
    private Integer pre_close;
    @SG_column(dbField = "d_date")
    private Date date;
    @SG_crdt
    @SG_column(dbField = "d_crdt")
    private Timestamp createdAt;

    public ProductDay(ResultSet rs) {
        resultSetToClass(rs);
    }
}
