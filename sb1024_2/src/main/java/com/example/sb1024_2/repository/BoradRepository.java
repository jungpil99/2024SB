package com.example.sb1024_2.repository;

import com.example.sb1024_2.fileuploadboard.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoradRepository extends JpaRepository<Board, Integer> {

    public List<Board> findAllByOrderByBoardIdxDesc(Pageable pageable);
}
