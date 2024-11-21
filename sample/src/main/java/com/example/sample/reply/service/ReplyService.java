package com.example.sample.reply.service;

import com.example.sample.Board.entity.Board;
import com.example.sample.Board.service.BoardService;
import com.example.sample.reply.entity.Reply;
import com.example.sample.reply.repository.ReplyRepository;
import com.example.sample.spring.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    BoardService boardService;

    public Page<Reply> selectReply(Pageable pageable, Integer boardIdx) {
        return replyRepository.findByBoard_BoardIdx(boardIdx, pageable);
    }

    public List<Reply> selectReplyByUserName(String username) {
        return replyRepository.findByUsername(username);
    }

    public void saveReply(@RequestParam("replyContents") String replyContents,
                          @RequestParam("boardIdx") Integer boardIdx,
                          HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        Board board = boardService.selectBoardDetail(boardIdx)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Reply reply = Reply.builder()
                .username(authInfo.getName())
                .replyContents(replyContents)
                .board(board)
                .replyDate(LocalDateTime.now().toString().substring(0, 10))
                .build();

        replyRepository.save(reply);
    }

    public void deleteReply(@RequestParam("replyId") Integer replyId){
        replyRepository.deleteById(replyId);
    }

    public void deleteByUserName(String username){
        replyRepository.deleteByUsername(username);
    }

    public void updateReply(@RequestParam("replyId") Integer replyId, Reply reply){
        replyRepository.save(reply);
    }

    public Optional<Reply> findReplyById(Integer replyId){
        return replyRepository.findById(replyId);
    }
}
