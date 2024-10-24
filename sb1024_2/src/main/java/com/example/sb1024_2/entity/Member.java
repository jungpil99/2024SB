package com.example.sb1024_2.entity;


import com.example.sb1024_2.spring.WrongIdPasswordException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "member")
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private LocalDateTime regdate;

    @Column
    private String role;

    public Member(String email, String username, String password, LocalDateTime regdate) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.regdate = regdate;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!password.equals(oldPassword))
            throw new WrongIdPasswordException();
        this.password = newPassword;
    }

}
