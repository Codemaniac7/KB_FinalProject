package com.kb.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auth implements GrantedAuthority {
    private String id;
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
