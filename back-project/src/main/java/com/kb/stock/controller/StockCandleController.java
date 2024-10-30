package com.kb.stock.controller;

import com.kb.stock.dto.StockCandleDTO;
import com.kb.stock.service.StockCandleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stockcandle")
@Api(tags = {"StockCandle API"}, description = "주식 차트 데이터 API")
public class StockCandleController {

    private final StockCandleService stockCandleService;

    public StockCandleController(StockCandleService stockCandleService) {
        this.stockCandleService = stockCandleService;
    }

    // 특정 종목의 기간별 주식 데이터를 조회하는 API
    @ApiOperation(value = "특정 종목의 기간별 주식 데이터를 조회",
            notes = "주식 코드와 기간에 맞는 데이터를 반환합니다.")
    @GetMapping("/{stockCode}/{period}")
    public List<StockCandleDTO> getStockCandleData(
            @ApiParam(value = "주식 코드", required = true) @PathVariable String stockCode,
            @ApiParam(value = "기간 (1day, 1week, 1month, 1year, 3years, 5years)", required = true) @PathVariable String period) {

        return stockCandleService.getStockData(stockCode, period);
    }
}
