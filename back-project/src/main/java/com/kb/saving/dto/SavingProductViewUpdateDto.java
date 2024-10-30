package com.kb.saving.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingProductViewUpdateDto {
    private int savingId;      // 상품 ID
    private String wmtiType;   // MBTI 유형
    private int viewCount;     // 조회수

}
