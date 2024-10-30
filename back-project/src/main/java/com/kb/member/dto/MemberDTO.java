package com.kb.member.dto;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {
    private String id; 			// id=username
    private String password;	// password
    private String name;        // 사용자이름
    private String email;       // 이메일
    private String kakaoId;       // 카카오 아이디
    private String investType;

    MultipartFile avatar;

    public Member toMember() {
        return Member.builder().id(id).password(password).name(name).email(email).kakaoId(kakaoId).investType(investType).build();
    }
}
