package com.example.sb1022secu3.spring;


import com.example.sb1022secu3.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {

	private final MemberDao memberDao;

	@Transactional
	public void changePassword(String username, String oldPwd, String newPwd) {
		Member member = memberDao.selectByUsername(username).get();
		if (member == null)
			throw new MemberNotFoundException();

		member.changePassword(oldPwd, newPwd);

		memberDao.update(member);
	}

}
