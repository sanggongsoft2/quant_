package com.quant_socket.models.Logs;
import com.quant_socket.annotations.SG_substring;
import com.quant_socket.models.SG_substring_model;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class EquitiesBatchData extends SG_substring_model {
    /**
     * 현선물 통합상품의 종목 코드(ISIN종목코드)
     * */
    @SG_substring(start = 27, end = 39)
    private String isin_code;
    /**
     * 종목코드(12자리 표준 ISIN코드) 보다 짧으면서도 유일성이 보장되는 9자리 코드
     * */
    @SG_substring(start = 45, end = 54)
    private String short_code;
    /**
     * 정보분배에서 분배하는 정보의 구분 코드값
     * */
    @SG_substring(start = 2, end = 5)
    private String info_category;
    /**
     * 종목약명
     * A003S0000053600087520240618KR7423920008001557A423920  TIGER 미국필라델피아반도체레버리지(합성)TIGER SYNTH-US PHLX Semiconductor 2X    00006STKEFN0000N00N1N00NNNNN          N 000000031415100000031415000000227100000000007147519730.0000000005025500000012575000000207300000000.00000000010000202204190000000008570000N                                000000000.000000000000000000000.000Y0000700007000010000700001000N0000000000000000000000000000000000000000000100000000001             KRW410NYYYYYN000002.000000NN2000066.000000N        0S                          213020601007P1P2010200           YNN0000000000000085700.000NNN
     * A001S0000072000104620240618KR7047400007000720A047400  유니온머티리얼                          UNIONMaterials                          00003STKSTN0000N00N1N00NNNNN032302    N 000000002660100000002660000000124071000000000330998585.0000000000345500000001865000000017000000500.00000000017500200909290000000042000000N                                000000000.000000000021000000000.000Y0000700007000010000700001000N0000000000000000000000000000000000000000000100000000001             KRW410NYYYYYN000000.000000NN0000064.000000N        0                                                       00005NNN0000000000000420000.000NNN
     * */
    @SG_substring(start = 54, end = 94)
    private String abbr_issue_name_kr;
    /**
     * 종목영문약명
     * */
    @SG_substring(start = 94, end = 134)
    private String abbr_issue_name_en;
    /**
     * 증권그룹 ID (주식,투자회사,ETF,ELW,선물,옵션 등)
     * */
    @SG_substring(start = 142, end = 144)
    private String sec_group_id;

    /**
     * "재평가종목사유코드
     * 00:해당없음
     * 01:회사분할
     * 02:자본감소
     * 03:장기간정지
     * 04:초과분배
     * 05:대규모배당
     * 06:회사분할합병
     * 07:ETN증권병합∙분할
     * 99:기타"
     * */
    @SG_substring(start = 152, end = 153)
    private String base_price_change;
    /**
     * "1:우량기업부
        2:벤처기업부
        3:중견기업부
        4:신성장기업부
        A:외국기업(소속부없음)
        B:투자회사(소속부없음)
        C:SPAC(소속부없음)
        D:ETF(소속부없음)
        E:관리종목(소속부없음)
        F:투자주의환기종목(소속부없음)
        J:일반(구 소속부)
        K:벤처(구 소속부)
        L:MF(구 소속부)
        M:ETF(구 소속부)
        N:외국기업(구 소속부)
        W:크라우드펀딩기업부
        X:일반기업부
        Y:스타트업기업부
        Z:기타(소속부없음)"
     * */
    @SG_substring(start = 173, end = 174)
    private String section_type_code;
    /**
     * 종류주권의 구분
        0: 보통주
        1: 구형우선주
        2: 신형우선주
        9: 종류주권
     * */
    @SG_substring(start = 407, end = 408)
    private String other_stock_type_code;
    /**
     * 액면가격
     * */
    @SG_substring(start = 265, end = 276)
    private Double par_value;
    /**
     * 종목의 상장주식수
     * */
    @SG_substring(start = 295, end = 311)
    private Long number_of_listed_shares;
    /**
     * 종목의 전일 종가
     * */
    @SG_substring(start = 187, end = 198)
    private BigDecimal yesterday_closing_price;
    /**
     * "당일 매매의 기준이 되는 가격
     * 상한가, 하한가 산출의 기준이 되는 가격"
     * */
    @SG_substring(start = 175, end = 186)
    private BigDecimal base_price;
    /**
     * 전일누적거래량
     * */
    @SG_substring(start = 198, end = 210)
    private Long yesterday_trading_volume;
    /**
     * 전일누적거래대금
     * */
    @SG_substring(start = 210, end = 232)
    private Float yesterday_trading_value;

    public EquitiesBatchData(String msg) {
        this(msg, false);
    }

    public EquitiesBatchData(String msg, boolean withLP) {
        super(msg, withLP);
    }
}
