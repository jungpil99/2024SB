package com.example.sb1029;

import com.example.sb1029.entity.Notice;
import com.example.sb1029.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class Sb1029Application {

    @Autowired
    NoticeRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Sb1029Application.class, args);
    }


    @PostConstruct
    public void init() {

        IntStream.range(1, 100).forEach(i -> {
            Notice notice = Notice.builder()
                    .title("제목 " + i)
                    .content("내용 " + i)
                    .hitCnt(0)
                    .createdDatetime(LocalDateTime.now().toString().substring(0, 10))
                    .deletedYn("Y")
                    .build();

            repository.save(notice);
        });
    }
}
