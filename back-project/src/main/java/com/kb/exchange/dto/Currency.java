package com.kb.exchange.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Currency {
    private int currencyId;
    private String currencyCode;
    private String currencyName;
}