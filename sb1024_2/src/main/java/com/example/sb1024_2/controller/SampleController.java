package com.example.sb1024_2.controller;

import com.example.sb1024_2.service.MemberLoginService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/sample/")
@Log4j2
public class SampleController {

    @Autowired
    private MemberLoginService service;

    @GetMapping("/accessDenied")
    public void accessDenied() {
    }

    @GetMapping("/admin")
    public void admin() {
        log.info("exAdmin......");
    }

    @GetMapping("/member")
    public void member() {
        log.info("exMember......");
    }

    @GetMapping("/all")
    public void all() {
        log.info("exAll......");
    }

    @GetMapping("/login")
    public void login(String errorCode, String logout) {
        log.info("login 페이지..........");
        if (logout != null) {
            log.info("user logout..........");
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {

        boolean login = service.login(username, password);
        if (login) {
            return "redirect:/sample/all";
        }else{
            model.addAttribute("errorMessage", "로그인에 실패하였습니다. 사용자 이름 또는 비밀번호를 확인하세요.");
            return "/sample/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request) {
        session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/sample/all";
    }


}