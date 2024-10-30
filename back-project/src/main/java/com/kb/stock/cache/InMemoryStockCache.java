package com.kb.stock.cache;

import com.kb.stock.dto.StockDTO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStockCache {

    private Map<String, StockDTO> stockCache = new ConcurrentHashMap<>();

    // 주식 데이터 저장
    public void updateStockData(String stockCode, StockDTO stockData) {
        stockCache.put(stockCode, stockData);
    }

    // 주식 데이터 조회
    public StockDTO getStockData(String stockCode) {
        return stockCache.get(stockCode);
    }

    // 전체 주식 데이터 조회
    public Map<String, StockDTO> getAllStockData() {
        return stockCache;
    }
}
