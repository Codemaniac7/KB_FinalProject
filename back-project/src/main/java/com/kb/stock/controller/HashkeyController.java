package com.kb.stock.controller;

import com.kb.stock.service.HashkeyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(tags = {"Hashkey API"}, description = "Hashkey 생성 API")
public class HashkeyController {

    @Autowired
    private HashkeyService hashkeyService;

    @ApiOperation(value = "Hashkey 생성", notes = "POST 요청의 Body 값을 보호하기 위해 Hashkey를 생성하는 API입니다.")
    @PostMapping("/hashkey")
    public String generateHashkey(
            @ApiParam(value = "POST 요청에서 전송할 Body 값", required = true)
            @RequestBody Map<String, Object> requestBody) {
        // Hashkey 생성 로직
        String hashKey = hashkeyService.getHashKey(requestBody);

        if (hashKey != null) {
            return "Hashkey: " + hashKey;
        } else {
            return "Hashkey 생성 실패";
        }
    }
}
