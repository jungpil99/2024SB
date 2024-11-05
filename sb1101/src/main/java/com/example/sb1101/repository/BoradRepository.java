package com.example.sb1101.repository;

import com.example.sb1101.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoradRepository extends JpaRepository<Board, Integer> {

    public List<Board> findAllByOrderByBoardIdxDesc(Pageable pageable);

    public Board findByTitle(String title);

    public Board findByBoardIdx(int boardIdx);

    public Board deleteByBoardIdx(int boardIdx);


}
