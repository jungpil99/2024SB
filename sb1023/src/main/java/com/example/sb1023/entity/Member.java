package com.example.sb1023.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Member {

    @Id
    @Column(name = "member_id")
    private String id;

    private String username;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username, String id) {
        this.username = username;
        this.id = id;
    }
}
