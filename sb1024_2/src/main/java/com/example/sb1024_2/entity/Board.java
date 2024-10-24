package com.example.sb1024_2.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

//@Entity
@Data
public class Board {

//	@Id
	private Long boardIdx;
	
	private String title;
	
	private String contents;
	
	private int hitCnt;
	
	private String creatorId;
	
	private String createdDatetime;
	
	private String updaterId;
	
	private String updatedDatetime;

	private List<BoardFile> fileList;
}
