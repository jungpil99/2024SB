package com.example.sb1030.repository;



import com.example.sb1030.spring.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public  Member findByName(String name);

    public Member findByEmail(String email);
}
