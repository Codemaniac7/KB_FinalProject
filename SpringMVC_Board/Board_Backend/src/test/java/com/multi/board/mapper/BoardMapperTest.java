package com.multi.board.mapper;

import com.kb.board.mapper.BoardMapper;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.kb._config.RootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class,})
@Log4j
class BoardMapperTest {
    @Autowired
    BoardMapper mapper;

    @Autowired
    private BoardMapper boardMapper;


    @Test
    void testPaging() {
        mapper.selectAttachFileByBno(1);
    }
}