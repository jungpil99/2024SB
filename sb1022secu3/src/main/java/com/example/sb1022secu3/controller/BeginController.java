package com.example.sb1022secu3.controller;

import com.example.sb1022secu3.entity.Member;
import com.example.sb1022secu3.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Controller
public class BeginController {

    @Autowired
    MemberRepository repository;

    @GetMapping("/")
    public String index() {
        return "/sample/all";
    }

    @PostConstruct
    public void init() {
        Member member = Member.builder()
                .id(1001L)
                .username("hong11")
                .password(passwordEncoder().encode("1234"))
                .email("hong@aaa.co.kr")
                .regdate(LocalDateTime.now())
                .role("ADMIN")
                .build();
        repository.save(member);
    }

    private PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}
}
