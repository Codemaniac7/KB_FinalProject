package com.multi.security.account.mapper;

import com.kb._config.RootConfig;
import com.kb._config.SecurityConfig;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RootConfig.class, SecurityConfig.class })
@Log4j
public class UserDetailsMapperTest {
//    @Autowired
//    private UserDetailsMapper mapper;
//
//    @Test
//    public void testGet() {
//        MemberVO member = mapper.get("admin");
//        log.info(member);
//
//        for(AuthVO auth : member.getAuthList()) {
//            log.info(auth);
//        }
//    }
}
