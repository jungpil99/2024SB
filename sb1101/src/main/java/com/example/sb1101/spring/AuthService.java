package com.example.sb1101.spring;

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
		if(!member.matchPassword(password)) {
			throw new WrongIdPasswordException();
		}
		
		return new AuthInfo(member.getId(),
				member.getEmail(), 
				member.getName());
	}
}
