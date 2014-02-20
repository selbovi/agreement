package com.silverheart.server.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Commentaries")
public class Commentaries {
	public Commentaries(){}
	
	@Column(name="agreementId", nullable = false, length=20)
	private String agreementId;
	@Column(name="comment", nullable = false, length=2000)
	private String comment;
	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAgreementId() {
		return agreementId;
	}
	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue
	private Long id;
	
}
