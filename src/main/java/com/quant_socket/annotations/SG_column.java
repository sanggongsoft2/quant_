package com.quant_socket.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SG_column {
    /**
     * DB 컬럼명
     * **/
    String dbField();
    boolean useInsert() default true;
}
