package com.kb.stock.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.stock.dto.StockDTO;
import com.kb.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StockWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StockService stockService;
    private final Map<WebSocketSession, Set<String>> sessionSubscriptions = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(StockWebSocketHandler.class);

    // 10개의 주식 코드 (구독 시 사용할 기본 주식 목록)
    private final List<String> stockCodes = Arrays.asList("035720", "068270", "035420", "000660", "373220", "005380", "005930", "055550", "000270", "105560");

    @Autowired
    public StockWebSocketHandler(StockService stockService) {
        this.stockService = stockService;
        this.stockService.setWebSocketHandler(this);

        // 주기적으로 테스트 데이터를 보내기 위한 타이머 설정 (5초마다 데이터 전송)
        Timer timer = new Timer(true); // 데몬 쓰레드로 실행
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendTestStockData();  // 주기적으로 테스트 데이터 전송
            }
        }, 0, 5000);  // 즉시 실행 시작, 5초마다 반복
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionSubscriptions.put(session, new HashSet<>());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.debug("받은 메시지: {}", payload);
        Map<String, Object> stockData = objectMapper.readValue(payload, Map.class);

        String action = (String) stockData.get("action");
        String stockCode = (String) stockData.get("stockCode");

        if ("subscribe".equals(action)) {
            subscribeStock(session, stockCode);
        } else if ("unsubscribe".equals(action)) {
            unsubscribeStock(session, stockCode);
        }
    }

    private void subscribeStock(WebSocketSession session, String stockCode) {
        sessionSubscriptions.get(session).add(stockCode);
        stockService.addSubscription(stockCode);
        sendInitialStockData(session, stockCode);
    }

    private void unsubscribeStock(WebSocketSession session, String stockCode) {
        sessionSubscriptions.get(session).remove(stockCode);
        stockService.removeSubscription(stockCode);
    }

    private void sendInitialStockData(WebSocketSession session, String stockCode) {
        try {
            StockDTO stockData = stockService.getStockData(stockCode);
            if (stockData != null) {
                String jsonStockData = objectMapper.writeValueAsString(stockData);
                session.sendMessage(new TextMessage(jsonStockData));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 테스트 데이터를 주기적으로 전송하는 메서드
    // 테스트 데이터를 주기적으로 전송하는 메서드
    public void sendTestStockData() {
        for (String stockCode : stockCodes) {
            double testCurrentPrice = 95000 + Math.random() * 1000;  // 임의의 주가 생성
            double testPriceChange = testCurrentPrice - 94000;
            double testChangeRate = (testPriceChange / 94000) * 100;

            StockDTO stockData = new StockDTO();
            stockData.setStockCode(stockCode);
            stockData.setCurrentPrice(new BigDecimal(testCurrentPrice));
            stockData.setPriceChange(new BigDecimal(testPriceChange));
            stockData.setPriceChangePct(new BigDecimal(testChangeRate));

            logger.info("Sending test stock data: {}", stockData);

            // 구독된 모든 세션에 테스트 데이터 전송
            for (Map.Entry<WebSocketSession, Set<String>> entry : sessionSubscriptions.entrySet()) {
                WebSocketSession session = entry.getKey();
                Set<String> subscriptions = entry.getValue();

                if (session.isOpen() && subscriptions.contains(stockCode)) {
                    try {
                        String jsonStockData = objectMapper.writeValueAsString(stockData);
                        session.sendMessage(new TextMessage(jsonStockData));
                        logger.info("주식 데이터 전송 완료: {}", jsonStockData);
                    } catch (IOException e) {
                        logger.error("주식 데이터 전송 중 오류 발생", e);
                    }
                } else {
                    logger.warn("WebSocket 세션이 열려 있지 않거나 주식 코드가 구독되지 않음: {}", stockCode);
                }
            }
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Set<String> subscriptions = sessionSubscriptions.remove(session);
        if (subscriptions != null) {
            for (String stockCode : subscriptions) {
                stockService.removeSubscription(stockCode);
            }
        }
    }
    public void sendStockData(String stockCode, StockDTO stockData) {
        String jsonStockData;
        try {
            jsonStockData = objectMapper.writeValueAsString(stockData);
            logger.info("Sending stock data: {}", jsonStockData);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (Map.Entry<WebSocketSession, Set<String>> entry : sessionSubscriptions.entrySet()) {
            WebSocketSession session = entry.getKey();
            Set<String> subscriptions = entry.getValue();
            if (session.isOpen() && subscriptions.contains(stockCode)) {
                try {
                    session.sendMessage(new TextMessage(jsonStockData));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
