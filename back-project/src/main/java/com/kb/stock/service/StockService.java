package com.kb.stock.service;

import com.kb.stock.dto.StockDTO;
import com.kb.stock.mapper.StockMapper;
import com.kb.stock.handler.StockWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private TokenService tokenService;

    @Value("${kis.api.appkey}")
    private String appKey;

    @Value("${kis.api.secretkey}")
    private String appSecret;

    @Value("${kis.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1500;
    private static final int REQUESTS_PER_SECOND = 10;
    private static final long REQUEST_INTERVAL_MS = 1500 / REQUESTS_PER_SECOND;

    private StockWebSocketHandler webSocketHandler;
    private final Set<String> subscribedStocks = ConcurrentHashMap.newKeySet();

    @Autowired
    private WebSocketService webSocketService;

    @PostConstruct
    public void init() {
        int result = stockMapper.checkDatabaseConnection();
        logger.info("Database connection check result: {}", result);
        startRealTimeUpdates();
    }

    @Autowired
    @Lazy
    public void setWebSocketHandler(StockWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    public void addSubscription(String stockCode) {
        subscribedStocks.add(stockCode);
    }

    public void removeSubscription(String stockCode) {
        subscribedStocks.remove(stockCode);
    }

    public void updateAllStocks() {
        logger.info("Starting updateAllStocks method");
        List<String> stockCodes;
        try {
            stockCodes = stockMapper.selectAllStockCodes();
        } catch (Exception e) {
            logger.error("Error fetching stock codes from database: {}", e.getMessage(), e);
            return;
        }

        logger.info("Retrieved {} stock codes from database", stockCodes.size());

        for (int i = 0; i < stockCodes.size(); i += 10) {
            List<String> batch = stockCodes.subList(i, Math.min(i + 10, stockCodes.size()));
            processStockBatch(batch, i / 10 + 1);

            try {
                Thread.sleep(REQUEST_INTERVAL_MS * 20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Thread interrupted while waiting for next batch", e);
            }
        }
        logger.info("Finished updateAllStocks method");
    }

    // 주식 배치 처리
    private void processStockBatch(List<String> stockCodes, int batchNumber) {
        long batchStartTime = System.currentTimeMillis(); // 배치 시작 시간

        for (String stockCode : stockCodes) {
            long stockStartTime = System.currentTimeMillis(); // 각 주식 코드 시작 시간

            try {
                Map<String, Object> stockData = getStockPrice(stockCode);
                if (stockData != null) {
                    StockDTO stockDTO = mapToStockDTO(stockData);
                    if (stockDTO != null) {
                        upsertStockData(stockDTO);
                        webSocketService.updateLastPrices(stockDTO.getStockCode(), stockDTO.getCurrentPrice().doubleValue());
                        logger.info("Batch {}, Stock {} successfully updated in {} ms", batchNumber, stockCode, System.currentTimeMillis() - stockStartTime);
                    } else {
                        logger.warn("Batch {}, Stock {} data incomplete in {} ms", batchNumber, stockCode, System.currentTimeMillis() - stockStartTime);
                    }
                } else {
                    logger.warn("Batch {}, No stock data retrieved for stock {} in {} ms", batchNumber, stockCode, System.currentTimeMillis() - stockStartTime);
                }

            } catch (Exception e) {
                logger.error("Batch {}, Error processing stock {}: {}", batchNumber, stockCode, e.getMessage());
            }
        }

        long batchEndTime = System.currentTimeMillis(); // 배치 종료 시간
        logger.info("Batch {} processed in {} ms", batchNumber, batchEndTime - batchStartTime);
    }

    public Map<String, Object> getStockPrice(String stockCode) {
        logger.info("Attempting to get stock price for code: {}", stockCode);

        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            try {
                String accessToken = tokenService.getAccessToken();
                logger.info("Retrieved access token: {}", accessToken);

                String url = baseUrl + "/uapi/domestic-stock/v1/quotations/inquire-price?FID_COND_MRKT_DIV_CODE=J&FID_INPUT_ISCD=" + stockCode;
                logger.info("API request URL: {}", url);

                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "application/json; charset=utf-8");
                headers.set("authorization", "Bearer " + accessToken);
                headers.set("appkey", appKey);
                headers.set("appsecret", appSecret);
                headers.set("tr_id", "FHKST01010100");

                HttpEntity<String> request = new HttpEntity<>(headers);

                ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
                logger.info("API response for stock code {}: Status={}, Body={}", stockCode, response.getStatusCode(), response.getBody());

                if (response.getStatusCode() == HttpStatus.OK) {
                    logger.info("API response body: {}", response.getBody());
                    return (Map<String, Object>) response.getBody().get("output");

                } else {
                    logger.error("Failed to retrieve stock data for {}. Status code: {}", stockCode, response.getStatusCode());
                }
            } catch (HttpClientErrorException e) {
                logger.error("HTTP client error occurred: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
                if (attempt < MAX_RETRIES - 1) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (Exception e) {
                logger.error("Error getting stock price for code {}: {}", stockCode, e.getMessage());
                if (attempt < MAX_RETRIES - 1) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        return null;
    }

    private StockDTO mapToStockDTO(Map<String, Object> stockData) {
        logger.info("Mapping stock data to StockDTO: {}", stockData);
        if (stockData == null) {
            return null;
        }

        try {
            String stockCode = (String) stockData.get("stck_shrn_iscd");
            String stockName = stockMapper.selectStockNameByCode(stockCode);

            StockDTO stockDTO = new StockDTO();
            stockDTO.setStockCode(stockCode);
            stockDTO.setStockName(stockName != null ? stockName : "");
            stockDTO.setCurrentPrice(stockData.get("stck_prpr") != null ? new BigDecimal(stockData.get("stck_prpr").toString()) : BigDecimal.ZERO);
            stockDTO.setPriceChange(stockData.get("prdy_vrss") != null ? new BigDecimal(stockData.get("prdy_vrss").toString()) : BigDecimal.ZERO);
            stockDTO.setPriceChangePct(stockData.get("prdy_ctrt") != null ? new BigDecimal(stockData.get("prdy_ctrt").toString()) : BigDecimal.ZERO);
            stockDTO.setHighPrice(stockData.get("stck_hgpr") != null ? new BigDecimal(stockData.get("stck_hgpr").toString()) : BigDecimal.ZERO);
            stockDTO.setLowPrice(stockData.get("stck_lwpr") != null ? new BigDecimal(stockData.get("stck_lwpr").toString()) : BigDecimal.ZERO);
            stockDTO.setOpeningPrice(stockData.get("stck_oprc") != null ? new BigDecimal(stockData.get("stck_oprc").toString()) : BigDecimal.ZERO);
            stockDTO.setIndustry(stockData.get("bstp_kor_isnm") != null ? stockData.get("bstp_kor_isnm").toString() : "");
            stockDTO.setVolume(stockData.get("acml_vol") != null ? Long.parseLong(stockData.get("acml_vol").toString()) : 0L);
            stockDTO.setHtsAvls(stockData.get("hts_avls") != null ? new BigDecimal(stockData.get("hts_avls").toString()) : BigDecimal.ZERO); // HTS 시가총액 추가
            stockDTO.setW52Hgpr(stockData.get("w52_hgpr") != null ? new BigDecimal(stockData.get("w52_hgpr").toString()) : BigDecimal.ZERO); // 52주일 최고가 추가
            stockDTO.setW52Lwpr(stockData.get("w52_lwpr") != null ? new BigDecimal(stockData.get("w52_lwpr").toString()) : BigDecimal.ZERO); // 52주일 최저가 추가
            stockDTO.setAcmlTrPbmn(stockData.get("acml_tr_pbmn") != null ? new BigDecimal(stockData.get("acml_tr_pbmn").toString()) : BigDecimal.ZERO); // acml_tr_pbmn 시가총액 추가

            logger.info("Stock data received: {}", stockData);
            logger.info("Mapped StockDTO: {}", stockDTO);
            return stockDTO;
        } catch (Exception e) {
            logger.error("Error mapping stock data to StockDTO: {}", e.getMessage(), e);
            return null;
        }
    }

    public void upsertStockData(StockDTO stockData) {
        logger.info("Attempting to upsert stock data: {}", stockData);
        try {
            stockMapper.upsertStock(stockData);
            logger.info("Successfully upserted stock data for code: {}", stockData.getStockCode());
        } catch (Exception e) {
            logger.error("Error upserting stock data for code {}: {}", stockData.getStockCode(), e.getMessage(), e);
        }
    }

    public List<String> getAllStockCodes() {
        return stockMapper.selectAllStockCodes();
    }

    public List<StockDTO> getAllStocks() {
        return stockMapper.selectAllStocks();
    }

    public StockDTO getStockData(String stockCode) {
        Map<String, Object> stockData = getStockPrice(stockCode);
        return mapToStockDTO(stockData);
    }

    public void startRealTimeUpdates() {
        new Thread(() -> {
            while (true) {
                for (String stockCode : subscribedStocks) {
                    try {
                        StockDTO stockData = getStockData(stockCode);
                        if (stockData != null && webSocketHandler != null) {
                            webSocketHandler.sendStockData(stockCode, stockData);
                        }
                    } catch (Exception e) {
                        logger.error("Error updating stock data for {}: {}", stockCode, e.getMessage());
                    }
                }
                try {
                    Thread.sleep(5000); // 5초마다 업데이트
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }

    // 안정성 중심 주식 조회
    public List<StockDTO> getStableStocks() {
        logger.info("Fetching stable stocks");
        return stockMapper.selectStableStocks();
    }

    // 성장성 중심 주식 조회
    public List<StockDTO> getGrowthStocks() {
        logger.info("Fetching growth stocks");
        return stockMapper.selectGrowthStocks();
    }

    // 배당성 중심 주식 조회
    public List<StockDTO> getDividendStocks() {
        logger.info("Fetching dividend stocks");
        return stockMapper.selectDividendStocks();
    }

    // 변동성 중심 주식 조회
    public List<StockDTO> getVolatileStocks() {
        logger.info("Fetching volatile stocks");
        return stockMapper.selectVolatileStocks();
    }

    // 공격성 중심 주식 조회
    public List<StockDTO> getAggressiveStocks() {
        logger.info("Fetching aggressive stocks");
        return stockMapper.selectAggressiveStocks();
    }

    public List<Map<String, Object>> getCategoryRankings() {
        List<StockDTO> allStocks = getAllStocks();
        Map<String, List<StockDTO>> categoryStocks = new HashMap<>();

        // 카테고리별로 주식 그룹화
        for (StockDTO stock : allStocks) {
            categoryStocks.computeIfAbsent(stock.getIndustry(), k -> new ArrayList<>()).add(stock);
        }

        List<Map<String, Object>> categoryRankings = new ArrayList<>();

        // 각 카테고리의 평균 등락률 계산
        for (Map.Entry<String, List<StockDTO>> entry : categoryStocks.entrySet()) {
            String category = entry.getKey();
            List<StockDTO> stocks = entry.getValue();

            double avgChangeRate = stocks.stream()
                    .mapToDouble(s -> s.getPriceChangePct().doubleValue())
                    .average()
                    .orElse(0.0);

            long risingStocksCount = stocks.stream()
                    .filter(s -> s.getPriceChangePct().compareTo(BigDecimal.ZERO) > 0)
                    .count();

            StockDTO topStock = stocks.stream()
                    .max(Comparator.comparing(StockDTO::getPriceChangePct))
                    .orElse(null);

            Map<String, Object> categoryInfo = new HashMap<>();
            categoryInfo.put("name", category);
            categoryInfo.put("avgChange", avgChangeRate);
            categoryInfo.put("risingStocksCount", risingStocksCount);
            categoryInfo.put("totalStocksCount", stocks.size());
            if (topStock != null) {
                categoryInfo.put("topStock", Map.of("name", topStock.getStockName(), "changeRate", topStock.getPriceChangePct()));
            }

            categoryRankings.add(categoryInfo);
        }

        // 평균 등락률로 정렬
        categoryRankings.sort((c1, c2) -> Double.compare((Double) c2.get("avgChange"), (Double) c1.get("avgChange")));

        return categoryRankings;
    }

}
