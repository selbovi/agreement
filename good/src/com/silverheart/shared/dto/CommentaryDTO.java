package com.silverheart.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class CommentaryDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String comment;
	private String agreementId;
	public CommentaryDTO(String agreementId, String text, Date date) {
		this.agreementId = agreementId;
		this.comment = text;
		this.date = date;
	}
	public CommentaryDTO() {}
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
	private Date date;
	
	public enum Names {
		DATE("Дата"), HEADER("Комментарии");
		
		private Names(String title) {
			this.title = title;
		}
		
		public String title;
	}
}
