package com.example.sb1024_2.controller;



import com.example.sb1024_2.entity.Member;
import com.example.sb1024_2.repository.MemberRepository;
import com.example.sb1024_2.spring.DuplicateMemberException;
import com.example.sb1024_2.spring.MemberRegisterService;
import com.example.sb1024_2.spring.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
public class RegisterController {

	@Autowired
	MemberRepository repository;

	@Autowired
	private MemberRegisterService memberRegisterService;

	public void setMemberRegisterService(
			MemberRegisterService memberRegisterService) {
		this.memberRegisterService = memberRegisterService;
	}


	@RequestMapping("/register/step1")
	public String handleStep1() {
		return "register/step1";
	}

	@PostMapping("/register/step2")
	public String handleStep2(
			@RequestParam(value = "agree", defaultValue = "false") Boolean agree,
			Model model) {
		if (!agree) {
			return "register/step1";
		}
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register/step2";
	}

	@GetMapping("/register/step2")
	public String handleStep2Get() {
		return "redirect:/register/step1";
	}

	@PostMapping("/register/step3")
	public String handleStep3(RegisterRequest regReq, Model model, String name) {
		if (!regReq.getPassword().equals(regReq.getConfirmPassword())) {
			model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
			return "register/step2"; // 비밀번호 불일치 시 step2로 돌아감
		}

		Optional<Member> member = repository.findByUsername(name);
		if(member.isPresent()){
			Member member1 = member.get();
			if(regReq.getName().equals(member1.getUsername())){
				model.addAttribute("errorMessage", "존재하는 사용자입니다");
				return "register/step2";
			}
		}

		try {
			memberRegisterService.regist(regReq);
			return "register/step3";
		} catch (DuplicateMemberException ex) {
			return "register/step2";
		}
	}

	@GetMapping("/main")
	public String main() {
		return "redirect:/sample/all";
	}

}
