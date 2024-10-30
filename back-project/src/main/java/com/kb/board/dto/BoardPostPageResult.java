package com.kb.board.dto;

import com.kb.common.pagination.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardPostPageResult {
    private List<BoardPost> postList; // 게시글 목록
    private PageInfo pageInfo;        // 페이징 정보
    private int totalCount;            // 총 게시글 수
}
