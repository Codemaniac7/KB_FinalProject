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
public class BoardPageResult {
    private List<BoardPost> boardPostList;
    private BoardParam boardParam;
    private PageInfo pageInfo;
    private List<BoardCategory> boardCategory;
    private int totalCount;
}
