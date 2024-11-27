package com.example.sample.Board.repository;

import com.example.sample.Board.entity.BoardView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardViewRepository extends JpaRepository<BoardView, Integer> {

    boolean existsByUsernameAndBoardIdx(String username, Integer boardIdx);

    int deleteByViewDateLessThanEqual(String date);  // Before 대신 LessThanEqual 사용
}
