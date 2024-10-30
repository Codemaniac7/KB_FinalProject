package com.kb.financialTerms.mapper;

import com.kb.financialTerms.dto.TermsDTO;

import java.util.List;

public interface TermsMapper {
    void insertTermsBatch(List<TermsDTO> termsList);
    List<TermsDTO> getTermsBatch();
}
