package com.example.sample.review.repository;

import com.example.sample.review.entity.ReviewView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewViewRepository extends JpaRepository<ReviewView, Integer> {

    boolean existsByUsernameAndReviewId(String username, Integer reviewId);

    int deleteByViewDateLessThanEqual(String date);  // Before 대신 LessThanEqual 사용
}
