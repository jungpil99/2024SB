package com.example.sb1024_2;

import com.example.sb1024_2.fileuploadboard.entity.Board;
import com.example.sb1024_2.repository.BoradRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest
class Sb10242ApplicationTests {

    @Autowired
    BoradRepository repository;

    @Test
    void testPage1() {
        Pageable pageable = PageRequest.of(1, 10);
        List<Board> page = repository.findAllByOrderByBoardIdxDesc(pageable);
        for (Board board : page) {
            System.out.println(board);
        }
    }

    @Test
    void testPage2() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Board> page = repository.findAllByOrderByBoardIdxDesc(pageable);
        System.out.println(page.getFirst());
        System.out.println(page.getLast());
        System.out.println(page.size());
        System.out.println(page.isEmpty());
    }

}
