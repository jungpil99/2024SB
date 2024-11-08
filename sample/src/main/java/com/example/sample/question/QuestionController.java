package com.example.sample.question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionController {

    @GetMapping("/question")
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
