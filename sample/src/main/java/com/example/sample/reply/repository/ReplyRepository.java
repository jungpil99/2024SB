package com.example.sample.reply.repository;

import com.example.sample.reply.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    void deleteById(Integer replyId);

    Page<Reply> findByBoard_BoardIdx(Integer boardIdx, Pageable pageable);

    Page<Reply> findByReview_ReviewId(Integer reviewId, Pageable pageable);

    public List<Reply> findByUsername(String username);

    void deleteByUsername(String username);

    Optional<Reply> findByReplyIdAndReviewReviewId(Integer replyId, Integer reviewId);

    Optional<Reply> findByReplyIdAndBoardBoardIdx(Integer replyId, Integer boardIdx);
}
