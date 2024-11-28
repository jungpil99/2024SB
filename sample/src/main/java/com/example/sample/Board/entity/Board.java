package com.example.sample.Board.entity;

import com.example.sample.reply.entity.Reply;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name= "t_board")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardIdx;

    private String title;

    private String contents;

    @ColumnDefault("0") //default 0
    private Integer hitCnt;

    private String creatorId;

    private String username;

    private String createdDatetime;

    private String updaterId;

    private String updatedDatetime;

    @Column(columnDefinition = "varchar(2) default 'N'")
    private String deletedYn;

    private Integer replyCnt;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reply> replies;

    @Column(columnDefinition = "integer default 0")
    private Integer likeCnt = 0;

    @Column(columnDefinition = "integer default 0")
    private Integer dislikeCnt = 0;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BoardUserReaction> reactions = new HashSet<>();

    // 좋아요/싫어요 확인 메서드
    public boolean hasUserLiked(String username) {
        return reactions.stream()
                .anyMatch(r -> r.getUsername().equals(username)
                        && "LIKE".equals(r.getReactionType()));
    }

    public boolean hasUserDisliked(String username) {
        return reactions.stream()
                .anyMatch(r -> r.getUsername().equals(username)
                        && "DISLIKE".equals(r.getReactionType()));
    }
}

