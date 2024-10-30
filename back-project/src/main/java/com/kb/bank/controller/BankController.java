package com.kb.bank.controller;

import com.kb.bank.dto.Bank;
import com.kb.bank.service.BankService;
import com.kb.saving.service.SavingService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
@Slf4j
@Api(value = "BankController", tags = "은행")
public class BankController {
    private final BankService service;

    @GetMapping("/list")
    public ResponseEntity<List<Bank>> getBankList(){
        return ResponseEntity.ok(service.getBankListByType());
    }

}
