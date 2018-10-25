package com.woongs.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Quiz {
	@Id
	@GeneratedValue
	private Long id;			// 식별 ID
	
	@ManyToOne					// Quiz VO기준에서 User VO는 N:1 관계
	@JoinColumn(foreignKey = @ForeignKey(name="fk_quiz_writer"))
	private User writer;
	
	private String part;		// 파트 (예. Java, Spring, DB...)
	private String question;	// 질문
	@Lob
	private String answer;		// 답변
	private int time;			// 제한시간

	public void setId(Long id) {
		this.id = id;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", writer=" + writer + ", part=" + part + ", question=" + question + ", answer="
				+ answer + ", time=" + time + "]";
	}
}
