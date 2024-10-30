package com.kb.saving.service;

import com.kb.bank.mapper.BankMapper;
import com.kb.common.pagination.PageInfo;
import com.kb.saving.dto.*;
import com.kb.saving.mapper.SavingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Log4j
@Service
@RequiredArgsConstructor
public class SavingService {
    private final SavingMapper mapper;
    private final BankMapper bankMapper;
    private final static int LIST_LIMIT = 9;
    private final static int PAGE_LIMIT = 5;

    public SavingListResponseDTO getProductList(SavingParam savingParam) {
        int totalSize = mapper.getTotalCount(savingParam);
        PageInfo pageInfo = new PageInfo(savingParam.getPage(), totalSize, LIST_LIMIT, PAGE_LIMIT);
        savingParam.setLimit(pageInfo.getListLimit());
        savingParam.setOffset(pageInfo.getStartList() - 1);
        List<SavingListDTO> savingList = mapper.getProductList(savingParam);
        if (savingList == null || savingList.isEmpty()) {
            savingList = new ArrayList<>();
        }
        return new SavingListResponseDTO(savingList, savingParam, pageInfo, totalSize);
    }

    public Saving getProductDetail(int finCategoryId, int savingId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("finCategoryId", finCategoryId);
        map.put("savingId", savingId);
        Saving saving = mapper.getProductDetail(map);
        saving.setJoinWays(new ArrayList<>(Arrays.asList(saving.getJoinWay().split(","))));
        return saving;
    }

    public List<SavingListDTO> getTopProductList(SavingTopRequestDTO savingTopRequestDTO) {
        return mapper.getTopProductListByInvestType(savingTopRequestDTO);
    }
    public List<SavingListDTO> getTopSavingsProductList(int finCategoryId) {
        return mapper.getTopProductListByViewCount(finCategoryId);
    }

    public SavingCategory getCategoryList(){
        SavingCategory savingCategory = new SavingCategory();
        savingCategory.setBankList(bankMapper.getBankListByType(1));
        return savingCategory;
    }

    @Transactional
    public void logProductViewAndUpdateViewCount(SavingProductViewLogDto dto) {
        // 1. 1시간 이내에 동일한 유저가 동일한 상품을 조회했는지 확인
        int logCount = mapper.checkSavingProductViewByWmtiAndUserid(dto);

        if (logCount == 0) {
            // 2. 조회 기록이 없으면 신규 조회 기록 추가 또는 업데이트
            mapper.updateProductViewLog(dto);

            // 3. 조회수가 없으면 새로운 카운트 추가, 있으면 1 증가
            SavingProductViewUpdateDto updateDto = new SavingProductViewUpdateDto(dto.getSavingId(), dto.getWmtiType(), 1);
            mapper.updateSavingProductViewByWmti(updateDto);
        }
    }
}
