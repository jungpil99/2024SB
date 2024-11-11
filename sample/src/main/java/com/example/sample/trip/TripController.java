package com.example.sample.trip;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trip")
public class TripController {


    @GetMapping
    public String trip() {
        return "/trip/tripMain";
    }

    @PostMapping("/tripSearch")
    public String tripSearch() {
        return "/trip/tripSearch";
    }
}
