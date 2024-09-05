package com.kb.board.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardReply {
	private long rno;
	private long bno;
	private long mno;
	private String memberId;
	private String memberName;
	private String content;	
	private String status;
	private Date createDate;
	private Date modifyDate;
}
