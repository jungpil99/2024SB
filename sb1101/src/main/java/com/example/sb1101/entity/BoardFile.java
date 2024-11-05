package com.example.sb1101.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name= "t_file")
public class BoardFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idx;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;

	private String creatorId;
	private LocalDateTime createdDatetime;
	private String updatorId;
	private LocalDateTime updatedDatetime;

	@Column(columnDefinition = "varchar(2) default 'N'")
	private String deletedYn;

	@ManyToOne // Board와의 다대일 관계 설정
	@JoinColumn(name = "board_idx", nullable = false) // 외래 키 설정
	private Board board; // Board 엔티티와의 관계

}
