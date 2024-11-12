package com.example.sample.review.controller;

import com.example.sample.Board.entity.Board;
import com.example.sample.review.entity.Review;
import com.example.sample.review.sevice.ReviewService;
import com.example.sample.spring.AuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping
    public String review(Model model, @RequestParam(value = "query", required = false) String query, @PageableDefault(page = 0, size = 10)
    Pageable pageable) throws Exception {

        List<Review> list = reviewService.selectReviewList();

        list.sort(Comparator.comparing(Review::getReviewId).reversed());

        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), list.size());

        log.info("start: {}, end: {}", start, end);
        final Page<Review> page = new PageImpl<>(list.subList(start, end), pageable, list.size());

        log.info("총 페이지 수: {}", page.getTotalPages());
        log.info("전체 개수: {}", page.getTotalElements());
        log.info("현재 페이지 번호: {}", page.getNumber());
        log.info("페이지당 데이터 개수: {}", page.getSize());
        log.info("다음 페이지 존재 여부: {}", page.hasNext());
        log.info("이전 페이지 존재 여부: {}", page.hasPrevious());
        log.info("시작페이지(0) 입니까: {}", page.isFirst());
        model.addAttribute("list", page);

        return "review/reviewList";
    }

    @GetMapping("/reviewWrite")
    public String reviewWrite(HttpSession session, Model model, RedirectAttributes redirectAttributes) throws Exception {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if (authInfo != null && authInfo.getName() != null && !authInfo.getName().isEmpty()) {
            // authInfo가 존재하고 name이 비어있지 않으면, 작성자 값을 모델에 추가
            model.addAttribute("authorName", authInfo.getName());
            // 사용자가 로그인된 상태이면 글쓰기 페이지로 이동
            return "review/reviewWrite";
        } else {
            // 로그인되지 않은 경우, 에러 메시지와 함께 게시판 목록 페이지로 리다이렉트
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다");
            return "redirect:/review";
        }

    }

    @PostMapping("/insertReview")
    public String insertReview(@RequestParam("title") String title,
                               @RequestParam("contents") String contents,
                               HttpSession session,
                               MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{

        reviewService.insertBoard(session,title,contents);

        return "redirect:/review";
    }

    @GetMapping("/reviewDetail")
    public String reviewDetail(Model model, @RequestParam("reviewId") Integer reviewId, HttpSession session)
    {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Optional<Review> optionalReview = reviewService.selectReviewDetail(reviewId);

//        List<Reply> reply = replyService.selectReply(pageable);

        if (optionalReview.isPresent()) {

            Review review = optionalReview.get();

            review.setHitCnt(review.getHitCnt() + 1);
            reviewService.updateReview(review);

//            model.addAttribute("reply", reply);
            model.addAttribute("review", review); // Optional을 풀어 Board 객체만 전달
        } else {
            // 게시글을 찾을 수 없는 경우 처리 (예: 오류 페이지 표시)
            model.addAttribute("error", "게시글을 찾을 수 없습니다.");
            return "error";
        }

        return "review/reviewDetail";
    }

    @GetMapping("/reviewUpdate")
    public String reviewUpdate() {
        return "review/reviewUpdate";
    }
}
