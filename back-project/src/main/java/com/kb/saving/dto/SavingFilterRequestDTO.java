package com.kb.saving.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class SavingFilterRequestDTO {
    private String searchValue;
    private List<String> bankNameList;
    private int saveTerm = 36;
    private int page = 1;
    private String interestRateType = "단리";

    public SavingParam toSavingParam(){
        return SavingParam.builder()
                .searchValue(searchValue)
                .bankNameList(bankNameList)
                .saveTerm(saveTerm)
                .page(page)
                .interestRateType(interestRateType)
                .build();
    }

}
