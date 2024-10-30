package com.kb.funds.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kb._config.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundsDTO {
    private Long id; // ID 필드 추가
    private String fundFnm;
    // 기준가
    private BigDecimal gijunGa;
    // 벤치마크
    private String bmNm;
    // 1개월 수익률
    private BigDecimal suikRt1;
    // 3개월 수익률
    private BigDecimal suikRt3;
    // 6개월 수익률
    private BigDecimal suikRt6;
    // 1년 수익률
    private BigDecimal suikRt12;
    // 투자 위험 등급
    private int investGrade;
    // 총 보수
    private BigDecimal feeTot;

    @JsonDeserialize(using = LocalDateDeserializer.class) //
    private LocalDate gijunYmd;
    // 순 자산
    private BigDecimal navTot;

    private String fundCd;
}
