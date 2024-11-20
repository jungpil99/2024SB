package com.example.sample.question.service;

import com.example.sample.Board.entity.Board;
import com.example.sample.question.entity.Question;
import com.example.sample.question.repository.QuestionRepository;
import com.example.sample.review.entity.Review;
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
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public List<Question> selectByUsername(String username) {
        return questionRepository.findByUsername(username ,Sort.by(Sort.Order.desc("questionId")));
    }

    public void insertQuestion(HttpSession session,
                            @RequestParam("title") String title,
                            @RequestParam("contents") String contents){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        Question question = Question.builder()
                .title(title)
                .createdDatetime(LocalDateTime.now().toString().substring(0, 10))
                .contents(contents)
                .questionYn("답변 대기")
                .username(authInfo.getName())
                .build();
        questionRepository.save(question);
    }

    public Optional<Question> questionDetail(Integer questionId) {
        return questionRepository.findById(questionId);
    }

    public void deleteQuestion(Integer questionId) {
        questionRepository.deleteById(questionId);
    }

    public void updateQuestion(Question question){
        questionRepository.save(question);
    }
}
