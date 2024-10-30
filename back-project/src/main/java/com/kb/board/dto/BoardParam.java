package com.kb.board.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

// BoardParam : 검색창 파라메터 받아오는 객체

// 설계 요령 
// 1. form의 name과 종류와 일치해서 파라메터 설계 필요
// 2. types의 경우 마이바티스 호환성을 위해 typeList 설계 추가 필요
// 3. 페이징 처리를 위해 page, limit, offset을 따로 설계 필요

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardParam {
	// html - form의 name과 일치하는 파라메터
	private String searchType;
	private String searchValue;
	private int amount;
	private List<String> types;
	// 페이징 인자, 요청 할 값
	private int page = 1;

	// mybatis에서 사용 할 limit, offset
	private int limit;
	private int offset;
}
