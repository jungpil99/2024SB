package com.example.sample.review.repository;

import com.example.sample.Board.entity.Board;
import com.example.sample.review.entity.Review;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findAll(Sort sort);

    Review findByReviewId(Integer reviewId);

    List<Review> findByUsername(String username);

    @Query("SELECT b FROM Review b WHERE b.title LIKE %:title%")
    List<Review> findByTitleContaining(@Param("title") String title);

    void deleteByUsername(String username);
}
