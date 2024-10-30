package com.kb.board.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostParam {
    private int boardId; // 게시판 ID
    private int bno;     // 게시글 번호 추가
    private int page = 1; // 현재 페이지 (기본값 1)
    private int limit;    // 페이지당 게시글 수
    private int offset;   // 데이터 시작 위치
}
