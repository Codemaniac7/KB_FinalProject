package com.kb.gold.dto;


import com.kb.common.pagination.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoldListResponseDTO {
    private List<GoldDto> golds;
    private GoldParam goldParam;
    private PageInfo pageInfo;
    private int totalCount;
}
