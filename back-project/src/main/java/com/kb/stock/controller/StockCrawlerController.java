package com.kb.stock.controller;

import com.kb.stock.service.StockCrawlerService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin(origins = "http://localhost:8081")
@Api(tags = {"Kospi API"}, description = "코스피 관련 API")
public class StockCrawlerController {
    private static final Logger logger = LoggerFactory.getLogger(StockCrawlerController.class);

    private final StockCrawlerService stockCrawlerService;
    private final ObjectMapper objectMapper;

    public StockCrawlerController(StockCrawlerService stockCrawlerService, ObjectMapper objectMapper) {
        this.stockCrawlerService = stockCrawlerService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/api/index/kospi", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getKospiIndex(HttpServletResponse response) {
        response.setContentType("application/json; charset=UTF-8");
        try {
            return stockCrawlerService.getKOSPI();
        } catch (IOException e) {
            return createErrorResponse("KOSPI 지수 가져오기 실패", e);
        }
    }

    @GetMapping(value = "/api/index/kosdaq", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getKosdaqIndex(HttpServletResponse response) {
        response.setContentType("application/json; charset=UTF-8");
        logger.info("Received request for KOSDAQ index");
        try {
            String result = stockCrawlerService.getKOSDAQ();
            logger.info("Successfully fetched KOSDAQ index");
            return result;
        } catch (IOException e) {
            logger.error("Failed to fetch KOSDAQ index", e);
            return createErrorResponse("KOSDAQ 지수 가져오기 실패", e);
        }
    }

    @GetMapping(value = "/api/index/kospi200", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getKospi200Index(HttpServletResponse response) {
        response.setContentType("application/json; charset=UTF-8");
        logger.info("Received request for KOSPI200 index");
        try {
            String result = stockCrawlerService.getKOSPI200();
            logger.info("Successfully fetched KOSPI200 index");
            return result;
        } catch (IOException e) {
            logger.error("Failed to fetch KOSPI200 index", e);
            return createErrorResponse("KOSPI200 지수 가져오기 실패", e);
        }
    }

    private String createErrorResponse(String message, Exception e) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("error", message + ": " + e.getMessage());
        try {
            return objectMapper.writeValueAsString(errorNode);
        } catch (Exception ex) {
            logger.error("Error creating error response", ex);
            return "{\"error\":\"Error creating error response\"}";
        }
    }
}