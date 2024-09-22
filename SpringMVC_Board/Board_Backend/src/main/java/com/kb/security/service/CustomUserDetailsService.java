package com.kb.security.service;

import com.kb.member.dto.Member;
import com.kb.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Log4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    final private MemberMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = mapper.selectById(username);
        if(member == null) {
            throw new UsernameNotFoundException(username + "은 없는 id 입니다.");
        }
        return member;
    }

}
