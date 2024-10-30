package com.kb.exchange.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeCategory {
    private List<Currency> currencyCategory;
    private List<Currency> exchangeBankCategory;
}
