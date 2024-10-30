package com.kb.stock.controller;

import com.kb.stock.dto.StockDTO;
import com.kb.stock.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "http://localhost:8081")
@Api(tags = {"Stock API"}, description = "주식 관련 API")
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    // 모든 주식 데이터를 조회합니다.
    @ApiOperation(value = "모든 주식 데이터를 조회합니다.", notes = "데이터베이스에 저장된 모든 주식 데이터를 조회합니다.")
    @GetMapping("/all")
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        logger.info("getAllStocks 메서드 호출됨");
        try {
            List<StockDTO> stocks = stockService.getAllStocks();
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            logger.error("모든 주식 데이터 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 모든 주식 코드를 조회합니다.
    @ApiOperation(value = "모든 주식 코드를 조회합니다.", notes = "데이터베이스에 저장된 모든 주식 코드를 조회합니다.")
    @GetMapping("/codes")
    public ResponseEntity<List<String>> getAllStockCodes() {
        logger.info("getAllStockCodes 메서드 호출됨");
        try {
            List<String> stockCodes = stockService.getAllStockCodes();
            return ResponseEntity.ok(stockCodes);
        } catch (Exception e) {
            logger.error("모든 주식 코드 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 카테고리별 평균 등락률 조회
    @ApiOperation(value = "카테고리별 평균 등락률을 조회합니다.", notes = "각 카테고리의 평균 등락률과 관련된 데이터를 조회합니다.")
    @GetMapping("/categories")
    public ResponseEntity<List<Map<String, Object>>> getCategoryRankings() {
        logger.info("getCategoryRankings 메서드 호출됨");
        try {
            List<Map<String, Object>> categoryRankings = stockService.getCategoryRankings();
            return ResponseEntity.ok(categoryRankings);
        } catch (Exception e) {
            logger.error("카테고리별 평균 등락률 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 안정성 중심 주식 조회
    @ApiOperation(value = "안정성 중심 주식을 조회합니다.", notes = "안정성 중심 투자 성향에 맞는 주식을 조회합니다.")
    @GetMapping("/stable")
    public ResponseEntity<List<StockDTO>> getStableStocks() {
        logger.info("getStableStocks 메서드 호출됨");
        try {
            List<StockDTO> stableStocks = stockService.getStableStocks();
            return ResponseEntity.ok(stableStocks);
        } catch (Exception e) {
            logger.error("안정성 중심 주식 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 성장성 중심 주식 조회
    @ApiOperation(value = "성장성 중심 주식을 조회합니다.", notes = "성장성 중심 투자 성향에 맞는 주식을 조회합니다.")
    @GetMapping("/growth")
    public ResponseEntity<List<StockDTO>> getGrowthStocks() {
        logger.info("getGrowthStocks 메서드 호출됨");
        try {
            List<StockDTO> growthStocks = stockService.getGrowthStocks();
            return ResponseEntity.ok(growthStocks);
        } catch (Exception e) {
            logger.error("성장성 중심 주식 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 배당성 중심 주식 조회
    @ApiOperation(value = "배당성 중심 주식을 조회합니다.", notes = "배당성 중심 투자 성향에 맞는 주식을 조회합니다.")
    @GetMapping("/dividend")
    public ResponseEntity<List<StockDTO>> getDividendStocks() {
        logger.info("getDividendStocks 메서드 호출됨");
        try {
            List<StockDTO> dividendStocks = stockService.getDividendStocks();
            return ResponseEntity.ok(dividendStocks);
        } catch (Exception e) {
            logger.error("배당성 중심 주식 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 변동성 중심 주식 조회
    @ApiOperation(value = "변동성 중심 주식을 조회합니다.", notes = "변동성 중심 투자 성향에 맞는 주식을 조회합니다.")
    @GetMapping("/volatile")
    public ResponseEntity<List<StockDTO>> getVolatileStocks() {
        logger.info("getVolatileStocks 메서드 호출됨");
        try {
            List<StockDTO> volatileStocks = stockService.getVolatileStocks();
            return ResponseEntity.ok(volatileStocks);
        } catch (Exception e) {
            logger.error("변동성 중심 주식 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 공격성 중심 주식 조회
    @ApiOperation(value = "공격성 중심 주식을 조회합니다.", notes = "공격성 중심 투자 성향에 맞는 주식을 조회합니다.")
    @GetMapping("/aggressive")
    public ResponseEntity<List<StockDTO>> getAggressiveStocks() {
        logger.info("getAggressiveStocks 메서드 호출됨");
        try {
            List<StockDTO> aggressiveStocks = stockService.getAggressiveStocks();
            return ResponseEntity.ok(aggressiveStocks);
        } catch (Exception e) {
            logger.error("공격성 중심 주식 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
