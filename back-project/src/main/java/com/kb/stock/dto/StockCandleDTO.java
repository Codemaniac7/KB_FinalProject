package com.kb.stock.dto;

import lombok.Data;

@Data
public class StockCandleDTO {
    private String stockCode;
    private String stockCandleDay;   // 거래 날짜
    private float stockCandleOpen;   // 시가
    private float stockCandleClose;  // 종가
    private float stockCandleHigh;   // 고가
    private float stockCandleLow;    // 저가
    private long stockCandleVolume;  // 거래량
}
