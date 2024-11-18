package com.example.sample.transport.controller;

import com.example.sample.Board.entity.Board;
import com.example.sample.transport.entity.TransPort;
import com.example.sample.transport.repository.TransPortRepository;
import com.example.sample.transport.service.TransPortService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/transport")
@Slf4j
public class TransPortController {

    @Autowired
    TransPortService transPortService;

    @Autowired
    TransPortRepository transPortRepository;

    @GetMapping("/transportMain")
    public String transport(Model model) {
        List<TransPort> list = transPortRepository.findAll();
        Random random = new Random();

        List<TransPort> randomList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int randomInt = random.nextInt(list.size());
            randomList.add(list.get(randomInt));
        }

        model.addAttribute("list", randomList);
        return "/transport/transportMain";
    }

    @GetMapping("/transportSearch")
    public String Search(@RequestParam(required = false) String departureTime,
                         @RequestParam(required = false) String departureCity,
                         @RequestParam(required = false, defaultValue = "all") String transportType,
                         @RequestParam(required = false) String templateType,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         @PageableDefault(page = 0, size = 5) Pageable pageable) {

        TransPort transport = new TransPort();
        transport.setTransportType(transportType);  // 'airplane', 'train', 'bus' 등의 값 설정

        // Thymeleaf로 transport 객체 전달
        model.addAttribute("transport", transport);


        if (transportType == null) {
            transportType = "all";  // 기본값으로 설정
        }

        log.info("Search params - departureTime: {}, departureCity: {}, transportType: {}", departureTime, departureCity, transportType);

        List<TransPort> list = transPortService.searchTransports(departureTime, departureCity, transportType);
        log.info("Search result size: {}", list.size());
        if (!list.isEmpty()) {
            log.info("Search results: {}", list);
        }


        if (departureTime == null || departureTime.isEmpty()) {
            if ("transportMain".equals(templateType)) {
                model.addAttribute("departTimeNull", "출발 날짜를 선택하세요");
                return "/transport/transportMain";  // 메인 폼에서 에러 발생 시
            }else if (departureCity.isEmpty() && !transportType.equals("all")) {
                model.addAttribute("departCityNull", "출발 지를 입력해주세요");
                return "/transport/transportSearch"; // 검색 화면에 머무름
            }else {
                list = transPortService.searchByDepartCity(departureCity, transportType);
            }
        } else if (departureCity == null || departureCity.isEmpty()) {
            if ("transportMain".equals(templateType)) {
                model.addAttribute("departCityNull", "출발 지를 입력해주세요");
                return "/transport/transportMain";  // 메인 폼에서 에러 발생 시
            }else{
                list = transPortService.searchByDepartTime(departureTime, transportType);
            }
        }

        if (list == null || list.isEmpty()) {
            if ("transportMain".equals(templateType)) {
                model.addAttribute("departCityNull", "검색 정보가 없습니다");
                return "/transport/transportMain"; // 메인 화면으로 돌아감
            } else {
                model.addAttribute("departCityNull", "검색된 결과가 없습니다.");
                return "/transport/transportSearch"; // 검색 화면에 머무름
            }
        }

        if ((departureTime == null || departureTime.isEmpty())
                && (departureCity == null || departureCity.isEmpty())
                && (transportType == null || transportType.equals("all"))) {
            // 검색 조건이 없으면 모든 데이터를 반환
            list = transPortRepository.findAll();
        }

        list.sort(Comparator.comparing(TransPort::getTransId));

        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), list.size());

        log.info("start: {}, end: {}", start, end);
        final Page<TransPort> page = new PageImpl<>(list.subList(start, end), pageable, list.size());

        log.info("Results size: " + list.size());  // 결과의 크기 확인
        model.addAttribute("list", page);
        model.addAttribute("departureTime", departureTime);
        model.addAttribute("departureCity", departureCity);
        model.addAttribute("transportType", transportType);
        return "/transport/transportSearch";
    }

}
