package com.kb.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StockListService {

    @Autowired
    private StockService stockService;

    public List<Map<String, Object>> getStockPrices(List<String> stockCodes) {
        List<Map<String, Object>> stockDataList = new ArrayList<>();
        for (String stockCode : stockCodes) {
            Map<String, Object> stockData = stockService.getStockPrice(stockCode);
            if (stockData != null) {
                stockDataList.add(stockData);
            }
        }
        return stockDataList;
    }
}
