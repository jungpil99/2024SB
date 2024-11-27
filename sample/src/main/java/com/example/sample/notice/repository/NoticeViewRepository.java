package com.example.sample.notice.repository;

import com.example.sample.notice.entity.NoticeView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeViewRepository extends JpaRepository<NoticeView, Integer> {

    boolean existsByUsernameAndNoticeId(String username, Integer noticeId);

    int deleteByViewDateLessThanEqual(String date);  // Before 대신 LessThanEqual 사용
}
