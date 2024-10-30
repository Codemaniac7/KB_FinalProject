package com.kb.funds.mapper;
import com.kb.funds.dto.FundsDTO;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
public interface FundsMapper {
    @Transactional
    void insertFund(FundsDTO fund);
    void updateFund(FundsDTO fund); // 펀드 업데이트 메서드
    // ID로 펀드 존재 여부 확인
//    boolean existsById(Long id);
    List<FundsDTO> searchFunds(String keyword);
    List<FundsDTO> findAllFunds();
}