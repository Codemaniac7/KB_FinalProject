package com.kb.gold.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoldDto {
    private String basDd; // 기준일자
    private String isuCd; // 종목코드
    private int cmpPrevDdPrc; // 전일 대비
    private double flucRt; // 등락률
    private int tddOpnPrc; // 시가
    private double price; // 한돈 가격

}
