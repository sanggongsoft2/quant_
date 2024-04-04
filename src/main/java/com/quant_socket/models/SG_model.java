package com.quant_socket.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quant_socket.annotations.SG_column;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public abstract class SG_model<T> {
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
    protected void resultSetToClass(ResultSet res) {
        try {
            if(res.findColumn("row_num") > 0) this.row_num = res.getLong("row_num");
        } catch (SQLException e) {
        }

        for(final Field f : this.getClass().getDeclaredFields()) {
            final Class<?> type = f.getType();

            if (f.isAnnotationPresent(SG_column.class)) {
                final SG_column sc = f.getAnnotation(SG_column.class);
                try {
                    if (res.getObject(sc.dbField()) != null && !res.wasNull()) {
                        f.setAccessible(true);
                        if (type.equals(String.class)) f.set(this, res.getString(sc.dbField()));
                        else if (type.equals(Long.class) || type.equals(long.class))
                            f.set(this, res.getLong(sc.dbField()));
                        else if (type.equals(int.class) || type.equals(Integer.class))
                            f.set(this, res.getInt(sc.dbField()));
                        else if (type.equals(boolean.class) || type.equals(Boolean.class))
                            f.set(this, res.getBoolean(sc.dbField()));
                        else if (type.equals(Timestamp.class)) f.set(this, res.getTimestamp(sc.dbField()));
                        else if (type.equals(Date.class)) f.set(this, res.getDate(sc.dbField()));
                        else if (type.equals(Double.class) || type.equals(double.class)) f.set(this, res.getDouble(sc.dbField()));
                        else if (type.equals(Float.class) || type.equals(float.class)) f.set(this, res.getFloat(sc.dbField()));
                        else f.set(this, res.getObject(sc.dbField()));
                    }
                } catch (IllegalAccessException | SQLException ignored) {

                }
            }
        }
    }

    private class CustomSGModel {
        private Field field;
        private SG_column sgColumn;
        private Object value;

        public CustomSGModel(Field field, SG_column sgColumn, Object value) {
            this.field = field;
            this.sgColumn = sgColumn;
            this.value = value;
        }
    }

    public String toJson() {
        final JSONObject sobj = new JSONObject();
        for(Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                final Object value = field.get(this);
                if(value != null) sobj.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return sobj.toJSONString();
    }

    public void fromJson(String json) {
        try {
            final ObjectMapper om = new ObjectMapper();
            final SG_model sm = om.readValue(json, this.getClass());
            log.debug("SM = {}", sm.toJson());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> toMap() throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        for (Field field : getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(this));
            } catch (IllegalAccessException ignored) {

            }
        }
        return map;
    }
}
