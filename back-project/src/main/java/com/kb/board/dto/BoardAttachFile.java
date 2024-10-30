package com.kb.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardAttachFile {
	private long fno;
	private long postId;
	private String originalFilename;
	private String renamedFilename;
	private String contentType;
	private long size;
	private Date createDate;
}
