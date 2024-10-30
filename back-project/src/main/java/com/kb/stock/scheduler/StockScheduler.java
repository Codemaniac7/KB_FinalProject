package com.kb.stock.scheduler;

import com.kb.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class StockScheduler {

    private static final Logger logger = LoggerFactory.getLogger(StockScheduler.class);

    @Autowired
    private StockService stockService;

    @Scheduled(cron = "0 */1 * * * *") // 매 1분마다 실행
    public void updateAllStocksScheduled() {
        // 현재 시간을 확인하는 코드
        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() >= 16 || now.getDayOfWeek() == DayOfWeek.SATURDAY || now.getDayOfWeek() == DayOfWeek.SUNDAY) {
            logger.info("현재 시간 {}: 주식 스케줄러가 실행되지 않음 (장 마감 또는 주말).", now);
            return; // 16시 이후이거나 주말이면 실행하지 않음
        }

        logger.info("Starting scheduled update of all stocks at {}", now);
        try {
            stockService.updateAllStocks();
            logger.info("Completed scheduled update of all stocks at {}", now);
        } catch (Exception e) {
            logger.error("Error during scheduled update of stocks", e);
        }
    }

}
