package com.kb.stock.service;

import com.kb.stock.dto.StockCandleDTO;
import com.kb.stock.mapper.StockCandleMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockCandleService {

    private final StockCandleMapper stockCandleMapper;

    // 생성자 주입
    public StockCandleService(StockCandleMapper stockCandleMapper) {
        this.stockCandleMapper = stockCandleMapper;
    }

    // 기간에 따라 주식 데이터를 가져오는 메서드
    public List<StockCandleDTO> getStockData(String stockCode, String period) {
        // 기간에 따른 시작 날짜 계산 (필요하다면 유틸리티로 따로 분리 가능)
        LocalDate startDate = calculateStartDate(period);
        LocalDate endDate = LocalDate.now();

        // MyBatis 매퍼를 통해 데이터를 조회
        return stockCandleMapper.getStockData(stockCode, startDate, endDate);
    }

    private LocalDate calculateStartDate(String period) {
        switch (period) {
            case "1day":
                return LocalDate.now().minusDays(1);
            case "1week":
                return LocalDate.now().minusWeeks(1);
            case "1month":
                return LocalDate.now().minusMonths(1);
            case "3months":
                return LocalDate.now().minusMonths(3);
            case "1year":
                return LocalDate.now().minusYears(1);
            case "3years":
                return LocalDate.now().minusYears(3);
            case "5years":
                return LocalDate.now().minusYears(5);
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }
}
