package com.example.sample.spring;


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

    @Getter
    @Column
    private LocalDateTime regdate;

    @Column
    private String role;

    public Member(String email, String name, String password, LocalDateTime regdate) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.regdate = regdate;

    }

    public Member(String email, String name, String password, LocalDateTime regdate,String role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.regdate = regdate;
        this.role = role;
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
