package com.silverheart.server.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/** прикрепленный файл */
@Entity
@Table(name = "ATTACHEDFILE")
public class AttachedFile {
	public AttachedFile() {
	};

	@Column(name = "name", nullable = false, length = 40)
	private String name;
	@Column(name = "ext", nullable = false, length = 20)
	private String ext;
	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@Column(name = "size", nullable = false, length = 15)
	private Long size;

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	/** номер договора */
	@Column(name = "agreementId", nullable = false, length = 20)
	private String agreementId;

	@Id
	@GeneratedValue
	private Long id;

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

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Basic(fetch = FetchType.LAZY)
	@Lob
	@Column(name = "data")
	private byte[] data;

	@Override
	public String toString() {
		return "Файл " + name + "." + ext + " для договора " + agreementId;
	}
}
