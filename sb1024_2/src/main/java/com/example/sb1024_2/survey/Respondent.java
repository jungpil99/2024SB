package com.example.sb1024_2.survey;

import com.example.sb1024_2.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Respondent {

	@Id @GeneratedValue
	@Column(name = "RESPONDENT_ID")
	private int id;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private Member member;

	private int age;
	private String location;

	@Column(nullable = false, columnDefinition = "varchar(255) default 'N'")
	private String answer;

//	public int getAge() {
//		return age;
//	}
//
//	public void setAge(int age) {
//		this.age = age;
//	}
//
//	public String getLocation() {
//		return location;
//	}
//
//	public void setLocation(String location) {
//		this.location = location;
//	}

}
