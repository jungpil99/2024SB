package com.example.sb1014;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/hello")
    public String[] hello() {
        System.out.println("SampleController.hello");
        return new String[] {"Hello", "World"};
    }
}
