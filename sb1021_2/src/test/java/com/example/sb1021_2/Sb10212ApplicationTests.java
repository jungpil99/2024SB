package com.example.sb1021_2;

import com.example.sb1021_2.entity.Member;
import com.example.sb1021_2.repository.MemberRepository;
import com.example.sb1021_2.spring.MemberDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootTest
class Sb10212ApplicationTests {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberDao memberDao;

    @Test
    void test() {
        Member member = Member.builder()
                .name("홍길동")
                .password("1234")
                .email("hong11@hong.co.kr")
                .regdate(LocalDateTime.now())
                .build();
        System.out.println(memberRepository.save(member));

        Member members = memberDao.selectByEmail("hong11@hong.co.kr");
    }

}
