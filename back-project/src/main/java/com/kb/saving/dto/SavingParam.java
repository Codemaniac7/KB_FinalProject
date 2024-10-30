package com.kb.saving.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SavingParam {
    private String searchValue;
    private String interestRateType;
    private List<String> bankNameList;
    private Integer saveTerm;
    private Integer finCategoryId;

    private int page;
    private int limit;
    private int offset;


}
