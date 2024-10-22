package com.example.sb1021_2.repository;

import com.example.sb1021_2.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
