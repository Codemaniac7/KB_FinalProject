package com.kb.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardDTO {
    private int bno;        // 게시판 번호
    private String type;         // 타입
    private String title;        // 제목
    private String content;      // 내용
    private String status;       // 상태 ('y' 또는 'n')

//    public BoardStatus getStatusEnum() {
//        return BoardStatus.fromValue(status); // BoardStatus로 변환
//    }


    public BoardPost toBoardPost() {
        return BoardPost.builder()
                .bno(bno) // bno 추가
                .type(type)
                .title(title)
                .content(content)
                .status(status)
                .build();
    }
}
