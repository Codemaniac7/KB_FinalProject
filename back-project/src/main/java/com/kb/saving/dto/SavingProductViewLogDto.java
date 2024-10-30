package com.kb.saving.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingProductViewLogDto {
    private int savingId;      // 상품 ID
    private int userId;        // 사용자 ID
    private String wmtiType;   // MBTI 유형
    private Timestamp viewedAt;  // 조회 시간

}
