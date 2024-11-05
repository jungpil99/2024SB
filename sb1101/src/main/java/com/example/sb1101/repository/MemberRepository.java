package com.example.sb1101.repository;




import com.example.sb1101.spring.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findByName(String name);

    public Member findByEmail(String email);
}
