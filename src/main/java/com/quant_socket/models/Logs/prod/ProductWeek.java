package com.quant_socket.models.Logs.prod;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.SG_model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@SG_table(name = "product_week")
public class ProductWeek extends SG_model {
    @SG_idx
    @SG_column(dbField = "w_idx")
    private Long idx;
    @SG_column(dbField = "p_code")
    private String isinCode;
    @SG_column(dbField = "w_close")
    private Integer close;
    @SG_column(dbField = "w_high")
    private Integer high;
    @SG_column(dbField = "w_low")
    private Integer low;
    @SG_column(dbField = "w_open")
    private Integer open;
    @SG_column(dbField = "w_volume")
    private Integer volume;
    @SG_column(dbField = "w_pre_close")
    private Integer pre_close;
    @SG_column(dbField = "w_date")
    private Date date;
    @SG_column(dbField = "w_crdt")
    private Timestamp createdAt;

    public ProductWeek(ResultSet rs) {
        resultSetToClass(rs);
    }
}
