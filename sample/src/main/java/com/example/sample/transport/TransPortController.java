package com.example.sample.transport;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transport")
public class TransPortController {

    @GetMapping
    public String transport() {
        return "/transport/transportMain";
    }

    @PostMapping("/transportSearch")
    public String Search(){
        return "/transport/transportSearch";
    }
}
