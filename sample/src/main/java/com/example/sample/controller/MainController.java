package com.example.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "main";
    }

//    @GetMapping("/trip")
//    public String trip() {
//        return "/trip/tripMain";
//    }

    @GetMapping("/tripDetail")
    public String tripDetail() {
        return "/trip/tripDetail";
    }

//    @GetMapping("/transport")
//    public String transport() {
//        return "/transport/transportMain";
//    }

//    @GetMapping("/stay")
//    public String stay() {
//        return "/stay/stayMain";
//    }

//    @GetMapping("/user")
//    public String user() {
//        return "/user/userMain";
//    }


}
