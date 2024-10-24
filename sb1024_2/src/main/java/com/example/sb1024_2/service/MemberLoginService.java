package com.example.sb1024_2.service;

import com.example.sb1024_2.entity.Member;
import com.example.sb1024_2.spring.MemberDao;
import com.example.sb1024_2.spring.MemberNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberLoginService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean login(String username, String password) {
        Optional<Member> memberOtp = memberDao.selectByUsername(username);

        if (memberOtp.isPresent()) {
            Member member = memberOtp.get();

            if (passwordEncoder.matches(password, member.getPassword())) {
                return true;
            }else{
                return false;
            }
        }else{
            throw new MemberNotFoundException();
        }

    }
}
