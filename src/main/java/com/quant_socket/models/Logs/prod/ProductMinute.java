package com.quant_socket.models.Logs.prod;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.Logs.SecOrderFilled;
import com.quant_socket.models.Product;
import com.quant_socket.models.SG_model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@SG_table(name = "product_minute")
@Getter
@Setter
@ToString
@Slf4j
public class ProductMinute extends SG_model {
    @SG_idx
    @SG_column(dbField = "m_idx")
    private Long idx;
    @SG_column(dbField = "p_code")
    private String isinCode;
    @SG_column(dbField = "m_close")
    private double close = 0;
    @SG_column(dbField = "m_high")
    private double high = 0;
    @SG_column(dbField = "m_low")
    private double low = 0;
    @SG_column(dbField = "m_open")
    private double open = 0;
    @SG_column(dbField = "m_volume")
    private long volume = 0;
    @SG_column(dbField = "m_pre_close")
    private double pre_close = 0;
    @SG_column(dbField = "m_date", useInsert = false)
    private Date date;
    @SG_column(dbField = "m_time", useInsert = false)
    private Time time;

    public ProductMinute(Product prod) {
        synchronized (this) {
            final double currentPrice = prod.getOpenPrice();
            this.isinCode = prod.getCode();
            this.close = currentPrice;
            this.high = currentPrice;
            this.low = currentPrice;
            this.open = currentPrice;
            this.pre_close = currentPrice;
        }
    }

    public void update(SecOrderFilled data) {
        synchronized (this) {
            final double trading_price = data.getTrading_price();
            this.isinCode = data.getIsin_code();
            this.close = trading_price;
            if(trading_price >= this.high) this.high = trading_price;
            if(trading_price <= this.low) this.low = trading_price;
            this.open = data.getOpening_price();
            this.volume += data.getTrading_volume();
            this.pre_close = trading_price;
        }
    }

    public void resetVolume() {
        this.pre_close = close;
        this.volume = 0;
    }
}
