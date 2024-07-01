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
import java.util.LinkedHashMap;
import java.util.Map;

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
        final LocalDateTime now = LocalDateTime.now();
        final double currentPrice = prod.getOpenPrice();
        this.isinCode = prod.getCode();
        this.close = currentPrice;
        this.high = currentPrice;
        this.low = currentPrice;
        this.open = currentPrice;
        this.pre_close = currentPrice;
        this.date = Date.valueOf(now.toLocalDate());
        this.time = Time.valueOf(now.toLocalTime());
    }

    public void update(SecOrderFilled data) {
        final double trading_price = data.getTrading_price();
        final LocalDateTime now = data.getUnixTimestamp();
        this.isinCode = data.getIsin_code();
        if(trading_price > 0) {
            this.close = trading_price;
            if(trading_price >= this.high) this.high = trading_price;
            if(trading_price <= this.low) this.low = trading_price;
            this.open = data.getOpening_price();
            this.volume += data.getTrading_volume();
            this.date = Date.valueOf(now.toLocalDate());
            this.time = Time.valueOf(now.toLocalTime());
        }
    }

    public void resetVolume() {
        this.pre_close = close;
        this.volume = 0;
        this.high = this.close;
        this.low = this.close;
    }

    public Map<String, Object> toSocket() {
        final Map<String, Object> data = new LinkedHashMap<>();
        data.put("Date", date.getTime() + time.getTime());
        data.put("Close", close);
        data.put("High", high);
        data.put("Low", low);
        data.put("Open", pre_close);
        data.put("Volume", volume);
        return data;
    }
}
