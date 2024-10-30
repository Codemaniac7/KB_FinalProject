package com.kb.funds.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kb.funds.dto.FundsDTO;
import com.kb.funds.service.FundsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/funds")
public class FundsController {

    private final FundsService fundsService;

    // 크롤링하여 펀드 데이터를 저장하는 엔드포인트
    @GetMapping("/crawl")
    public String crawlAndSaveFunds() throws JsonProcessingException {
        fundsService.crawlAndSaveFunds();
        return "Funds have been crawled and saved successfully!";
    }

    // 펀드 검색 엔드포인트 (옵션)
    @GetMapping("/search")
    public List<FundsDTO> searchFunds(@RequestParam String keyword) {
        return fundsService.searchFunds(keyword);
    }

    // 모든 펀드 조회 엔드포인트 (옵션)
    @GetMapping("")
    public ResponseEntity<List<FundsDTO>> findAllFunds(
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(fundsService.findAllFunds(grade, category));
    }
}
