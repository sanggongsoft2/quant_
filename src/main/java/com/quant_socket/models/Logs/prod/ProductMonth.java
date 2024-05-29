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

@SG_table(name = "product_month")
public class ProductMonth extends SG_model {
    @SG_idx
    @SG_column(dbField = "m_idx")
    private Long idx;
    @SG_column(dbField = "p_code")
    private String isinCode;
    @SG_column(dbField = "m_close")
    private Integer close;
    @SG_column(dbField = "m_high")
    private Integer high;
    @SG_column(dbField = "m_low")
    private Integer low;
    @SG_column(dbField = "m_open")
    private Integer open;
    @SG_column(dbField = "m_volume")
    private Integer volume;
    @SG_column(dbField = "m_pre_close")
    private Integer pre_close;
    @SG_column(dbField = "m_date")
    private Date date;
    @SG_column(dbField = "m_crdt")
    private Timestamp createdAt = Timestamp.from(Instant.now());

    public ProductMonth(ResultSet rs) {
        resultSetToClass(rs);
    }
}
