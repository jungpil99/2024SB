package com.example.sb1023_3.entity;

import javax.persistence.*;
import java.util.List;

/**
 * AnsweredData 엔티티는 설문 응답 데이터를 저장합니다.
 */
@Entity
@Table(name = "answered_data") // 데이터베이스에서 'answered_data' 테이블에 매핑
public class AnsweredData {

	@Id // 주 키로 사용될 필드를 나타냄
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ID 필드는 자동 증가
	private Long id;

	@ElementCollection // 이 필드는 다른 엔티티가 아닌 기본 타입 리스트를 저장
	@Column(name = "response") // 'responses' 컬렉션의 각 요소를 'response'라는 이름의 컬럼에 매핑
	@CollectionTable(name = "responses", joinColumns = @JoinColumn(name = "answered_data_id")) // 응답을 저장할 'responses' 테이블에 매핑, 'answered_data_id'로 조인
	private List<String> responses; // 설문 질문에 대한 응답 리스트

	@Embedded // Respondent 객체를 임베드하여 AnsweredData 엔티티에 포함
	private Respondent res; // 응답자에 대한 정보

	// ID 필드의 getter
	public Long getId() {
		return id;
	}

	// ID 필드의 setter
	public void setId(Long id) {
		this.id = id;
	}

	// 응답 리스트의 getter
	public List<String> getResponses() {
		return responses;
	}

	// 응답 리스트의 setter
	public void setResponses(List<String> responses) {
		this.responses = responses;
	}

	// 응답자 정보의 getter
	public Respondent getRes() {
		return res;
	}

	// 응답자 정보의 setter
	public void setRes(Respondent res) {
		this.res = res;
	}
}
