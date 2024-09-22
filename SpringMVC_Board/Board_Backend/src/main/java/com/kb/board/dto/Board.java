package com.kb.board.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Board {
    private long bno;
    private long mno;
    private String type;
    private String title;
    private String content;
    private String memberId;
    private String memberName;
    private int readCount;
    private String status;
    private Date createDate;
    private Date modifyDate;

    private ArrayList<BoardReply> replyList;
    private ArrayList<BoardAttachFile> boardAttachFileList;
}

