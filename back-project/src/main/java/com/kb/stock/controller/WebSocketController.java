package com.kb.stock.controller;

import com.kb.stock.dto.StockDTO;
import com.kb.stock.service.WebSocketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/websocket")
@CrossOrigin(origins = "http://localhost:8081")
@Api(tags = {"WebSocket API"}, description = "WebSocket 관련 API")
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    private final WebSocketService webSocketService;

    @Autowired
    public WebSocketController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    private final List<String> STOCK_CODES = Arrays.asList("035720", "068270", "035420", "000660", "373220", "005380", "005930", "055550", "000270", "105560");

    @ApiOperation(value = "WebSocket 연결을 시작합니다.", notes = "실시간 주식 데이터 수신을 위한 WebSocket 연결을 시작합니다." , produces = "application/json; charset=UTF-8")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "WebSocket 연결 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping(value = "/connect",  produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> connectWebSocket() {
        logger.info("WebSocket 연결 시작");
        try {
            webSocketService.connectWebSocket();
            return ResponseEntity.ok("WebSocket 연결이 시작되었습니다.");
        } catch (Exception e) {
            logger.error("WebSocket 연결 중 오류 발생", e);
            return ResponseEntity.status(500).body("WebSocket 연결 중 오류가 발생했습니다.");
        }
    }
    @ApiOperation(value = "현재 주식 가격을 조회합니다.", notes = "WebSocket을 통해 수신된 10개 종목의 최신 주식 가격 정보를 반환합니다." , produces = "application/json; charset=UTF-8")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "데이터 없음")
    })
    @GetMapping("/prices")
    public ResponseEntity<Map<String, StockDTO>> getStockPrices() {
        logger.info("현재 주식 데이터 조회 시작");
        Map<String, StockDTO> allStockData = webSocketService.getLastStockData();
        logger.info("전체 주식 데이터: {}", allStockData);
        Map<String, StockDTO> filteredStockData = allStockData.entrySet().stream()
                .filter(entry -> STOCK_CODES.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (filteredStockData.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(filteredStockData);
    }

    @ApiOperation(value = "WebSocket 승인 키를 가져옵니다.", notes = "Access Token을 이용하여 WebSocket 승인 키를 발급받는 메서드입니다.", produces = "application/json; charset=UTF-8")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "승인 키 발급 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping("/approval-key")
    public ResponseEntity<String> getWebSocketApprovalKey() {
        logger.info("getWebSocketApprovalKey 메서드 호출됨");
        try {
            String approvalKey = webSocketService.getWebSocketApprovalKey();
            if (approvalKey != null) {
                logger.info("WebSocket Approval Key 발급 성공: {}", approvalKey);
                return ResponseEntity.ok(approvalKey);
            } else {
                logger.error("WebSocket Approval Key 발급 실패");
                return ResponseEntity.status(500).body("Failed to get WebSocket approval key");
            }
        } catch (Exception e) {
            logger.error("WebSocket Approval Key 발급 중 예외 발생", e);
            return ResponseEntity.status(500).body("Error occurred while getting WebSocket approval key");
        }
    }

    @ApiOperation(value = "구독 중인 주식 코드 목록을 조회합니다.", notes = "현재 실시간 데이터를 수신 중인 10개 주식 코드 목록을 반환합니다." , produces = "application/json; charset=UTF-8")
    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeStocks(@RequestBody List<String> stockCodes) {
        for (String stockCode : stockCodes) {
            webSocketService.addSubscription(stockCode);
        }
        return ResponseEntity.ok("Subscribed to stocks successfully");
    }
    @ApiOperation(value = "테스트 주식 데이터를 반환합니다.", notes = "테스트 주식 데이터를 전송합니다.", produces = "application/json; charset=UTF-8")
    @GetMapping("/test-prices")
    public ResponseEntity<String> getTestStockPrices() {
        webSocketService.sendTestStockData(); // 테스트 데이터를 전송
        return ResponseEntity.ok()
                .header("Content-Type", "text/plain; charset=UTF-8")  // UTF-8로 인코딩 설정
                .body("테스트 데이터가 전송되었습니다.");  // 한글 메세지 전송
    }


}