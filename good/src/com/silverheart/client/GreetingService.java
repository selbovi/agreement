package com.silverheart.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.silverheart.shared.dto.AgreementDTO;
import com.silverheart.shared.dto.AgreementWholeDTO;
import com.silverheart.shared.dto.AttachedFileDTO;
import com.silverheart.shared.dto.CommentaryDTO;
import com.silverheart.shared.dto.ContragentDTO;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	boolean createContragent(ContragentDTO dto);
	List<AttachedFileDTO> getFiles(String id);
	List<AgreementDTO> getAgreements();
	List<ContragentDTO> getContragents();
	AgreementWholeDTO createAgreement(AgreementDTO dto);
	AgreementWholeDTO getAgreement(String id);
	void addComment(CommentaryDTO dto);
	List<CommentaryDTO> getCommentaries(String agreementId);
	void deleteAgreement(String id);
	AgreementWholeDTO updateAgreement(AgreementDTO dto);
}
