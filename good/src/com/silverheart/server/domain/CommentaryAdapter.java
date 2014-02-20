package com.silverheart.server.domain;

import com.silverheart.shared.dto.CommentaryDTO;

public class CommentaryAdapter {

	public static Commentaries toCommentaries(CommentaryDTO dto){
		Commentaries com = new Commentaries();
		com.setAgreementId(dto.getAgreementId());
		com.setComment(dto.getComment());
		com.setDate(dto.getDate());
		return com;
	}

	public static CommentaryDTO toDto(Commentaries com) {
		CommentaryDTO dto = new CommentaryDTO();
		dto.setAgreementId(com.getAgreementId());
		dto.setComment(com.getComment());
		dto.setDate(com.getDate());
		return dto;
	}
}
