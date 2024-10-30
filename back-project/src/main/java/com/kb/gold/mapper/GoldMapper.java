package com.kb.gold.mapper;

import com.kb.gold.dto.GoldDataDto;
import com.kb.gold.dto.GoldDto;
import com.kb.gold.dto.GoldParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface GoldMapper {
    // 배치로 데이터를 삽입하는 메서드
    void insertGoldDataBatch(List<GoldDataDto> goldDataList);
    int getTotalCount();
    List<GoldDto> getGoldInfoList(GoldParam goldParam);
}
