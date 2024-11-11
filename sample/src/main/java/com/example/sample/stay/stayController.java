package com.example.sample.stay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stay")
public class stayController {

    @GetMapping
    public String stay() {
        return "/stay/stayMain";
    }

    @PostMapping("/staySearch")
    public String staySearch() {
        return "/stay/staySearch";
    }

    @GetMapping("/stayDetail")
    public String stayDetail() {
        return "/stay/stayDetail";
    }
}
