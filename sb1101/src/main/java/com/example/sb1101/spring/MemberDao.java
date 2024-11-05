package com.example.sb1101.spring;

import com.example.sb1101.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final MemberRepository repository;


	public Member selectByUsername(String name) {
		return repository.findByName(name);
	}

	public Member selectByEmail(String email) {
		return repository.findByEmail(email);
	}

	public void insert(Member member) {
		repository.save(member);
	}

	public void update(Member member) {
		repository.save(member);
	}

	public List<Member> selectAll() {
		return repository.findAll();
	}

}
