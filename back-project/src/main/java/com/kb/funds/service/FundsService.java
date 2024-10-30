
package com.kb.funds.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.funds.dto.FundsDTO;
import com.kb.funds.mapper.FundsMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FundsService {
    private final ObjectMapper objectMapper;
    private final FundsMapper fundsMapper;
    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(FundsService.class);
    @Transactional
    public void crawlAndSaveFunds() throws JsonProcessingException {
        int pageNo = 1;
        boolean hasMoreFunds = true;
        while (hasMoreFunds) {
            List<FundsDTO> funds = crawlFundsFromWebsite(pageNo);
            if (funds.isEmpty()) {
                hasMoreFunds = false;
            } else {
                for (FundsDTO fund : funds) {
                    try {
                        // Fund ID가 null인 경우, 새 펀드 추가
                        if (fund.getId() == null) {
                            logger.debug("Inserting fund with gijunYmd: {}", fund.getGijunYmd());
                            fundsMapper.insertFund(fund);
//                            logger.info("Inserted new fund: {}", fund.getFundCd());
                        } else {
                            // 기존 펀드 업데이트
                            fundsMapper.updateFund(fund);
                        }
                        // 상세 데이터를 가져와서 펀드 객체에 추가
//                        List<FundsDTO> detailedCharts = crawlDetailedData(fund);
//                        for (FundsDTO detailedChart : detailedCharts) {
//                            // 필요한 필드만 업데이트
//                            fund.setCategory(detailedChart.getCategory());
//                            fund.setEvaluationAmount(detailedChart.getEvaluationAmount());
//                            fund.setWeight(detailedChart.getWeight());
//                        }
                        // 펀드 정보 업데이트 (기본 정보만)
                        logger.debug("Updating fund: {}", fund);
                        fundsMapper.updateFund(fund);
                    } catch (Exception e) {
                        logger.error("Error occurred while processing fund ID: {}", fund.getId(), e);
                    }
                }
                pageNo++;
            }
        }
    }
    private Long generateFundId() {
        // fundId를 생성하는 로직을 구현
        return System.currentTimeMillis(); // 예시: 현재 시간을 사용해 생성
    }


    private List<FundsDTO> crawlFundsFromWebsite(int pageNo) throws JsonProcessingException {
        String url = "https://www.samsungfund.com/api/v1/fund/product.do?graphTerm=1&orderBy=DESC&orderByType=SUIK_RT3&pageNo=" + pageNo;
        String jsonResponse = restTemplate.getForObject(url, String.class);

        // JSON을 FundsDTO 리스트로 변환
        return objectMapper.readValue(jsonResponse, new TypeReference<List<FundsDTO>>() {});
    }

    @Scheduled(fixedRate = 43200000) // 12시간마다 실행
    public void scheduleCrawl() {
        try {
            crawlAndSaveFunds();
        } catch (JsonProcessingException e) {
            logger.error("Error occurred while crawling funds: {}", e.getMessage(), e);
        }
    }

    public List<FundsDTO> searchFunds(String keyword) {
        return fundsMapper.searchFunds(keyword);
    }

    public List<FundsDTO> findAllFunds(String grade, String category) {
        List<FundsDTO> funds = fundsMapper.findAllFunds();

        // 수익률 필터링: 모든 수익률 필드가 null인 펀드 제외
        funds = funds.stream()
                .filter(fund ->
                        fund.getSuikRt1() != null ||
                                fund.getSuikRt3() != null ||
                                fund.getSuikRt6() != null ||
                                fund.getSuikRt12() != null
                )
                .collect(Collectors.toList());

        // 등급 필터링
        if (grade != null && !grade.isEmpty()) {
            String[] grades = grade.split("-");
            int gradeStart = Integer.parseInt(grades[0]);
            int gradeEnd = Integer.parseInt(grades[1]);

            funds = funds.stream()
                    .filter(fund -> fund.getInvestGrade() >= gradeStart && fund.getInvestGrade() <= gradeEnd)
                    .collect(Collectors.toList());
        }

        // 카테고리 필터링
        if (category != null && !category.isEmpty()) {
            funds = funds.stream()
                    .filter(fund -> {
                        String fundName = fund.getFundFnm().toLowerCase();
                        boolean isMixed = fundName.contains("혼합");

                        // 카테고리에 따른 필터링 로직
                        switch (category.toLowerCase()) {
                            case "mixed":
                                return isMixed; // 혼합 카테고리를 포함하는 경우
                            case "stock":
                                return fundName.contains("주식") && !isMixed; // 혼합 펀드 제외
                            case "bond":
                                return fundName.contains("채권") && !isMixed; // 혼합 펀드 제외
                            case "etc": // 기타 카테고리
                                return !isMixed && !fundName.contains("주식") && !fundName.contains("채권");
                            default:
                                return true; // 기타 (예: 전체보기)
                        }
                    })
                    .collect(Collectors.toList());
        }

        // 등급에 따른 정렬
        funds.sort((a, b) -> {
            int gradeA = a.getInvestGrade();
            int gradeB = b.getInvestGrade();

            // 0등급이 가장 우선
            if (gradeA == 0) return -1;
            if (gradeB == 0) return 1;

            // 1등급이 2등급보다 우선
            if (gradeA == 1 && gradeB == 2) return -1;
            if (gradeA == 2 && gradeB == 1) return 1;

            // 3등급이 4등급보다 우선
            if (gradeA == 3 && gradeB == 4) return -1;
            if (gradeA == 4 && gradeB == 3) return 1;

            // 5등급이 6등급보다 우선
            if (gradeA == 5 && gradeB == 6) return -1;
            if (gradeA == 6 && gradeB == 5) return 1;

            // 기본 정렬 (숫자가 작은 것이 우선)
            return Integer.compare(gradeA, gradeB);
        });

        return funds;
    }

}

