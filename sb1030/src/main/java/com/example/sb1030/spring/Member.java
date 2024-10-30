package com.example.sb1030.spring;


import com.example.sb1030.spring.WrongIdPasswordException;
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
    private String name;

    @Column(nullable = false)
    private String password;

    @Column
    private LocalDateTime regdate;

    public Member(String email, String name, String password, LocalDateTime regdate) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.regdate = regdate;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!password.equals(oldPassword))
            throw new WrongIdPasswordException();
        this.password = newPassword;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

}
