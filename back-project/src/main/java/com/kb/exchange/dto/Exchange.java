package com.kb.exchange.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Exchange {
    private int rateId;
    private int currencyId;
    private double receivingPrice;
    private double sendingPrice;
    private double basePrice;
    private Date exchangeRateDate;

}