package com.silverheart.shared.dto;

import java.io.Serializable;

public class ContragentDTO implements Serializable {
	private static final long serialVersionUID = 5960149017575367728L;
	private String name;
	/** телефон */
	private String phone;
	/** адрес */
	private String address;

	public ContragentDTO(String name, String phone, String add) {
		setName(name);setPhone(phone);setAddress(add);
	}
	
	public ContragentDTO(){};

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
}
