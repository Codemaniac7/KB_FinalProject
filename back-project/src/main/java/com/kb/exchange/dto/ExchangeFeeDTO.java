package com.kb.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeFeeDTO {
    private String currencyCode;
    private String bankName;
    private String bankUrl;
    private double buyingFee;
    private double sellingFee;
    private Date baseDate;
}
