package com.kb.gold.service;

import com.kb.common.pagination.PageInfo;
import com.kb.gold.dto.*;
import com.kb.gold.mapper.GoldMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Log4j
@Service
@RequiredArgsConstructor
public class GoldService {

    private final GoldMapper mapper;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String KRX_API_URL = "http://data-dbg.krx.co.kr/svc/apis/gen/gold_bydd_trd";
    @Value("${GOLD_API_KEY}") String AUTH_KEY;

    private final static int LIST_LIMIT = 10;
    private final static int PAGE_LIMIT = 5;
    private void fetchAndSaveGoldDataForDate(String basDd) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTH_KEY", AUTH_KEY);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String url = KRX_API_URL + "?basDd=" + basDd;
            ResponseEntity<GoldResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, GoldResponseDto.class);
            log.info(response.getStatusCode());
            log.info(response.getHeaders());
            log.info(response.getBody());
            if (response.getBody() != null && response.getBody().getOutBlock1() != null && !response.getBody().getOutBlock1().isEmpty()) {
                List<GoldDataDto> goldDataList = response.getBody().getOutBlock1();
                mapper.insertGoldDataBatch(goldDataList);
                log.info("Gold data saved for date: " + basDd);
            } else {
                log.warn("No data for date: " + basDd);
            }
        } catch (Exception e) {
            log.error("Error fetching or saving gold data for date: " + basDd, e);
        }
    }

    public void fetchDay(String day){
        fetchAndSaveGoldDataForDate(day);
    }

    // 1년치 데이터 저장
    public void fetchAndSaveGoldDataFromSpecificDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = LocalDate.of(2023, 10, 13);  // 2024년 2월 17일을 시작 날짜로 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 시작 날짜부터 현재 날짜까지 데이터 처리
        for (LocalDate date = startDate; !date.isAfter(currentDate); date = date.plusDays(1)) {
            String formattedDate = date.format(formatter);
            fetchAndSaveGoldDataForDate(formattedDate);
        }
    }

    @Scheduled(cron = "0 0 12 * * *")  // 매일 자정에 실행
    @Transactional
    public void scheduleFetchForToday() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = today.format(formatter);
        fetchAndSaveGoldDataForDate(formattedDate);
    }

    public GoldListResponseDTO getGoldInfoList(GoldParam goldParam){
        int totalSize = mapper.getTotalCount();
        PageInfo pageInfo = new PageInfo(goldParam.getPage(), totalSize, LIST_LIMIT, PAGE_LIMIT);
        goldParam.setLimit(pageInfo.getListLimit());
        goldParam.setOffset(pageInfo.getStartList() - 1);
        List<GoldDto> goldInfoList = mapper.getGoldInfoList(goldParam);
        if (goldInfoList == null || goldInfoList.isEmpty()) {
            goldInfoList = new ArrayList<>();
        }
        return new GoldListResponseDTO(goldInfoList, goldParam, pageInfo, totalSize);
    }

}
