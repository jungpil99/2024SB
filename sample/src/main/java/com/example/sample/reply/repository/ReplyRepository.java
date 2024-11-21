package com.example.sample.reply.repository;

import com.example.sample.reply.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    public void deleteById(Integer replyId);

    Page<Reply> findByBoard_BoardIdx(Integer boardIdx, Pageable pageable);

    public List<Reply> findByUsername(String username);

    void deleteByUsername(String username);
}
