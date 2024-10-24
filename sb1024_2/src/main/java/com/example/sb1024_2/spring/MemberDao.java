package com.example.sb1024_2.spring;



import com.example.sb1024_2.entity.Member;
import com.example.sb1024_2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final MemberRepository repository;


	public Optional<Member> selectByUsername(String username) {
		return repository.findByUsername(username);
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
