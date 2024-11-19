package com.example.sample.question.entity;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name= "t_question")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    private String title;

    private String content;

    private String username;

    private String createdDatetime;

    private String answer;

    private String answerDatetime;

    private String questionYn;
}
