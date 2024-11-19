package com.example.sample.controller;

import com.example.sample.Board.entity.Board;
import com.example.sample.Board.service.BoardService;
import com.example.sample.notice.entity.Notice;
import com.example.sample.notice.service.NoticeService;
import com.example.sample.review.entity.Review;
import com.example.sample.review.sevice.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@Slf4j
public class MainController {

    @Autowired
    BoardService boardService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    NoticeService noticeService;

    @GetMapping("/")
    public String index(Model model, @PageableDefault(page = 0, size = 5) Pageable pageable) {

        List<Board> boardList = boardService.selectBoardList();

        boardList.sort(Comparator.comparing(Board::getBoardIdx).reversed());

        final int start = (int) pageable.getOffset();

        final int end = Math.min(start + pageable.getPageSize(), boardList.size());
        final Page<Board> boardPage = new PageImpl<>(boardList.subList(start, end), pageable, boardList.size());

        model.addAttribute("board", boardPage);

        List<Notice> noticeList = noticeService.selectNoticeList();

        noticeList.sort(Comparator.comparing(Notice::getNoticeId).reversed());

        final int noticeStart = (int) pageable.getOffset();
        final int noticeEnd = Math.min(start + pageable.getPageSize(), noticeList.size());

        log.info("start: {}, end: {}", start, end);
        final Page<Notice> noticePage = new PageImpl<>(noticeList.subList(noticeStart, noticeEnd), pageable, noticeList.size());

        model.addAttribute("notice", noticePage);

        List<Review> reviewList = reviewService.selectReviewList();

        reviewList.sort(Comparator.comparing(Review::getReviewId).reversed());

        final int reviewStart = (int) pageable.getOffset();
        final int reviewEnd = Math.min(start + pageable.getPageSize(), reviewList.size());

        log.info("start: {}, end: {}", start, end);
        final Page<Review> reviewPage = new PageImpl<>(reviewList.subList(reviewStart, reviewEnd), pageable, reviewList.size());
        model.addAttribute("review", reviewPage);


        return "main";
    }

}
