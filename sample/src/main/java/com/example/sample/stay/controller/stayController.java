package com.example.sample.stay.controller;

import com.example.sample.stay.entity.Stay;
import com.example.sample.stay.repository.StayRepository;
import com.example.sample.stay.service.StayService;
import com.example.sample.transport.entity.TransPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/stay")
@Slf4j
public class stayController {

    @Autowired
    StayService stayService;

    @Autowired
    StayRepository stayRepository;

    @GetMapping("/stayMain")
    public String stay(Model model) {
        List<Stay> list = stayRepository.findAll();
        Random random = new Random();

        List<Stay> randomList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int randomInt = random.nextInt(list.size());
            randomList.add(list.get(randomInt));
        }

        model.addAttribute("list", randomList);
        return "/stay/stayMain";
    }

    @GetMapping("/staySearch")
    public String staySearch(@RequestParam(required = false) String stayType,
                             @RequestParam(required = false) String stayAddress,
                             @RequestParam(required = false) String templateType,
                             Model model,
                             @PageableDefault(page = 0, size = 5) Pageable pageable) {

        if(stayType == null){
            stayType = "all";
        }

        if (stayAddress == null) {
            stayAddress = "";
        }

        List<Stay> list = stayService.SearchByStay(stayType, stayAddress);

        log.info("Received stayType: {}, stayAddress: {}, templateType: {}", stayType, stayAddress, templateType);

        if (stayAddress.isEmpty()) {
            if ("stayMain".equals(templateType)) {
                model.addAttribute("stayAddressNull", "찾으시려는 지역을 입력해주세요");
                return "/stay/stayMain";
            } else if (!stayType.equals("all")) {
                // 특정 stayType을 검색
                list = stayService.SearchByType(stayType);
            } else {
                // stayType이 all이면 모든 데이터를 찾음
                list = stayRepository.findAll();
            }
        }

        list.sort(Comparator.comparing(Stay::getStayId));

        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), list.size());

        log.info("start: {}, end: {}", start, end);
        final Page<Stay> page = new PageImpl<>(list.subList(start, end), pageable, list.size());

        log.info("Results size: " + list.size());  // 결과의 크기 확인
        model.addAttribute("list", page);
        model.addAttribute("stayAddress", stayAddress);
        model.addAttribute("stayType", stayType);
        return "/stay/staySearch";
    }

}
