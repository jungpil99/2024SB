package com.example.sample.review.sevice;

import com.example.sample.Board.entity.Board;
import com.example.sample.review.entity.Review;
import com.example.sample.review.repository.ReviewRepository;
import com.example.sample.spring.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    public List<Review> selectReviewList(){
        return reviewRepository.findAll(Sort.by(Sort.Order.desc("reviewId")));
    }

    public void insertBoard(HttpSession session,
                            @RequestParam("title") String title,
                            @RequestParam("contents") String contents){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        Review review = Review.builder()
                .title(title)
                .createdDatetime(LocalDateTime.now().toString().substring(0, 10))
                .contents(contents)
                .deletedYn("N")
                .hitCnt(0)
                .username(authInfo.getName())
                .build();
        reviewRepository.save(review);
    }

    public Optional<Review> selectReviewDetail(Integer reviewId){
        return reviewRepository.findById(reviewId);
    }

    public void updateReview(Review review){
        reviewRepository.save(review);
    }
}
