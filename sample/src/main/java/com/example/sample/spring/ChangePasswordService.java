package com.example.sample.spring;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {

	private final MemberDao memberDao;

	@Transactional
	public void changePassword(String email, String oldPwd, String newPwd) {
		Member member = memberDao.selectByEmail(email);
		if (member == null) {
			throw new MemberNotFoundException();
		}

		if (!BCrypt.checkpw(oldPwd, member.getPassword())) {
			throw new WrongIdPasswordException();
		}

		String hashedNewPassword = BCrypt.hashpw(newPwd, BCrypt.gensalt());
		member.setPassword(hashedNewPassword);
		memberDao.update(member);
	}

}
