package com.example.sb1030.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.sb1030.spring.AuthInfo;
import com.example.sb1030.spring.AuthService;
import com.example.sb1030.spring.WrongIdPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private AuthService authService;

	@GetMapping
	public String form(LoginCommand loginCommand, @CookieValue(value = "REMEMBER", required = false) Cookie rCookie) { //쿠키 이름
		if(rCookie != null) {
			loginCommand.setEmail(rCookie.getValue());//커맨드 객체라
			loginCommand.setRememberEmail(true);
		}
		return "login/loginForm";
	}
	
	@PostMapping
	public String submit(LoginCommand loginCommand, Errors errors, HttpSession session,
						 HttpServletResponse response) {
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

			Cookie rememberCookie =
					new Cookie("REMEMBER", loginCommand.getEmail()); //이메일 정보를 꺼내주어서 뷰에 보내주는 부분
			rememberCookie.setPath("/");
			if(loginCommand.isRememberEmail()){
				rememberCookie.setMaxAge(60*60*24*30);
			}else{
				rememberCookie.setMaxAge(0);
			}
			response.addCookie(rememberCookie);//이걸로 브라우저에 응답을 안하면 쿠키 저장을 안함

			return "login/loginSuccess";
		}catch(WrongIdPasswordException e) {
			errors.reject("idPasswordNotMatching"); //폼 양식 자체에 오류를 준다
			return "login/loginForm";
		}
	}

}
