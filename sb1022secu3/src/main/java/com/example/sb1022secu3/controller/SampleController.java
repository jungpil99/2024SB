package com.example.sb1022secu3.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample/")
@Log4j2
public class SampleController {
    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "/sample/accessDenied";
    }

    @GetMapping("/admin")
    public String admin() {
        log.info("exAdmin......");
        return "/sample/admin";
    }

    @GetMapping("/member")
    public String member() {
        log.info("exMember......");
        return "/sample/member";
    }

    @GetMapping("/all")
    public String all() {
        log.info("exAll......");
        return "/sample/all";
    }

    
}