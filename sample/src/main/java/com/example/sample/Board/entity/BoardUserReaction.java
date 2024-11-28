package com.example.sample.Board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "board_user_reactions")
@Getter
@Setter
public class BoardUserReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "board_idx")
    private Board board;

    @Column(name = "username")
    private String username;

    @Column(name = "reaction_type")
    private String reactionType; // "LIKE" 또는 "DISLIKE"

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
