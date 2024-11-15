package com.example.sample.repository;

import com.example.sample.spring.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public  Member findByName(String name);

    public Member findByEmail(String email);

    void deleteByEmail(String email);
}
