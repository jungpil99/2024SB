package com.example.sb1029.repository;

import com.example.sb1029.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    public List<Notice> findAllByOrderByIdDesc();

}
