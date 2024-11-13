package com.example.sample.notice.repository;

import com.example.sample.Board.entity.Board;
import com.example.sample.notice.entity.Notice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    List<Notice> findAll(Sort sort);

    @Query("SELECT b FROM Notice b WHERE b.title LIKE %:title%")
    List<Notice> findByTitleContaining(@Param("title") String title);
}
