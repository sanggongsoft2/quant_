package com.quant_socket.models;

import com.quant_socket.annotations.SG_substring;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;

@Slf4j
abstract public class SG_substring_model {

    protected SG_substring_model(String msg) {
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
}
