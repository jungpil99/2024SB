package com.example.sample.Board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private Integer boardIdx;
    private String viewDate;


    public BoardView(String name, Integer boardIdx, String viewDate) {
        this.username = name;
        this.boardIdx = boardIdx;
        this.viewDate = viewDate;
    }
}
