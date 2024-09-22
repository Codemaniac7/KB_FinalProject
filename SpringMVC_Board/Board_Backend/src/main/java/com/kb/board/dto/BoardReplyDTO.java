package com.kb.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardReplyDTO {
	private long bno;
	private String writer;
	private String content;

	public BoardReply toReply(){
		return BoardReply.builder().bno(bno).memberId(writer).content(content).build();
	}
}
