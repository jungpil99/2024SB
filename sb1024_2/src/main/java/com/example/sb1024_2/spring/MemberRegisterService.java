package com.example.sb1024_2.spring;


import com.example.sb1024_2.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MemberRegisterService {
	@Autowired
	private MemberDao memberDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Long regist(RegisterRequest req) {
		Optional<Member> member = memberDao.selectByUsername(req.getName());
		if (member.isPresent()) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}

		String encodedPassword = passwordEncoder.encode(req.getPassword());

		Member newMember = Member.builder()
				.email(req.getEmail())
				.password(encodedPassword)
				.username(req.getName())
				.regdate(LocalDateTime.now())
				.role("USER")
				.build();
		memberDao.insert(newMember);
		System.out.println("====>" + newMember);
		return newMember.getId();
	}
}
