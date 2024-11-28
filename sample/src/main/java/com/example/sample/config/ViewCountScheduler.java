package com.example.sample.config;

import com.example.sample.Board.repository.BoardViewRepository;
import com.example.sample.notice.repository.NoticeViewRepository;
import com.example.sample.review.repository.ReviewViewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@Slf4j
public class ViewCountScheduler {

    @Autowired
    private BoardViewRepository boardViewRepository;

    @Autowired
    private NoticeViewRepository noticeViewRepository;

    @Autowired
    ReviewViewRepository reviewViewRepository;

    @Transactional
    @Scheduled(cron = "0 0 14 * * *")// 매일 자정(00:00)에 실행
    public void resetViewCount() {
        // 어제 날짜 구하기
        String todayDate = LocalDateTime.now().toString().substring(0, 10);

        try {
            int boardCount = boardViewRepository.deleteByViewDateLessThanEqual(todayDate);
            int noticeCount = noticeViewRepository.deleteByViewDateLessThanEqual(todayDate);
            int reviewCount = reviewViewRepository.deleteByViewDateLessThanEqual(todayDate);

            log.info("조회수 초기화 실행 - 현재시간: {}", LocalDateTime.now());
            log.info("삭제된 레코드 수 - 게시판: {}, 공지사항: {}, 리뷰: {}",
                    boardCount, noticeCount, reviewCount);
        } catch (Exception e) {
            log.error("조회수 초기화 중 오류 발생: ", e);
            e.printStackTrace();
        }
    }
}
