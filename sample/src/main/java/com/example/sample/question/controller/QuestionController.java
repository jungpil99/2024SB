package com.example.sample.question.controller;

import com.example.sample.Board.entity.Board;
import com.example.sample.question.entity.Question;
import com.example.sample.question.repository.QuestionRepository;
import com.example.sample.question.service.QuestionService;
import com.example.sample.spring.AuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/question")
@Slf4j
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionService questionService;

    @GetMapping("/questionMain")
    public String question(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable,
                           HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        List<Question> list = new ArrayList<>();

        if (authInfo != null && authInfo.getName() != null && !authInfo.getName().isEmpty()) {
            list = questionService.selectByUsername(authInfo.getName());
        }

        if(authInfo != null && authInfo.getRole().equals("Admin")) {
            model.addAttribute("Admin", authInfo.getRole());
            list = questionRepository.findAll();
        }



        list.sort(Comparator.comparing(Question::getQuestionId).reversed());

        Page<Question> page = null;
        if (authInfo != null && !list.isEmpty()) {
            final int start = (int) pageable.getOffset();
            final int end = Math.min(start + pageable.getPageSize(), list.size());
            page = new PageImpl<>(list.subList(start, end), pageable, list.size());
        }

        model.addAttribute("list", page);

        return "/question/questionMain";
    }

    @GetMapping("/questionWrite")
    public String write(Model model, RedirectAttributes redirectAttributes,HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if(authInfo != null && authInfo.getName() != null && !authInfo.getName().isEmpty()){
            model.addAttribute("authorName", authInfo.getName());
            return "/question/questionWrite";
        }else{
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다");
            return "redirect:/question/questionMain";
        }

    }

    @PostMapping("/questionInsert")
    public String insertQuestion(@RequestParam("title") String title,
                                 @RequestParam("contents") String contents,
                                 HttpSession session) {

        questionService.insertQuestion(session, title, contents);
        return "redirect:/question/questionMain";
    }

    @GetMapping("/questionDetail")
    public String questionDetail(Model model, @RequestParam("questionId") Integer questionId,
                                 HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        if(optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            model.addAttribute("question", question);
            if(authInfo != null && authInfo.getName().equals(question.getUsername())){
                model.addAttribute("User", authInfo.getName());
            }
        }

        if(authInfo.getRole().equals("Admin")) {
            model.addAttribute("Admin", authInfo.getRole());
        }



        return "/question/questionDetail";
    }

    @PostMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam("questionId") Integer questionId, HttpSession session, Model model,
                                 RedirectAttributes redirectAttributes) throws Exception{
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Question question = questionService.questionDetail(questionId).orElse(null);
        if(authInfo != null && authInfo.getName().equals(question.getUsername())){
            questionService.deleteQuestion(questionId);
        }

        return "redirect:/question/questionMain";
    }

    @PostMapping("answers")
    public String answer(@RequestParam("questionId") Integer questionId,
                         @RequestParam("answers")String answers,
                         Model model){

        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        if(optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();

            question.setAnswers(answers);
            question.setAnswerDatetime(LocalDateTime.now().toString().substring(0, 10));
            question.setQuestionYn("답변 완료");
            questionService.updateQuestion(question);
        }

        return "redirect:/question/questionDetail?questionId=" + questionId;
    }
}
