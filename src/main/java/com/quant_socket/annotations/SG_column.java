package com.quant_socket.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SG_column {
    /**
     * request로 받아오는 필드명 - 기본 해당 변수명
     * **/
    String reqField() default "";

    /**
     * DB 컬럼명
     * **/
    String dbField();

    /**
     * 해당 변수의 타입 - 기본 'String'
     * **/
    Class<?> type() default String.class;

    /**
     * (관리자) 목록에 표시되는지 유무
     * **/
    boolean is_show_admin() default false;

    /**
     * (관리자) thead 에 표시될 이름 - 기본 해당 변수명
     */
    String thead_name() default "";
    /**
     * (관리자) 해당 컬럼의 width 값 - 기본 '*'
     * **/
    String col_width() default "*";

    /**
     * (관리자) 체크박스인지 유무 - 기본 'false'
     * **/
    boolean is_checkbox() default false;

    /**
     * (관리자) 관리자에 표시될 순서
     * **/
    int sort() default 0;
}
