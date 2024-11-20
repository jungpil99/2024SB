package com.example.sample.notice.controller;

import com.example.sample.Board.entity.Board;
import com.example.sample.notice.entity.Notice;
import com.example.sample.notice.repository.NoticeRepository;
import com.example.sample.notice.service.NoticeService;
import com.example.sample.spring.AuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/notice")
@Slf4j
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @Autowired
    NoticeRepository noticeRepository;

    @GetMapping("/noticeList")
    public String notice(Model model, @RequestParam(value = "query", required = false) String query, @PageableDefault(page = 0, size = 10) Pageable pageable) throws Exception {
        List<Notice> list;

        if (query != null && !query.isEmpty()) {
            // 제목으로 검색된 게시글 리스트 가져오기
            list = noticeService.searchBoards(query);  // searchBoards 메소드에서 제목으로 검색
        } else {
            // 전체 게시글 리스트 가져오기
            list = noticeService.selectNoticeList();  // 전체 리스트를 가져오는 메소드
        }

        list.sort(Comparator.comparing(Notice::getNoticeId).reversed());

        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), list.size());

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
        model.addAttribute("query", query);

        return "notice/noticeList";
    }

    @GetMapping("/noticeWrite")
    public String noticeWrite(HttpSession session, Model model, RedirectAttributes redirectAttributes) throws Exception {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if(authInfo != null && authInfo.getRole().equals("Admin")) {
            model.addAttribute("authorName", authInfo.getName());
            return "notice/noticeWrite";
        }else{
            redirectAttributes.addFlashAttribute("errorMessage", "권한이 없습니다");
            return "redirect:/notice/noticeList";
        }


    }

    @PostMapping("/insertNotice")
    public String insertNotice(@RequestParam("title") String title,
                               @RequestParam("contents") String contents,
                               HttpSession session, RedirectAttributes redirectAttributes) throws Exception {

        noticeService.insertBoard(session, title, contents);

        return "/notice/noticeList";
    }

    @GetMapping("/noticeDetail")
    public String noticeDetail(Model model, @RequestParam("noticeId") Integer noticeId, HttpSession session) throws Exception {

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Optional<Notice> optionalNotice = noticeService.selectBoardDetail(noticeId);

        if(optionalNotice.isPresent()) {
            Notice notice = optionalNotice.get();

            notice.setHitCnt(notice.getHitCnt() + 1);
            noticeService.updateNotice(notice);


            model.addAttribute("notice", notice);
        }

        if(authInfo!=null && authInfo.getRole().equals("Admin")) {
            model.addAttribute("Admin", authInfo.getRole());
        }

        return "notice/noticeDetail";
    }

    @GetMapping("/noticeSearch")
    public String boardSearch(@RequestParam("query") String title, Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) throws UnsupportedEncodingException {
        String encodedTitle = URLEncoder.encode(title, "UTF-8");

        List<Notice> list = noticeRepository.findByTitleContaining(title);

        list.sort(Comparator.comparing(Notice::getNoticeId).reversed());

        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), list.size());

        final Page<Notice> page = new PageImpl<>(list.subList(start, end), pageable, list.size());

        model.addAttribute("list", page);
        model.addAttribute("query", title);

        return "redirect:/notice/noticeList?query=" + encodedTitle;
    }

    @PostMapping("/deleteNotice")
    public String deleteNotice(@RequestParam("noticeId") Integer noticeId, RedirectAttributes redirectAttributes) throws Exception {

        noticeService.deleteNotice(noticeId);
        return "redirect:/notice/noticeList";
    }

    @PostMapping("/updateNotice")
    public String updateNotice(@RequestParam("noticeId") Integer noticeId,
                               @RequestParam("contents") String contents,
                               RedirectAttributes redirectAttributes) throws Exception {

        Optional<Notice> optionalNotice = noticeService.selectBoardDetail(noticeId);

        if(optionalNotice.isPresent()) {
            Notice notice = optionalNotice.get();

            notice.setContents(contents);
            noticeService.updateNotice(notice);
        }

        return "redirect:/notice/noticeDetail?noticeId=" + noticeId;
    }

}
