package com.example.sb1024_2.config;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Log4j2
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder().encode("1234"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
        log.info("----------filterChain-------------");
        http.authorizeHttpRequests()
//                .antMatchers("/**").denyAll()
//                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/sample/all").permitAll()
//                .antMatchers("/login").permitAll()
                .antMatchers("/sample/admin").hasRole("ADMIN")
                .antMatchers("/register/**").permitAll()
                .antMatchers("/mapper/**").permitAll()
                .antMatchers("/board/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/sample/login") // 로그인 페이지 URL 설정
                .defaultSuccessUrl("/sample/all", true) // 로그인 성공 후 리다이렉트 URL 설정
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout") // 로그아웃 URL 설정
                .logoutSuccessUrl("/sample/login") // 로그아웃 성공 후 리다이렉트 URL 설정
                .invalidateHttpSession(true) // 로그아웃 시 세션 무효화
                .deleteCookies("JSESSIONID") // 로그아웃 시 쿠키 삭제
                .permitAll();

        http.csrf().disable();
//        http.logout();
        http.exceptionHandling()
                .accessDeniedPage("/sample/accessDenied");
        http.csrf()
                .ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().sameOrigin();
        return http.build();
    }
}
