package com.silverheart.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class AttachedFileDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String ext;
	private Date date;
	private Long size;
	private String hrSize;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
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

	public String getHrSize() {
		return hrSize;
	}

	public void setHrSize(String hrSize) {
		this.hrSize = hrSize;
	}

	private String agreementId;
	private Long id;
	

}
