package com.kb.board.converter;

import com.kb.board.dto.BoardStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToBoardStatusConverter implements Converter<String, BoardStatus> {
    @Override
    public BoardStatus convert(String source) {
        return BoardStatus.fromValue(source);
    }
}
