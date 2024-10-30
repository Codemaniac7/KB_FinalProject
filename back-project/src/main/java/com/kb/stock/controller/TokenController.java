
package com.kb.stock.controller;

import com.kb.stock.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token")
@CrossOrigin(origins = "http://localhost:8081")
@Api(tags = {"Token API"}, description = "토큰 관련 API")
public class TokenController {

    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private TokenService tokenService;

    @ApiOperation(value = "Access Token을 가져옵니다.", notes = "한국투자증권 API의 Access Token을 가져오는 메서드입니다.")
    @GetMapping
    public ResponseEntity<String> getAccessToken() {
        logger.info("getAccessToken 메서드 호출됨");
        try {
            String accessToken = tokenService.getAccessToken();
            logger.info("발급된 Access Token: {}", accessToken);
            if (accessToken != null) {
                logger.info("토큰 발급 성공");
                return ResponseEntity.ok(accessToken);
            } else {
                logger.error("토큰 발급 실패");
                return ResponseEntity.status(500).body("Failed to get access token");
            }
        } catch (Exception e) {
            logger.error("토큰 발급 중 예외 발생", e);
            return ResponseEntity.status(500).body("Error occurred while getting access token");
        }
    }
}
