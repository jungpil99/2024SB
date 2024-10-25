package com.example.sb1024_2.survey;

import com.example.sb1024_2.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnsweredData {

	@Id @GeneratedValue
	@Column(name = "MEMBER_ID")
	private int id;

	@ElementCollection
	@CollectionTable(
			name = "responses",
			joinColumns = @JoinColumn(name = "MEMBER_ID")
	)
	@OrderColumn
	@Column(name = "seq")
	private List<String> responses;

	@OneToOne
	@JoinColumn(name = "RESPONDENT_ID")
	private Respondent res;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "USERNAME", referencedColumnName = "username") // Member와의 관계 추가
	private Member member;
//
//	public List<String> getResponses() {
//		return responses;
//	}
//
//	public void setResponses(List<String> responses) {
//		this.responses = responses;
//	}
//
//	public Respondent getRes() {
//		return res;
//	}
//
//	public void setRes(Respondent res) {
//		this.res = res;
//	}

}
