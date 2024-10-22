package com.example.sb1022secu3.spring;


import com.example.sb1022secu3.entity.Member;
import com.example.sb1022secu3.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
