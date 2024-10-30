package com.kb.exchange.controller;


import com.kb.exchange.dto.*;
import com.kb.exchange.service.ExchangeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
@Slf4j
@Api(value = "ExchangeController", tags = "예금")
public class ExchangeController {
    private final ExchangeService service;

    @PostMapping("/fetch-exchange-rates")
    public String fetchExchangeRates() {
        service.fetchAndSaveExchangeRates();
        return "저장 완료";
    }

    @PostMapping("/crawl-exchange-fees")
    public String fetchExchangeFee() {
        service.crawlExchangeFee();
        return "저장 완료";
    }

    @GetMapping("/daily")
    public ResponseEntity<ExchangeDailyDTO> getDailyExchangeList(
            @RequestParam(value="currencyId") int currencyId) {
        return ResponseEntity.ok(service.getDailyExchange(currencyId));
    }

    @GetMapping("/detail/{currencyId}") // 1month, 3months, 6months, 1year 변수
    public ResponseEntity<List<ExchangeListByTerm>> getExchangeListByTerm(
            @PathVariable int currencyId,
            @RequestParam(value = "term", defaultValue = "3months") String term){
        ExchangeParam exchangeParam = new ExchangeParam(currencyId, term);
        return ResponseEntity.ok(service.getExchangeListByTerm(exchangeParam));
    }

    @GetMapping("/fee")
    public ResponseEntity<List<ExchangeFee>> getExchangeFeeByCurrency(
            @RequestParam(value = "currencyId", defaultValue = "23") int currencyId) {
        return ResponseEntity.ok(service.getExchangeFeeList(currencyId));
    }

    @GetMapping("/category")
    public ResponseEntity<ExchangeCategory> getCurrencyCategory(){
        return ResponseEntity.ok().body(service.getCategory());
    }
    
}
