package com.silverheart.server.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.silverheart.shared.dto.AgreementDTO;

/** класс основной договор вокруг которого все крутится */
@Entity
@Table(name = "AGREEMENT")
public class Agreement {
	
	/**конструктор по умолчанию нужен*/
	public Agreement() {
	};
	
	public Agreement(AgreementDTO dto) {
		setId(dto.getId());
		setName(dto.getName());
		setDate(dto.getDate());
		setContragent(dto.getContragent());
		setStage(dto.getStage());
	}

	/**номер договора*/
	@Id
	@Column(name="id", nullable = false, length=20)
	private String id;
	
	/**наименование договора*/
	@Column(name="name", nullable = false, length=200)
	private String name;
	
	/**контрагент*/
	@Column(name="contragent", nullable = false, length=200)
	private String contragent;
	
	/**дата договора*/
	@Column(name = "date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	/**комментарии с историей
	private ArrayList<String> comments;*/
	
	/**стадии договора строкой согласно enum Stages*/
	@Column(name="stage", nullable = false, length=20)
	private String stage;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContragent() {
		return contragent;
	}

	public void setContragent(String contragent) {
		this.contragent = contragent;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	@Override
	public String toString() {
		return "Договор " + id + " " + name + " " + contragent + " " + stage;
	}
	
}

