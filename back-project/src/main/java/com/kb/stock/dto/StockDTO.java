package com.kb.stock.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockDTO {
    private Long id;
    private String stockCode;
    private String stockName;
    private BigDecimal currentPrice;
    private BigDecimal priceChange;
    private BigDecimal priceChangePct;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal openingPrice;
    private Long volume;
    private LocalDateTime lastUpdated;
    private String industry;
    private BigDecimal htsAvls; // HTS 시가총액 추가
    private BigDecimal w52Hgpr; // 52주일 최고가 추가
    private BigDecimal w52Lwpr; // 52주일 최저가 추가
    private BigDecimal acmlTrPbmn;

    // 투자 성향 필드 추가
    private String investmentCategory; // 투자 성향 (안정성, 성장성, 배당성, 변동성, 공격성)
}
