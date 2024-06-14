package com.quant_socket.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quant_socket.annotations.*;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public abstract class SG_model {
    private Long row_num;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Long getRowNum() {
        return this.row_num;
    }

    public String getTargetUrl() {
        return "";
    }
    public String createdAtToDate(Timestamp date) {
        final LocalDate ld = date.toLocalDateTime().toLocalDate();
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return ld.format(dtf);
    }

    protected int typeToSqlType(Class<?> type) {
        if(type.equals(String.class)) {
            return Types.VARCHAR;
        } else if(type.equals(Integer.class) || type.equals(int.class)) {
            return Types.INTEGER;
        } else if(type.equals(Long.class) || type.equals(long.class)) {
            return Types.BIGINT;
        } else if(type.equals(Double.class) || type.equals(double.class)) {
            return Types.DECIMAL;
        } else if(type.equals(Float.class) || type.equals(float.class)) {
            return Types.FLOAT;
        } else {
            return Types.NULL;
        }
    }

    public boolean setPreparedStatement(PreparedStatement ps) {
        int index = 1;
        for (Field field : getClass().getDeclaredFields()) {
            final Class<?> type = field.getType();
            field.setAccessible(true);
            if(field.isAnnotationPresent(SG_column.class) && !field.isAnnotationPresent(SG_idx.class) && !field.isAnnotationPresent(SG_crdt.class)) {
                try {
                    final Object value = field.get(this);
                    if(value == null) {
                        ps.setNull(index, this.typeToSqlType(type));
                    } else {
                        ps.setObject(index, value);
                        /*if(type.equals(String.class)) ps.setString(index, (String) value);
                        else if(type.equals(Integer.class) || type.equals(int.class)) ps.setInt(index, (Integer) value);
                        else if(type.equals(Float.class) || type.equals(float.class)) ps.setFloat(index, (Float) value);
                        else if(type.equals(Double.class) || type.equals(double.class)) ps.setDouble(index, (Double) value);
                        else if(type.equals(Long.class) || type.equals(long.class)) ps.setLong(index, (Long) value);
                        else if(type.equals(Boolean.class) || type.equals(boolean.class)) ps.setBoolean(index, (Boolean) value);
                        else if(type.equals(Date.class)) ps.setDate(index, (Date) value);
                        else if(type.equals(Time.class)) ps.setTime(index, (Time) value);
                        else if(type.equals(Timestamp.class)) ps.setTimestamp(index, (Timestamp) value);*/
                    }
                } catch (Exception ignore) {
                } finally {
                    index++;
                }
            }
        }
        return true;
    }

    protected SG_model(String msg) {
        for(Field f : this.getClass().getDeclaredFields()) {
            if(f.isAnnotationPresent(SG_substring.class)) {
                final SG_substring sgs = f.getAnnotation(SG_substring.class);
                final Class<?> type = f.getType();
                try {
                    final String value = msg.substring(sgs.start(), sgs.end()).trim();
                    if(!value.isBlank()) {
                        f.setAccessible(true);
                        if(type.equals(int.class) || type.equals(Integer.class)) f.set(this, Integer.parseInt(value));
                        else if(type.equals(BigDecimal.class)) f.set(this, new BigDecimal(value));
                        else if(type.equals(boolean.class) || type.equals(Boolean.class)) f.set(this, Boolean.parseBoolean(value));
                        else if(type.equals(Long.class) || type.equals(long.class)) f.set(this, Long.parseLong(value));
                        else if(type.equals(Float.class) || type.equals(float.class)) f.set(this, Float.parseFloat(value));
                        else if(type.equals(Double.class) || type.equals(double.class)) f.set(this, Double.parseDouble(value));
                        else f.set(this, value);
                    }
                } catch (Exception ignore) {
                }
            }
        }
    }
    protected SG_model(ResultSet res) {

        for(final Field f : this.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(SG_column.class) && !f.isAnnotationPresent(SG_join.class)) {
                final SG_column sc = f.getAnnotation(SG_column.class);
                try {
                    if (res.getObject(sc.dbField()) != null) {
                        f.setAccessible(true);
                        f.set(this, res.getObject(sc.dbField()));
                    }
                } catch (SQLException | IllegalAccessException ignore) {
                }
            }
        }
    }

}
