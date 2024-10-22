package com.example.chap17.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@Data
@ToString
public class Article {

	private int id;
	private int groupId;
	private String sequenceNumber;
	private Date postingDate;
	private int readCount;
	private String writerName;
	private String password;
	private String title;
	private String content;

	public int getLevel() {
		if (sequenceNumber == null) {
			return -1;
		}
		if (sequenceNumber.length() != 16) {
			return -1;
		}
		if (sequenceNumber.endsWith("999999")) {
			return 0;
		}
		if (sequenceNumber.endsWith("9999")) {
			return 1;
		}
		if (sequenceNumber.endsWith("99")) {
			return 2;
		}
		return 3;
	}
	
}
