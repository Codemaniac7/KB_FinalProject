package com.kb.saving.dto;

import com.kb.bank.dto.Bank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SavingCategory {
    private List<Bank> bankList;
    private List<Integer> saveTerm = Arrays.asList(1, 3, 6, 12, 24, 36);
    private List<String> interestType = Arrays.asList("단리", "복리");
}
