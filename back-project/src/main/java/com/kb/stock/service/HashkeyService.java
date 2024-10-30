package com.kb.stock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HashkeyService {

    private static final Logger logger = LoggerFactory.getLogger(HashkeyService.class);

    @Value("${kis.api.appkey}")
    private String appKey;

    @Value("${kis.api.secretkey}")
    private String appSecret;

    @Value("${kis.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Hashkey 생성 메서드
     * @param requestBody POST로 보낼 Body 데이터
     * @return 생성된 Hashkey
     */
    public String getHashKey(Map<String, Object> requestBody) {
        try {
            String url = baseUrl + "/uapi/hashkey";
            logger.info("Hashkey 요청 URL: {}", url);

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json; charset=utf-8");
            headers.set("appkey", appKey);
            headers.set("appsecret", appSecret);

            // 요청 보낼 데이터와 헤더
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // POST 요청으로 Hashkey 받기
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                String hashKey = (String) responseBody.get("HASH");
                logger.info("Hashkey 생성 성공: {}", hashKey);
                return hashKey;
            } else {
                logger.error("Hashkey 생성 실패: {}", response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            logger.error("Hashkey 생성 중 예외 발생", e);
            return null;
        }
    }
}
