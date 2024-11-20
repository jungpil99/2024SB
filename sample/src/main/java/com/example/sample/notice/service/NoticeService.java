package com.example.sample.notice.service;

import com.example.sample.Board.entity.Board;
import com.example.sample.notice.entity.Notice;
import com.example.sample.notice.repository.NoticeRepository;
import com.example.sample.review.entity.Review;
import com.example.sample.spring.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    @Autowired
    NoticeRepository noticeRepository;

    public List<Notice> selectNoticeList() {
        return noticeRepository.findAll(Sort.by(Sort.Order.desc("noticeId")));
    }

    public List<Notice> searchBoards(String title) {
        return noticeRepository.findByTitleContaining(title);
    }

    public void insertBoard(HttpSession session,
                            @RequestParam("title") String title,
                            @RequestParam("contents") String contents){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        Notice notice = Notice.builder()
                .title(title)
                .createdDatetime(LocalDateTime.now().toString().substring(0, 10))
                .contents(contents)
                .deletedYn("N")
                .hitCnt(0)
                .username(authInfo.getName())
                .build();
        noticeRepository.save(notice);
    }

    public Optional<Notice> selectBoardDetail(Integer noticeId){
        return noticeRepository.findById(noticeId);
    }

    public void updateNotice(Notice notice){
        noticeRepository.save(notice);
    }

    public List<Notice> selectListUserName(String username){
        return noticeRepository.findByUsername(username);
    }

    public void deleteNotice(Integer noticeId){
        noticeRepository.deleteById(noticeId);
    }
}
