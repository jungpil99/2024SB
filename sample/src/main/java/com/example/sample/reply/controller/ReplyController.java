package com.example.sample.reply.controller;

import com.example.sample.Board.entity.Board;
import com.example.sample.Board.service.BoardService;
import com.example.sample.reply.entity.Reply;
import com.example.sample.reply.repository.ReplyRepository;
import com.example.sample.reply.service.ReplyService;
import com.example.sample.spring.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @Autowired
    BoardService boardService;

    @Autowired
    ReplyRepository replyRepository;



    @PostMapping("/insertReply")
    public String insertReply(@RequestParam("boardIdx") Integer boardIdx,
                            @RequestParam("replyContents") String replyContents,
                            HttpSession session, RedirectAttributes redirectAttributes) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Optional<Board> optionalBoard = boardService.selectBoardDetail(boardIdx);

        if(authInfo != null) {
            replyService.saveReply(replyContents, boardIdx,session);
            if (optionalBoard.isPresent()) {
                Board board = optionalBoard.get();
                board.setReplyCnt(board.getReplyCnt() + 1);
                boardService.updateBoard(board);
            }
        }else{
            redirectAttributes.addFlashAttribute("replyError", "로그인을 먼저 해주세요.");
            return "redirect:/board/boardDetail?boardIdx=" + boardIdx;  // 동일한 게시글 상세 화면으로 리디렉션
        }



        return "redirect:/board/boardDetail?boardIdx=" + boardIdx;
    }

    @PostMapping("/deleteReply")
    public String deleteReply(@RequestParam("boardIdx") Integer boardIdx,
                              @RequestParam("username") String username,
                              @RequestParam("replyId") Integer replyId,
                              HttpSession session){
        Optional<Board> optionalBoard = boardService.selectBoardDetail(boardIdx);
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if(authInfo != null && authInfo.getName().equals(username)) {

            replyService.deleteReply(replyId);
//삭제에 성공하면 댓글수를 1개 하락
            if(optionalBoard.isPresent()) {
                Board board = optionalBoard.get();
                board.setReplyCnt(board.getReplyCnt() - 1);
                boardService.updateBoard(board);
            }

        }

        return "redirect:/board/boardDetail?boardIdx=" + boardIdx;
    }

    @PostMapping("/updateReply")
    public String updateReply(@RequestParam("boardIdx") Integer boardIdx,
                              @RequestParam("username") String username,
                              @RequestParam("replyId") Integer replyId,
                              @RequestParam("replyContents") String replyContents,
                              HttpSession session){

        Optional<Reply> optionalReply = replyService.findReplyById(replyId);
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if(authInfo != null && authInfo.getName().equals(username)){
            if(optionalReply.isPresent()) {
                Reply reply = optionalReply.get();
                reply.setReplyContents(replyContents);  // replyContents 설정
                replyRepository.save(reply);
            }
        }


        return "redirect:/board/boardDetail?boardIdx=" + boardIdx;
    }
}
