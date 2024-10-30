package com.kb.security.filter;


import com.kb.kakao.KaKaoLoginService;
import com.kb.member.dto.KakaoLoginDTO;
import com.kb.member.dto.LoginDTO;
import com.kb.member.dto.Member;
import com.kb.member.service.MemberService;
import com.kb.security.handler.LoginFailureHandler;
import com.kb.security.handler.LoginSuccessHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Setter(onMethod = @__({@Autowired}))
    private MemberService service;

    @Setter(onMethod = @__({@Autowired}))
    private KaKaoLoginService kaKaoLoginService;

    @Setter(onMethod = @__({@Autowired}))
    private UserDetailsService userDetailsService;


    // 스프링 생성자 주입을 통해 전달
    public JwtUsernamePasswordAuthenticationFilter(
            AuthenticationManager authenticationManager,
            LoginSuccessHandler loginSuccessHandler,
            LoginFailureHandler loginFailureHandler ) {
        super(authenticationManager);

        setFilterProcessesUrl("/api/auth/login");		          // POST 로그인 요청 url
        setAuthenticationSuccessHandler(loginSuccessHandler);	// 로그인 성공 핸들러 등록
        setAuthenticationFailureHandler(loginFailureHandler);  // 로그인 실패 핸들러 등록
    }

    // 로그인 요청 URL인 경우 로그인 작업 처리
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        LoginDTO login = LoginDTO.of(request);

        if (login.getCode() != null) {
            log.info(login.getRedirectUrl());
            String token = kaKaoLoginService.getToken(login.getCode(), login.getRedirectUrl());
            Map<String, Object> map = kaKaoLoginService.getUserInfo(token);
            String kakaoId = (String) map.get("id");
            Member member = service.getMemberByKakaoId(kakaoId);

            UserDetails princiapl = userDetailsService.loadUserByUsername(member.getId());
            System.out.println("@@@ : " + princiapl);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(princiapl, null, princiapl.getAuthorities());
            // AuthenticationManager에게 인증 요청
            return authenticationToken;
        }

        // 인증 토큰(UsernamePasswordAuthenticationToken) 구성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(login.getId(), login.getPassword());

        // AuthenticationManager에게 인증 요청
        return getAuthenticationManager().authenticate(authenticationToken);

    }

}
