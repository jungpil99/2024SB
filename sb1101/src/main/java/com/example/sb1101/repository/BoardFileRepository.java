package com.example.sb1101.repository;

import com.example.sb1101.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardFileRepository extends JpaRepository<BoardFile, Integer> {
    Optional<BoardFile> findByBoard_BoardIdxAndOriginalFileName(Integer boardIdx, String originalFileName);

}
