package com.kb.saving.dto;

import com.kb.bank.dto.Bank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SavingListDTO {
    private long savingId;
    private String savingName;
    private int joinDeny;
    private String joinWay;
    private Bank bank;
    private SavingInterestRate interestRateList;
}
