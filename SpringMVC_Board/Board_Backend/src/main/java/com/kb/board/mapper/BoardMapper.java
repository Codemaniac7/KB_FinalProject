package com.kb.board.mapper;

import com.kb.board.dto.*;

import java.util.List;

public interface BoardMapper {
    List<BoardCategory> selectBoardCategory();
    List<Board> selectBoardList(BoardParam param);
    int selectBoardCount(BoardParam param);
    Board selectBoardByBno(long bno);
    int insertBoard(Board board);
    int updateBoard(Board board);
    int updateReadCount(Board board);
    int deleteBoard(long bno);
    BoardAttachFile selectAttachFileByFno(long fno);
    List<BoardAttachFile> selectAttachFileByBno(long bno);
    int insertAttachFile(BoardAttachFile attachFile);
    int deleteAttachFile(long fno);
    int insertReply(BoardReply reply);
    BoardReply selectReplyByRno(long rno);
    List<BoardReply> selectReplyByBno(long bno);
    int deleteReply(long rno);
}
