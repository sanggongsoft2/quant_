package com.quant_socket.models.Logs;
import com.quant_socket.annotations.SG_substring;
import com.quant_socket.models.SG_substring_model;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class EquitiesChangesOfBatchData extends SG_substring_model {
    /**
     * 상품이 거래되는 장소인 보드를 식별하기 위한 ID. 동일 상품이라도 여러 보드에서 집계장을 달리하고 독립적인 매매스케쥴과 매매규칙하에 각각의 가격으로 거래될 수 있으며 정규장(메인보드), 장전/후 시간외종가, 시간외단일가, 장전/중/후 대량매매 등이 보드에 해당함
     * */
    @SG_substring(start = 5, end = 7)
    private String board_id;
    /**
     * 현선물 통합상품의 종목 코드(ISIN종목코드)
     * */
    @SG_substring(start = 7, end = 19)
    private String isin_code;
    /**
     * 당일 매매의 기준이 되는 가격
     * 상한가, 하한가 산출의 기준이 되는 가격
     * **/
    @SG_substring(start = 28, end = 39)
    private Double base_price;
    /**
     * 증권사 등 외부 인터페이스에서 사용하는 문자열 상한가
     * **/
    @SG_substring(start = 39, end = 50)
    private BigDecimal upper_limit_price;
    /**
     * 증권사 등 외부 인터페이스에서 사용하는 문자열 하한가
     * **/
    @SG_substring(start = 50, end = 61)
    private BigDecimal lower_limit_price;
    /**
     * 증권사 등 외부 인터페이스에서 사용하는 문자열 평가가격
     * **/
    @SG_substring(start = 61, end = 72)
    private Double appraised_price;
    /**
     * 증권사 등 외부 인터페이스에서 사용하는 문자열 최고호가가격
     * **/
    @SG_substring(start = 72, end = 83)
    private Double high_order_price;
    /**
     * 증권사 등 외부 인터페이스에서 사용하는 문자열 최저호가가격
     * **/
    @SG_substring(start = 83, end = 94)
    private Double low_order_price;
    /**
     * 유무상 이벤트나 장기간 거래정지 등에 의해 기존 종가 외에 시가를 기준가격으로 설정하기 위한 여부(Y,N)
     * **/
    @SG_substring(start = 94, end = 95)
    private String use_trading;
    /**
     * 락구분코드(현물), 조정사유코드(파생)과 동일
     * 00:해당사항없음
     * 01:권리락
     * 02:배당락
     * 03:분배락
     * 04:권배락
     * 05:중간(분기)배당락
     * 06:권리중간배당락
     * 07:권리분기배당락
     * 99:기타
     * **/
    @SG_substring(start = 95, end = 97)
    private String rights_type_code;
    /**
     * 00:해당없음
     * 01:액면분할
     * 02:액면병합
     * 03:주식분할
     * 04:주식병합
     * 99:기타
     * **/
    @SG_substring(start = 97, end = 99)
    private String par_value_type_code;
    /**
     * 호가를 제출할 수 있는 수량의 최소단위. 호가의 수량은 수량단위의 정배수로만 제출할 수 있음
     * **/
    @SG_substring(start = 99, end = 110)
    private Long lot_sizes;
    /**
     * 종목의 상장주식수
     * **/
    @SG_substring(start = 110, end = 126)
    private Long having_count;
    /**
     * 공개정보조치 관리종목/정리매매/단기과열/초저유동성 지정여부를 송출하기 위한 공통사용항목
     * **/
    @SG_substring(start = 126, end = 127)
    private String designation;

    public EquitiesChangesOfBatchData(String msg) {
        super(msg, false);
    }

    public boolean isRealBoard() {
        return board_id != null && board_id.equals("G1");
    }
}
