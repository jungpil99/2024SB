package com.example.sample.spring;





import com.example.sample.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

	public List<Member> selectByRegdate(LocalDateTime from, LocalDateTime to) {
		List<Member> results = jdbcTemplate.query(
				"select * from MEMBER where REGDATE between ? and ? " +
						"order by REGDATE desc",
				memRowMapper,
				from, to);
		return results;
	}

	private RowMapper<Member> memRowMapper =
			new RowMapper<Member>() {
				@Override
				public Member mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Member member = new Member(rs.getString("EMAIL"),
							rs.getString("NAME"),
							rs.getString("PASSWORD"),
							rs.getTimestamp("REGDATE").toLocalDateTime());
					member.setId(rs.getLong("ID"));
					return member;
				}
			};

	public Member selectById(Long memId) {
		List<Member> results = jdbcTemplate.query(
				"select * from MEMBER where ID = ?",
				memRowMapper, memId);

		return results.isEmpty() ? null : results.get(0);
	}

}
