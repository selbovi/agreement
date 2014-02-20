package com.silverheart.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**контрагент*/
@Entity
@Table(name = "CONTRAGENT")
public class Contragent {
	public Contragent(){};
	
	public Contragent(String name2, String phone2, String address2) {
		name = name2;
		phone = phone2;
		address = address2;
	}

	/**наименование*/
	@Id
	@Column(name="name", nullable = false, length=200)
	private String name; 
	/** телефон */ 
	@Column(name="phone", nullable = true, length=20)
	private String phone; 
	/** адрес */ 
	@Column(name="address", nullable = false, length=100)
	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Контрагент " + name + " "+ phone + " " + address;
	}
}
