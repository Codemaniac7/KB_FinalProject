package com.kb.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExchangeFee {
    private int bankFeeId;
    private int currencyId;
    private String bankId;
    private double buyingFee;
    private double sellingFee;
    private Date baseDate;

}
