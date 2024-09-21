package com.kb.board.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardDTO {
    private String type;
    private String title;
    private String content;

    public Board toBoard() {
        return Board.builder().type(type).title(title).content(content).build();
    }
}

