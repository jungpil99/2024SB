package com.example.sb1029.controller;

import com.example.sb1029.entity.Notice;
import com.example.sb1029.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class NoticeController {

    @Autowired
    NoticeRepository repository;

    @RequestMapping("/")
    public String index(){
        return "main";
    }

    @GetMapping("/board/boardList")
    public String list(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) throws Exception {
        List<Notice> list  = repository.findAllByOrderByIdDesc();

        final int start = (int) pageable.getOffset();
        // Math.min()을 사용하여 리스트의 크기를 넘지 않도록 보정합니다.
        final int end = Math.min((start + pageable.getPageSize()), list.size());

        log.info("start: {}, end: {}", start, end);
        final Page<Notice> page = new PageImpl<>(list.subList(start, end), pageable, list.size());

        log.info("총 페이지 수: {}", page.getTotalPages());
        log.info("전체 개수: {}", page.getTotalElements());
        log.info("현재 페이지 번호: {}", page.getNumber());
        log.info("페이지당 데이터 개수: {}", page.getSize());
        log.info("다음 페이지 존재 여부: {}", page.hasNext());
        log.info("이전 페이지 존재 여부: {}", page.hasPrevious());
        log.info("시작페이지(0) 입니까: {}", page.isFirst());
        model.addAttribute("list", page);


        return "/board/boardList";
    }

    @GetMapping("/board/write")
    public String write(Model model){
        model.addAttribute("notice", new Notice());
        return "/board/boardWrite";
    }

    @PostMapping("/board/insertBoard")
    public String insert(@Validated @ModelAttribute("notice") Notice notice, BindingResult errors, Model model, String title, String content) throws Exception {
        if(errors.hasErrors()){
            model.addAttribute("notice", notice);
            return "/board/boardWrite";
        }

        notice.setTitle(title);
        notice.setContent(content);
        notice.setHitCnt(0);
        notice.setCreatedDatetime(LocalDateTime.now().toString().substring(0, 10));
        repository.save(notice);
        return "redirect:/board/boardList";
    }

    @GetMapping("/board/boardDetail")
    public String detail(@RequestParam int id, Model model) throws Exception {

        Optional<Notice> notice = repository.findById(id);
        model.addAttribute("notice", notice.get());

        if(notice.isPresent()){
            Notice notice1 = notice.get();
            notice1.setHitCnt(notice1.getHitCnt() + 1);
            repository.save(notice1);
        }
        return "/board/boardDetail";
    }

    @PostMapping("/board/updateBoard")
    public String update(@RequestParam int id, String title, String content) throws Exception {

        Optional<Notice> optionalNotice = repository.findById(id);
        if(optionalNotice.isPresent()) {
            Notice notice = optionalNotice.get();
            notice.setTitle(title);
            notice.setContent(content);
            notice.setUpdatedDatetime(LocalDateTime.now().toString().substring(0, 10));
            repository.save(notice);
        }

        return "redirect:/board/boardList";
    }

    @PostMapping("/board/deleteBoard")
    public String delete(@RequestParam int id) throws Exception {
        repository.deleteById(id);
        return "redirect:/board/boardList";
    }

    @InitBinder("notice")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new NoticeValidator());
    }
}
