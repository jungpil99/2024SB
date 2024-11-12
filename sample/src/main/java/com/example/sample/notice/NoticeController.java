package com.example.sample.notice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @GetMapping
    public String notice() {
        return "notice/noticeList";
    }

    @GetMapping("/noticeWrite")
    public String noticeWrite() {
        return "notice/noticeWrite";
    }

    @GetMapping("/noticeDetail")
    public String noticeDetail() {
        return "notice/noticeDetail";
    }
}
