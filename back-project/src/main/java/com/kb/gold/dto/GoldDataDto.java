package com.kb.gold.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoldDataDto {
    @JsonProperty("BAS_DD")// 기준일자
    private String basDd;

    @JsonProperty("ISU_CD")// 종목코드
    private String isuCd;

    @JsonProperty("ISU_NM")// 종목명
    private String isuNm;

    @JsonProperty("TDD_CLSPRC")// 종가
    private String tddClsPrc;

    @JsonProperty("CMPPREVDD_PRC")// 대비
    private String cmpPrevDdPrc;

    @JsonProperty("FLUC_RT")// 등락률
    private String flucRt;

    @JsonProperty("TDD_OPNPRC")// 시가
    private String tddOpnPrc;

    @JsonProperty("TDD_HGPRC")// 고가
    private String tddHgPrc;

    @JsonProperty("TDD_LWPRC")// 저가
    private String tddLwPrc;

    @JsonProperty("ACC_TRDVOL")// 거래량
    private String accTrdVol;

    @JsonProperty("ACC_TRDVAL")// 거래대금
    private String accTrdVal;              

}
