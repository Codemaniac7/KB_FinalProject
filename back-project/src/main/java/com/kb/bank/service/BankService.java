package com.kb.bank.service;

import com.kb.bank.dto.Bank;
import com.kb.bank.mapper.BankMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@Service
@RequiredArgsConstructor
public class BankService {
    private final BankMapper mapper;

    public List<Bank> getBankListByType(){
        return mapper.getBankListByType(1);
    }
}
