package com.kb.member.mapper;

import com.kb.member.dto.Auth;
import com.kb.member.dto.ChangePasswordDTO;
import com.kb.member.dto.Member;

import java.util.List;

public interface MemberMapper {
    List<Member> selectMemberAll();
    Member selectById(String id);
    Member selectByMno(int mno);
    Member selectBykakaoId(String kakaoId);
    int insertMember(Member member);
    int updateMember(Member member);
    int updatePassword(ChangePasswordDTO changePasswordDTO);
    int deleteMember(Integer mno);
    int insertAuth(Auth auth);
    int deleteAuth(Auth auth);
    int checkDuplicateById(String id);
    int checkDuplicateByKakaoId(String id);
    Integer getMemberAutoIncrement();
    int updateInvestType(Member member);
    Integer checkPassword(String id, ChangePasswordDTO changePassword);
}
