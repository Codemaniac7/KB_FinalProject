package com.kb.stock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${kis.api.appkey}")
    private String appKey;

    @Value("${kis.api.secretkey}")
    private String appSecret;

    @Value("${kis.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private String currentAccessToken;
    private LocalDateTime tokenIssuedTime;
    private long tokenExpiryDuration = 24 * 60 * 60; // 24시간 (초)

    // 토큰 발급 API 호출 메서드
    public String requestNewToken() {
        logger.info("새로운 Access Token 요청 중...");

        String url = baseUrl + "/oauth2/tokenP";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=utf-8");

        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "client_credentials");
        body.put("appkey", appKey);
        body.put("appsecret", appSecret);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                currentAccessToken = (String) response.getBody().get("access_token");
                long expiresIn = (Integer) response.getBody().get("expires_in"); // 토큰 유효기간 (초)
                tokenExpiryDuration = expiresIn;
                tokenIssuedTime = LocalDateTime.now(); // 토큰 발급 시간 저장
                logger.info("Access Token 발급 성공: {}", currentAccessToken);
                return currentAccessToken;
            } else {
                logger.error("Access Token 발급 실패: {}", response.getBody());
                return null;
            }
        } catch (Exception e) {
            logger.error("Access Token 발급 중 오류 발생", e);
            return null;
        }
    }

    // 토큰 유효성 확인 메서드
    public boolean isTokenValid() {
        if (currentAccessToken == null || tokenIssuedTime == null) {
            return false; // 토큰이 없으면 유효하지 않음
        }

        long secondsElapsed = ChronoUnit.SECONDS.between(tokenIssuedTime, LocalDateTime.now());
        return secondsElapsed < tokenExpiryDuration; // 24시간 내에 유효한지 확인
    }

    // 유효한 토큰을 가져오는 메서드 (없으면 새로 발급)
    public String getAccessToken() {
        if (isTokenValid()) {
            logger.info("기존 유효한 토큰 반환");
            return currentAccessToken;
        } else {
            return requestNewToken(); // 토큰이 없거나 만료되었으면 새로 발급
        }
    }


}