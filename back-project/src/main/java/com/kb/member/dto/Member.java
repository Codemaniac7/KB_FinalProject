package com.kb.member.dto;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Member implements UserDetails {
    private int mno;			// PK
    private String id; 			// id=username
    private String password;	// password
    private String name;        // 사용자이름
    private String email;       // 이메일
    private String status; 		// 활성화 여부, Y, N
    private String kakaoId;     // 카카오 아이디
    private Date createDate;    // 생성일
    private Date updateDate;    // 수정일
    private String role;
    private String investType;
    private String token; // JWT 토큰값, DB로는 저장하지 않음

    // 복수개의 권한을 관리
    // 문자열data("ROLE_USER", "ROLE_ADMIN")를 처리할 수 있는 GrantedAuthority의 하위클래스

    private List<SimpleGrantedAuthority> authorities; // authorities

    /**
     * Collection - List/Set
     *
     * Collection<? extends GrantedAuthority>
     * 	- <GrantedAuthority를 상속하는 ?> -> 자식타입(상한선)
     *  - <? super Integer> -> Integer 부모타입 (하한선)
     * Collection<GrantedAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String setUsername(String username) {
        return this.id = username;
    }
    @Override
    public String getUsername() {
        return id;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status.equalsIgnoreCase("Y");
    }

    public boolean checkRequiredValue(){
        try {
            return (id.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty());
        }catch (Exception e){
            return false;
        }
    }

}
