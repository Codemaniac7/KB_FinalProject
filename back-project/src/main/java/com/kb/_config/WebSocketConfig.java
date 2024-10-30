package com.kb._config;

import com.kb.stock.handler.StockWebSocketHandler;
import com.kb.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private StockService stockService;

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // WebSocket 경로를 "/api/websocket/stocks"로 변경
        registry.addHandler(stockWebSocketHandler(), "/api/websocket/stocks")
                .setAllowedOrigins("*");
    }

    @Bean
    public StockWebSocketHandler stockWebSocketHandler() {
        return new StockWebSocketHandler(stockService);
    }
}
