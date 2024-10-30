package com.kb.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardReplyDTO {
	private long postId;
	private String writer;
	private String content;

	public BoardReply toReply(){
		return BoardReply.builder().postId(postId).memberId(writer).content(content).build();
	}


}
