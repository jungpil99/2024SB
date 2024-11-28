package com.example.sample.spring;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemberRegisterService {
	@Autowired
	private MemberDao memberDao;

	public Long regist(RegisterRequest req) {
		Member member = memberDao.selectByUsername(req.getName());
		if (member != null) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}

		String hashedPassword = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());

		Member newMember = new Member(
				req.getEmail(), req.getName(), hashedPassword,
				LocalDateTime.now(), "User");
		memberDao.insert(newMember);
		return newMember.getId();
	}
}
