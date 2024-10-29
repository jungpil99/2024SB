package com.example.sb1024_2.controller;



import com.example.sb1024_2.entity.Member;
import com.example.sb1024_2.repository.MemberRepository;
import com.example.sb1024_2.spring.DuplicateMemberException;
import com.example.sb1024_2.spring.MemberRegisterService;
import com.example.sb1024_2.spring.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
	public String handleStep3(@Validated RegisterRequest regReq,
							  BindingResult errors,
							  Model model, String name,
							  HttpSession session) {
//		if (!regReq.getPassword().equals(regReq.getConfirmPassword())) {
//			model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
//			return "register/step2"; // 비밀번호 불일치 시 step2로 돌아감
//		}
//
//		Optional<Member> member = repository.findByUsername(name);
//		if(member.isPresent()){
//			Member member1 = member.get();
//			if(regReq.getName().equals(member1.getUsername())){
//				model.addAttribute("errorMessage", "존재하는 사용자입니다");
//				return "register/step2";
//			}
//		}

//		if(regReq.getName().isEmpty()){
//			model.addAttribute("errorMessage", "사용자를 입력하세요");
//			return "register/step2";
//		}
//
//		if(regReq.getEmail().isEmpty()){
//			model.addAttribute("errorMessage", "이메일을 입력하세요");
//			return "register/step2";
//		}

//		new RegisterRequestValidator().validate(regReq, errors);
		if(errors.hasErrors())
			return "register/step2";


		try {
			memberRegisterService.regist(regReq);
			session.invalidate();
			return "register/step3";
		} catch (DuplicateMemberException ex) {
//			errors.rejectValue("email", "duplicate");
			errors.reject("notMatchingPassword");
//			errors.rejectValue("registerRequest", "notMatchingPassword");
			return "register/step2";
		}
	}

	@GetMapping("/main")
	public String main() {
		return "redirect:/sample/login";
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new RegisterRequestValidator());
	}

}
