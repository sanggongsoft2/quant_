package com.quant_socket.models.Logs.prod;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.Product;
import com.quant_socket.models.SG_model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@SG_table(name = "product_day")
public class ProductDay extends SG_model {
    @SG_idx
    @SG_column(dbField = "d_idx")
    private Long idx;
    @SG_column(dbField = "p_code")
    private String isinCode;
    @SG_column(dbField = "d_close")
    private Double close;
    @SG_column(dbField = "d_high")
    private Double high;
    @SG_column(dbField = "d_low")
    private Double low;
    @SG_column(dbField = "d_open")
    private Double open;
    @SG_column(dbField = "d_volume")
    private Long volume;
    @SG_column(dbField = "d_pre_close")
    private Double pre_close;
    @SG_column(dbField = "d_date")
    private Date date;
    @SG_column(dbField = "d_crdt")
    private Timestamp createdAt;

    public ProductDay(ResultSet rs) {
        resultSetToClass(rs);
    }

    static public String[] insertCols() {
        return new String[] {
                "p_code",
                "d_close",
                "d_high",
                "d_low",
                "d_open",
                "d_volume",
                "d_pre_close",
                "d_date",
                "d_crdt",
        };
    }

    public ProductDay(Product prod) {
        Instant now = Instant.now();
        ZonedDateTime koreaTime = now.atZone(ZoneId.of("Asia/Seoul"));
        createdAt = Timestamp.from(koreaTime.toInstant());

        this.isinCode = prod.getCode();
        this.close = prod.getCurrentPrice();
        this.high = prod.getHighPrice();
        this.low = prod.getLowPrice();
        this.open = prod.getOpenPrice();
        this.volume = prod.getTradingVolume();
        this.pre_close = prod.getCurrentPrice();
        this.date = Date.valueOf(createdAt.toLocalDateTime().toLocalDate());
    }
}
