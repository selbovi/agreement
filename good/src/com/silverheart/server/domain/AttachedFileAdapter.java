package com.silverheart.server.domain;

import com.silverheart.shared.dto.AttachedFileDTO;

public class AttachedFileAdapter {
	public static AttachedFileDTO toAttachedFileDTO(AttachedFile file) {
		AttachedFileDTO dto = new AttachedFileDTO();
		dto.setAgreementId(file.getAgreementId());
		dto.setDate(file.getDate());
		dto.setExt(file.getExt());
		dto.setId(file.getId());
		dto.setName(file.getName());
		dto.setSize(file.getSize());
		dto.setHrSize(humanReadableByteCount(file.getSize(), true));
		return dto;
	}

	public static String humanReadableByteCount(long bytes, boolean si) {
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
}
