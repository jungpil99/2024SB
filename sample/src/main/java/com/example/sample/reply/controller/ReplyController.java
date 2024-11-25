package com.example.sample.reply.controller;

import com.example.sample.Board.entity.Board;
import com.example.sample.Board.service.BoardService;
import com.example.sample.reply.entity.Reply;
import com.example.sample.reply.repository.ReplyRepository;
import com.example.sample.reply.service.ReplyService;
import com.example.sample.review.entity.Review;
import com.example.sample.review.sevice.ReviewService;
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
    
    @Autowired
    ReviewService reviewService;



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
                              HttpSession session,RedirectAttributes redirectAttributes){
        Optional<Board> optionalBoard = boardService.selectBoardDetail(boardIdx);
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if(authInfo != null && authInfo.getName().equals(username)) {

            replyService.deleteReply(replyId, boardIdx);
//삭제에 성공하면 댓글수를 1개 하락
            if(optionalBoard.isPresent()) {
                Board board = optionalBoard.get();
                board.setReplyCnt(board.getReplyCnt() - 1);
                boardService.updateBoard(board);
            }
        }else {
            redirectAttributes.addFlashAttribute("RoleError", "권한이 없습니다.");
        }

        return "redirect:/board/boardDetail?boardIdx=" + boardIdx;
    }

    @PostMapping("/updateReply")
    public String updateReply(@RequestParam("boardIdx") Integer boardIdx,
                              @RequestParam("username") String username,
                              @RequestParam("replyId") Integer replyId,
                              @RequestParam("replyContents") String replyContents,
                              HttpSession session,RedirectAttributes redirectAttributes){

        Optional<Reply> optionalReply = replyService.findReplyById(replyId);
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if(authInfo != null && authInfo.getName().equals(username)){
            if(optionalReply.isPresent()) {
                Reply reply = optionalReply.get();
                reply.setReplyContents(replyContents);  // replyContents 설정
                replyRepository.save(reply);
            }
        }else {
            redirectAttributes.addFlashAttribute("RoleError", "권한이 없습니다.");
        }


        return "redirect:/board/boardDetail?boardIdx=" + boardIdx;
    }
    
    // 밑으로는 리뷰 게시판 쪽 댓글 관리 관련 내용
    
    @PostMapping("/addReviewReply")
    public String insertReviewReply(@RequestParam("reviewId") Integer reviewId,
                                    @RequestParam("replyContents") String replyContents,
                                    HttpSession session, RedirectAttributes redirectAttributes){
        
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Optional<Review> optionalReview = reviewService.selectReviewDetail(reviewId);

        if(authInfo != null) {
            replyService.saveReviewReply(replyContents, reviewId ,session);
            if (optionalReview.isPresent()) {
                Review review = optionalReview.get();
                review.setReplyCnt(review.getReplyCnt() + 1);
                reviewService.updateReview(review);
            }
        }else{
            redirectAttributes.addFlashAttribute("replyError", "로그인을 먼저 해주세요.");
            return "redirect:/review/reviewDetail?reviewId=" + reviewId;  // 동일한 게시글 상세 화면으로 리디렉션
        }
        
        return "redirect:/review/reviewDetail?reviewId=" + reviewId;
    }
    
    @PostMapping("/editReviewReply")
    public String updateReviewReply(@RequestParam("reviewId") Integer reviewId,
                                    @RequestParam("username") String username,
                                    @RequestParam("replyId") Integer replyId,
                                    @RequestParam("replyContents") String replyContents,
                                    HttpSession session,RedirectAttributes redirectAttributes){
        
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Optional<Reply> optionalReply = replyService.findReplyById(replyId);
        
        if(authInfo != null && authInfo.getName().equals(username)){
            if(optionalReply.isPresent()) {
                Reply reply = optionalReply.get();
                reply.setReplyContents(replyContents);  // replyContents 설정
                replyRepository.save(reply);
            }
        }else {
            redirectAttributes.addFlashAttribute("RoleError", "권한이 없습니다.");
        }

        return "redirect:/review/reviewDetail?reviewId=" + reviewId;
    }
    
    @PostMapping("/delReviewReply")
    public String deleteReviewReply(@RequestParam("reviewId") Integer reviewId,
                                    @RequestParam("username") String username,
                                    @RequestParam("replyId") Integer replyId,
                                    HttpSession session,RedirectAttributes redirectAttributes){

        Optional<Review> optionalReview = reviewService.selectReviewDetail(reviewId);
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if(authInfo != null && authInfo.getName().equals(username)) {

            replyService.deleteReviewReply(replyId, reviewId);
//삭제에 성공하면 댓글수를 1개 하락
            if(optionalReview.isPresent()) {
                Review review = optionalReview.get();
                review.setReplyCnt(review.getReplyCnt() - 1);
                reviewService.updateReview(review);
            }
        }else {
            redirectAttributes.addFlashAttribute("RoleError", "권한이 없습니다.");
        }

        return "redirect:/review/reviewDetail?reviewId=" + reviewId;
    }
}
