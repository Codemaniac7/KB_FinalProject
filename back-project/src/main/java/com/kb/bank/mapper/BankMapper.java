package com.kb.bank.mapper;

import com.kb.bank.dto.Bank;

import java.util.List;

public interface BankMapper {
    List<Bank> getBankListByType(int bankType);
}
