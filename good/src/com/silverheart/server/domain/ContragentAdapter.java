package com.silverheart.server.domain;

import java.util.ArrayList;
import java.util.List;

import com.silverheart.shared.dto.ContragentDTO;

public class ContragentAdapter {

	public static ContragentDTO toContragentDTO(Contragent cont) {
		ContragentDTO dto = new ContragentDTO();

		dto.setName(cont.getName());
		dto.setAddress(cont.getAddress());
		dto.setPhone(cont.getPhone());

		return dto;
	}
	
	public static  Contragent dtoToContragent(ContragentDTO dto) {
		Contragent contragent = new Contragent();
		contragent.setName(dto.getName());
		contragent.setAddress(dto.getAddress());
		contragent.setPhone(dto.getPhone());
		return contragent;
	}

	public static List<ContragentDTO> asList(List<Object> in) {
		List<ContragentDTO> result = new ArrayList<ContragentDTO>();
		for (Object o : in) {
			result.add(toContragentDTO(((Contragent) o)));
		}
		return result;
	}
}
