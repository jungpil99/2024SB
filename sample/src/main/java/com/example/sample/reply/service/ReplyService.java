package com.example.sample.reply.service;

import com.example.sample.reply.entity.Reply;
import com.example.sample.reply.repository.ReplyRepository;
import com.example.sample.spring.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {

    @Autowired
    ReplyRepository replyRepository;

    public List<Reply> selectReply(){
        return replyRepository.findAll();
    };

    public void saveReply(@RequestParam("replyContents") String replyContents,
                          @RequestParam("boardIdx") Integer boardIdx,
                          HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        Reply reply = Reply.builder()
                .username(authInfo.getName())
                .replyContents(replyContents)
                .boardIdx(boardIdx)
                .replyDate(LocalDateTime.now())
                .build();

        replyRepository.save(reply);
    }

    public void deleteReply(@RequestParam("replyId") Integer replyId){
        replyRepository.deleteById(replyId);
    }

    public void updateReply(@RequestParam("replyId") Integer replyId, Reply reply){
        replyRepository.save(reply);
    }

    public Optional<Reply> findReplyById(Integer replyId){
        return replyRepository.findById(replyId);
    }
}
