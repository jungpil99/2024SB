package com.example.sample.reply.entity;


import com.example.sample.Board.entity.Board;
import com.example.sample.review.entity.Review;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer replyId;

    private String username;

    private String replyContents;

    private String replyDate;

    @ManyToOne
    @JoinColumn(name = "board_idx", nullable = true) // 외래키 설정
    private Board board;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = true) // 외래키 설정
    private Review review;
}
