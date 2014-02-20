package com.silverheart.shared.dto;

import java.io.Serializable;
import java.util.List;

public class AgreementWholeDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private AgreementDTO aDto;
	private List<CommentaryDTO> comments;
	private List<AttachedFileDTO> files;

	public AgreementDTO getaDto() {
		return aDto;
	}
	public void setaDto(AgreementDTO aDto) {
		this.aDto = aDto;
	}
	public List<CommentaryDTO> getComments() {
		return comments;
	}
	public void setComments(List<CommentaryDTO> comments) {
		this.comments = comments;
	}
	public List<AttachedFileDTO> getFiles() {
		return files;
	}
	public void setFiles(List<AttachedFileDTO> files) {
		this.files = files;
	}
	
}
