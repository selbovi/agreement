package com.silverheart.server.domain;

import com.silverheart.shared.dto.AgreementDTO;

public class AgreementAdapter {

	public static AgreementDTO toAgreementDTO(Agreement a) {
		AgreementDTO dto = new AgreementDTO();
		dto.setContragent(a.getContragent());
		dto.setDate(a.getDate());
		dto.setId(a.getId());
		dto.setName(a.getName());
		dto.setStage(a.getStage());
		return dto;
	}
	
	public static Agreement toAgreement(AgreementDTO dto){
		Agreement a = new Agreement();
		a.setContragent(dto.getContragent());
		a.setDate(dto.getDate());
		a.setId(dto.getId());
		a.setName(dto.getName());
		a.setStage(dto.getStage());
		return a;
	}
	
}
