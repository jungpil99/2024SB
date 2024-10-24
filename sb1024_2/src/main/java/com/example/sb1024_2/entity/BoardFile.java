package com.example.sb1024_2.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class BoardFile {

	@Id
	private int idx;
	
	private int boardIdx;
	
	private String originalFileName;
	
	private String storedFilePath;
	
	private long fileSize;
}
