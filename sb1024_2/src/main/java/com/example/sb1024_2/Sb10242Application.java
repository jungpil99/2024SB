package com.example.sb1024_2;

import com.example.sb1024_2.fileuploadboard.entity.Board;
import com.example.sb1024_2.repository.BoradRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class Sb10242Application {

    @Autowired
    BoradRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Sb10242Application.class, args);
    }

    @PostConstruct
    public void init() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("제목" + i)
                    .createdDatetime(LocalDateTime.now().toString().substring(0, 10))
                    .contents("내용" + i)
                    .deletedYn("N")
                    .hitCnt(0)
                    .build();
            repository.save(board);
        });
    }
}
