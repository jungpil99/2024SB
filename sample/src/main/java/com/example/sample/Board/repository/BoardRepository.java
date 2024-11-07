package com.example.sample.Board.repository;

import com.example.sample.Board.entity.Board;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    List<Board> findAll(Sort sort);

    Board findByBoardIdx(Integer boardIdx);
}
