package com.example.sb1024_2.survey;

import com.example.sb1024_2.entity.Member;
import com.example.sb1024_2.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/survey")
public class SurveyController {

	@Autowired
	MemberRepository repository;

	@Autowired
	SurveyService surveyService;

	@GetMapping
	public String form(AnsweredData data,Model model, HttpSession session, Respondent respondent) {
		List<Question> questions = createQuestions();

		for (Question question : questions) {
			System.out.println(question);
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

//		if(respondent!= null && username.equals(respondent.getMember().getUsername())){
//			model.addAttribute("answeredData", respondent.getAnswer());
//		}

		model.addAttribute("username", username);
		model.addAttribute("questions", questions);
		return "/survey/surveyForm";
	}

	private List<Question> createQuestions() {
		Question q1 = new Question("당신의 역할은 무엇입니까?",
				Arrays.asList("서버", "프론트", "풀스택", "기타"));
		Question q2 = new Question("많이 사용하는 개발도구는 무엇입니까?",
				Arrays.asList("이클립스", "인텔리J", "서브라임", "기타"));
		Question q3 = new Question("하고 싶은 말을 적어주세요.");
		return Arrays.asList(q1, q2, q3);
	}

	@PostMapping
	public String submit(@ModelAttribute("ansData") AnsweredData data, HttpSession session) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		// Member 객체를 가져와서 data에 설정합니다.
		Optional<Member> member = repository.findByUsername(username);
		if (member.isPresent()) {
			data.setMember(member.get());

			Respondent respondent = new Respondent();
			respondent.setAge(data.getRes().getAge());
			respondent.setLocation(data.getRes().getLocation());
			respondent.setAnswer("Y");
			respondent.setMember(member.get());

			data.setRes(respondent);

		}
		surveyService.save(data);
		return "/survey/submitted";
	}

}
