package com.kb.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Bank {
    private int bankId;
    private String bankName;
    private String bankLogoUrl;
    private String bankUrl;
    private Integer bankType;
}
