package com.kb.stock.controller;

import com.kb.stock.service.HashkeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private HashkeyService hashkeyService;

    @PostMapping("/create")
    public String createOrder() {
        // 주문에 필요한 데이터
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("CANO", "00000000");
        requestBody.put("ACNT_PRDT_CD", "01");
        requestBody.put("OVRS_EXCG_CD", "SHAA");

        // Hashkey 생성
        String hashKey = hashkeyService.getHashKey(requestBody);

        // 생성된 Hashkey 사용하여 주문 API 호출 (이후 로직 구현 필요)
        if (hashKey != null) {
            // 주문 API 호출 로직 (Hashkey 포함)
            return "Hashkey: " + hashKey;
        } else {
            return "Hashkey 생성 실패";
        }
    }
}
