package com.kb.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeDailyDTO {
    private Exchange exchange;
    private Currency currency;
    private double dailyChangeRate;
    private double baseRateDifference;
}
