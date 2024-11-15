package com.example.sample.transport.controller;

import com.example.sample.transport.entity.TransPort;
import com.example.sample.transport.service.TransPortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/transport")
public class TransPortController {

    @Autowired
    TransPortService transPortService;

    @GetMapping
    public String transport() {
        return "/transport/transportMain";
    }

    @PostMapping("/transportSearch")
    public String Search(@RequestParam(required = false) String departureTime,
                         @RequestParam(required = false) String arrivalTime,
                         @RequestParam(required = false) String departureCity,
                         @RequestParam(required = false) String arrivalCity,
                         @RequestParam(required = false, defaultValue = "all") String transportType,
                         Model model) {

        if (transportType == null) {
            transportType = "all";  // 기본값으로 설정
        }

        if(arrivalCity == null) {
            List<TransPort> results = transPortService.searchNoArrive(departureTime, arrivalTime, departureCity, transportType);
        }

        if(arrivalTime == null) {
            List<TransPort> results = transPortService.searchOneWay(departureTime, departureCity, arrivalCity, transportType);
        }

        

        List<TransPort> results = transPortService.searchTransports(departureTime, arrivalTime, departureCity, arrivalCity, transportType);

        model.addAttribute("results", results);
        return "/transport/transportSearch";
    }
}
