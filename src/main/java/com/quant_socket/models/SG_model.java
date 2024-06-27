package com.quant_socket.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quant_socket.annotations.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Slf4j
public abstract class SG_model {

    protected int typeToSqlType(Class<?> type) {
        try {
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
        } catch (Exception e) {
            return Types.NULL;
        }
    }

    public boolean setPreparedStatement(PreparedStatement ps) {
        int index = 1;
        for (Field field : getClass().getDeclaredFields()) {
            final Class<?> type = field.getType();
            field.setAccessible(true);
            if(field.isAnnotationPresent(SG_column.class) && !field.isAnnotationPresent(SG_idx.class) && !field.isAnnotationPresent(SG_crdt.class)) {
                final SG_column sgc = field.getAnnotation(SG_column.class);
                if(sgc.useInsert()) {
                    try {
                        final Object value = field.get(this);
                        if(value == null) {
                            ps.setNull(index, this.typeToSqlType(type));
                        } else {
                            ps.setObject(index, value);
                        }
                        index++;
                    } catch (Exception ignore) {
                    }
                }
            }
        }
        return true;
    }

    protected SG_model(String msg) {
        this(msg, false);
    }

    protected SG_model(String msg, boolean withLP) {
        final Charset eucKrCharset = Charset.forName("EUC-KR");
        byte[] byteArray = msg.getBytes(eucKrCharset);
        for(Field f : this.getClass().getDeclaredFields()) {
            try {
                final int start;
                final int end;
                final Class<?> type = f.getType();
                if(withLP) {
                    final SG_substring_lp sgs = f.getAnnotation(SG_substring_lp.class);
                    start = sgs.start();
                    end = sgs.end();
                } else {
                    final SG_substring sgs = f.getAnnotation(SG_substring.class);
                    start = sgs.start();
                    end = sgs.end();
                }
                final int length = end-start;
                final byte[] subArray = new byte[end-start];
                System.arraycopy(byteArray, start, subArray, 0, length);
                final String value = new String(subArray, eucKrCharset).trim();
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
            } catch (Exception ignore) {}
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
