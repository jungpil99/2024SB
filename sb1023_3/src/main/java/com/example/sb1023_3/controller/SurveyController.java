package com.example.sb1023_3.controller;

import java.util.Arrays;
import java.util.List;


import com.example.sb1023_3.entity.AnsweredData;
import com.example.sb1023_3.entity.Question;
import com.example.sb1023_3.repository.AnswerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SurveyController {

	@Autowired
	AnswerDataRepository repository;

	@GetMapping("/survey")
	public String form(Model model) {
		List<Question> questions = createQuestions();
		model.addAttribute("questions", questions);

		AnsweredData ansData = new AnsweredData();
		model.addAttribute("ansData", ansData);
		return "surveyForm";
	}

	private List<Question> createQuestions() {
		Question q1 = new Question("당신의 역할은 무엇입니까?",
				Arrays.asList("서버", "프론트", "풀스택"));
		Question q2 = new Question("많이 사용하는 개발도구는 무엇입니까?",
				Arrays.asList("이클립스", "인텔리J", "서브라임"));
		Question q3 = new Question("하고 싶은 말을 적어주세요.");
		return Arrays.asList(q1, q2, q3);
	}

	@PostMapping("/submitSurvey")
	public String submitSurvey(@ModelAttribute("ansData") AnsweredData data) {
		repository.save(data);
		return "submitted";
	}
}
