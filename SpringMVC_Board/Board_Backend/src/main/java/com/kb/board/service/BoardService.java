package com.kb.board.service;

import com.kb.board.dto.*;
import com.kb.board.mapper.BoardMapper;
import com.kb.common.pagination.PageInfo;
import com.kb.common.util.UploadFiles;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j
@Service
@RequiredArgsConstructor
@PropertySource({"classpath:/application.properties"})
public class BoardService {
    @Value("#{'${os_type}' == 'win' ? '${file_save_location_win}/board':'${file_save_location_other}/board'}")
    public String BASE_DIR;

    private final BoardMapper mapper;
    private static List<BoardCategory> categoryList;
    private static final int PAGE_LIMIT = 5;
    private static final int LIST_LIMIT = 12; // 게시글 보여질 list 수

    public List<BoardCategory> getCategoryList() {
        if (categoryList == null) {
            categoryList = mapper.selectBoardCategory();
        }
        return categoryList;
    }

    public BoardPageResult getBoardList(BoardParam boardParam) {
        int totalSize = mapper.selectBoardCount(boardParam);
        int listLimit = boardParam.getAmount() == 0 ? LIST_LIMIT : boardParam.getAmount();
        PageInfo pageInfo = new PageInfo(boardParam.getPage(), totalSize, listLimit, PAGE_LIMIT);
        boardParam.setLimit(pageInfo.getListLimit());
        boardParam.setOffset(pageInfo.getStartList() - 1);
        List<Board> boardList = mapper.selectBoardList(boardParam);
        if (boardList == null || boardList.isEmpty()) {
            boardList = new ArrayList<>();
        }
        return new BoardPageResult(boardList, boardParam, pageInfo, getCategoryList(),totalSize);
    }

    @Transactional
    public Board getBoard(long bno) {
        log.info("get......" + bno);
        Board board = mapper.selectBoardByBno(bno);
        board.setReadCount(board.getReadCount() + 1);
        mapper.updateReadCount(board);
        log.info("========================" + board);
        return Optional.of(board)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(rollbackFor = Exception.class) // 2개 이상의 insert 문이 실행될 수 있으므로 트랜잭션 처리 필요
    public Board createBoard(Board board, List<MultipartFile> files) {
        log.info("create......" + board);
        int result = mapper.insertBoard(board);
        if (result != 1) {
            throw new NoSuchElementException();
        }
        // 파일 업로드 처리
        if (files != null && !files.isEmpty()) {
            upload(board.getBno(), files);
        }
        return getBoard(board.getBno());
    }


    private void upload(long bno, List<MultipartFile> files) {
        for (MultipartFile part : files) {
            if (part.isEmpty()) continue;
            try {
                String renameFileName = UploadFiles.upload(BASE_DIR, part);
                BoardAttachFile attach = new BoardAttachFile(0, bno, part.getOriginalFilename(), renameFileName, part.getContentType(), part.getSize(), null);
                mapper.insertAttachFile(attach);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteFile(String savePath, BoardAttachFile boardAttachFile) {
        File file = new File(savePath + "/" + boardAttachFile.getRenamedFilename());
        if (file.exists()) {
            file.delete();
        }
    }


    @Transactional
    public Board updateBoard(Board board, List<MultipartFile> files) {
        log.info("update...... " + board);
//        Board oldBoard = getBoard(board.getBno());

        int result = mapper.updateBoard(board);
        if (result != 1) {
            throw new NoSuchElementException();
        }

        // 파일 업로드 처리
        if (files != null && !files.isEmpty()) {
            upload(board.getBno(), files);
        }

        return getBoard(board.getBno());
    }


    @Transactional
    public Board deleteBoard(long bno) {
        log.info("delete...." + bno);
        Board board = getBoard(bno);

        List<BoardAttachFile> oldFiles = board.getBoardAttachFileList();
        for (BoardAttachFile old : oldFiles) {
            deleteFile(BASE_DIR, old);
        }

        int result = mapper.deleteBoard(bno);
        if (result != 1) {
            throw new NoSuchElementException();
        }
        return board;
    }

    public BoardAttachFile getAttachment(long fno) {
        return mapper.selectAttachFileByFno(fno);
    }

    public boolean deleteAttachment(long fno) {
        BoardAttachFile attachFile =  mapper.selectAttachFileByFno(fno);
        deleteFile(BASE_DIR, attachFile);
        return mapper.deleteAttachFile(fno) == 1;
    }

    public BoardReply createReply(BoardReply reply) {
        int result = mapper.insertReply(reply);
        reply = mapper.selectReplyByRno(reply.getRno());
        return reply;
    }

    public BoardReply getReply(long rno) {
        return mapper.selectReplyByRno(rno);
    }

    public int delteReply(long rno) {
        return mapper.deleteReply(rno);
    }
}
