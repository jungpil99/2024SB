package com.example.sample.Board.repository;

import com.example.sample.Board.entity.Board;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    List<Board> findAll(Sort sort);

    Board findByBoardIdx(Integer boardIdx);

    List<Board> findByUsername(String username);

    @Query("SELECT b FROM Board b WHERE b.title LIKE %:title%")
    List<Board> findByTitleContaining(@Param("title") String title);

    void deleteByUsername(String username);
}
