package com.example.sample.reply.repository;

import com.example.sample.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    public void deleteById(Integer replyId);

    public void findByBoardIdx(Integer boardIdx);
}
