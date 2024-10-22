package com.example.sb1022secu3.service;

import com.example.sb1022secu3.entity.Member;
import com.example.sb1022secu3.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserDtailsServiceImpl implements UserDetailsService {

    @Autowired
    MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("=========>사용자: " + username);
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder().encode("1234"))
//                .roles("ADMIN")
//                .build();
//        return user;
//        Member member = Member.builder()
//                .id(1001L)
//                .username("홍박사")
//                .password(passwordEncoder().encode("1234"))
//                .email("hong@aaa.co.kr")
//                .build();
        Optional<Member> member = repository.findByUsername(username);
        return toUserDetails(member.get());
    }

    private PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    private UserDetails toUserDetails(Member member){
        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
//                .authorities(new SimpleGrantedAuthority(member.getRole().toString()))
                .roles(member.getRole())
                .build();
    }

}
