package com.silverheart.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class AgreementDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public class AgreementCols{
		
		public static final String ID = "Номер договора";
		public static final String NAME = "Наименование";
		public static final String KONTRAGENT = "Контрагент";
		public static final String STAGE =  "Статус";
		public static final String DATE =  "Дата заключения";
	}

	/**номер договора*/
	private String id;
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
	/**наименование договора*/
	private String name;
	/**контрагент*/
	private String contragent;
	/**дата договора*/
	private Date date;
	/**стадии договора строкой согласно enum Stages*/
	private String stage;
	
	/**стадии договора*/
	public enum Stages{
		NEW("новый"), ACTIVE("в процессе"), FINISHED("завершен");
		private Stages(String stage) {
			this.stage = stage;
		}
		public String stage;
	}

}
