package com.kb.saving.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class SavingInterestRate {
    private int interestRateId;
    private int savingId;
    private String interestRateType;
    private int savingTerm;
    private double interestRate;
    private double interestMaxRate;

}
