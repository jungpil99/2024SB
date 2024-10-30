package com.example.sb1030.controller;

import javax.servlet.http.HttpSession;

import com.example.sb1030.spring.AuthInfo;
import com.example.sb1030.spring.AuthService;
import com.example.sb1030.spring.WrongIdPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private AuthService authService;

	@GetMapping
	public String form(LoginCommand loginCommand) {
		return "login/loginForm";
	}
	
	@PostMapping
	public String submit(LoginCommand loginCommand, Errors errors, HttpSession session) {
		new LoginCommandValidator().validate(loginCommand, errors);
		if(errors.hasErrors()) {
			return "login/loginForm";
		}
		try {
			AuthInfo authInfo = authService.authenticate(
					loginCommand.getEmail(),
					loginCommand.getPassword());
			System.out.println(authInfo.getName());
			
			session.setAttribute("authInfo", authInfo);
			return "login/loginSuccess";
		}catch(WrongIdPasswordException e) {
			errors.reject("idPasswordNotMatching"); //폼 양식 자체에 오류를 준다
			return "login/loginForm";
		}
	}

}
