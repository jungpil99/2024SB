package com.example.sample.review.repository;

import com.example.sample.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findByReviewId(Integer reviewId);
}
