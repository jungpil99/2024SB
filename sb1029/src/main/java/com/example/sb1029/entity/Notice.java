package com.example.sb1029.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name= "notice")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    @ColumnDefault("0")
    private Integer hitCnt;

    private String updatedDatetime;

    private String createdDatetime;

    @Column(columnDefinition = "varchar(2) default 'N'")
    private String deletedYn;

    private String creatorId;
}
