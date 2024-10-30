package com.kb.saving.dto;


import com.kb.common.pagination.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SavingListResponseDTO {
    private List<SavingListDTO> savings;
    private SavingParam savingParam;
    private PageInfo pageInfo;
    private int totalCount;
}
