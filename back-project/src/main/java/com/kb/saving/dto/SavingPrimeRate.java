package com.kb.saving.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SavingPrimeRate {
    private int primeRateId;
    private double primeRatePercent;
    private String primeRateDetail;
    private int savingId;


}
