package com.example.sample.spring;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	MemberDao memberDao;

	public AuthInfo authenticate(String email, String password) {
		Member member = memberDao.selectByEmail(email);
		if(member == null) {
			throw new WrongIdPasswordException();
		}
		// BCrypt로 비밀번호 검증
		if(!BCrypt.checkpw(password, member.getPassword())) {
			throw new WrongIdPasswordException();
		}

		return new AuthInfo(
				member.getId(),
				member.getEmail(),
				member.getName(),
				member.getRole()
		);
	}
}
