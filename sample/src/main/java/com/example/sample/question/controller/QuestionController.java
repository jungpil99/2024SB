package com.example.sample.question.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @GetMapping("/questionMain")
    public String question() {
        return "/question/questionMain";
    }

    @GetMapping("/write")
    public String write() {
        return "/question/questionWrite";
    }

    @GetMapping("/questionDetail")
    public String questionDetail() {
        return "/question/questionDetail";
    }
}
