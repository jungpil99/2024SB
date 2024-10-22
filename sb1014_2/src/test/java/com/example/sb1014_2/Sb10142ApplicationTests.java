package com.example.sb1014_2;

import com.example.sb1014_2.Repository.BoardRepository;
import com.example.sb1014_2.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sb10142ApplicationTests {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void contextLoads() {
        for (Board board : boardRepository.findAll()) {
            System.out.println(board);
        }
    }

    @Test
    void selectAll(){
        for (Board board : boardRepository.selectBoardList()) {
            System.out.println(board);
        }
    }

}
