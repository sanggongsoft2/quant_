package com.quant_socket.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SG_table {
    /**
     * DB에 저장되어 있는 테이블명
     * **/
    String name();

    /**
     * (관리자) 페이지 제목
     * **/
    String pageTitle() default "";

    /**
     * queryString 에 들어갈 파미터 목록
     * **/
    String[] params() default {};

    /**
     * (관리자) 번호컬럼의 순서
     * **/
    int rowNumSort() default 1;

    /**
     * (관리자) 번호 컬럼이 포함되는지 유무 - 기본 'true'
     * **/
    boolean hasRowNum() default true;

    /**
     * (관리자) 번호 컬럼의 이름 - 기본 '번호'
     * **/
    String rowNumName() default "번호";

    /**
     * (관리자) 번호 컬럼의 width값 - 기본 '5%'
     * **/
    String rowNumWidth() default "5%";
}
