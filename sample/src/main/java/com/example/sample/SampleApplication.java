package com.example.sample;

import com.example.sample.Board.entity.Board;
import com.example.sample.Board.repository.BoardRepository;
import com.example.sample.repository.MemberRepository;
import com.example.sample.spring.Member;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableScheduling
public class SampleApplication {

    @Autowired
    BoardRepository repository;

    @Autowired
    MemberRepository memberRepository;

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

    @PostConstruct
    public void init() {

//        IntStream.rangeClosed(1, 100).forEach(i -> {
//            Board board = Board.builder()
//                    .title("제목" + i)
//                    .createdDatetime(LocalDateTime.now().toString().substring(0, 10))
//                    .contents("내용" + i)
//                    .deletedYn("N")
//                    .hitCnt(0)
//                    .likeCnt(0)
//                    .replyCnt(0)
//                    .build();
//            repository.save(board);
//        });

//        String hashedPassword = BCrypt.hashpw("1234", BCrypt.gensalt());
//
//        Member member = Member.builder()
//                .email("admin@admin.com")
//                .name("관리자")
//                .password(hashedPassword)
//                .role("Admin")
//                .regdate(LocalDateTime.now())
//                .build();
//        memberRepository.save(member);

    }


}
