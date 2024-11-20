package com.example.sample.question.repository;

import com.example.sample.question.entity.Question;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByUsername(String username, Sort sort);
}
