package com.kb.stock.mapper;

import com.kb.stock.dto.StockCandleDTO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface StockCandleMapper {

    @Select({
            "SELECT stock_code, stock_candle_day, stock_candle_open, stock_candle_close,",
            "stock_candle_high, stock_candle_low, stock_candle_volume",
            "FROM stocks_candle",
            "WHERE stock_code = #{stockCode}",
            "AND stock_candle_day BETWEEN #{startDate} AND #{endDate}",
            "ORDER BY stock_candle_day ASC"
    })
    List<StockCandleDTO> getStockData(@Param("stockCode") String stockCode,
                                      @Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);

    @Insert({
            "INSERT INTO stocks_candle (stock_code, stock_candle_day, stock_candle_open, stock_candle_close,",
            "stock_candle_high, stock_candle_low, stock_candle_volume)",
            "VALUES (#{stockCode}, #{stockCandleDay}, #{stockCandleOpen}, #{stockCandleClose},",
            "#{stockCandleHigh}, #{stockCandleLow}, #{stockCandleVolume})",
            "ON DUPLICATE KEY UPDATE",
            "stock_candle_open = VALUES(stock_candle_open),",
            "stock_candle_close = VALUES(stock_candle_close),",
            "stock_candle_high = VALUES(stock_candle_high),",
            "stock_candle_low = VALUES(stock_candle_low),",
            "stock_candle_volume = VALUES(stock_candle_volume)"
    })
    void upsertStockCandle(StockCandleDTO stockCandleDTO);
}
