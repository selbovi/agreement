package com.silverheart.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.silverheart.shared.dto.AgreementDTO;
import com.silverheart.shared.dto.AgreementWholeDTO;
import com.silverheart.shared.dto.AttachedFileDTO;
import com.silverheart.shared.dto.CommentaryDTO;
import com.silverheart.shared.dto.ContragentDTO;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;

	void createContragent(ContragentDTO dto, AsyncCallback<Boolean> callback);

	void getFiles(String id, AsyncCallback<List<AttachedFileDTO>> callback);

	void getAgreements(AsyncCallback<List<AgreementDTO>> callback);

	void createAgreement(AgreementDTO dto, AsyncCallback<AgreementWholeDTO> callback);

	void getAgreement(String id, AsyncCallback<AgreementWholeDTO> callback);

	void getContragents(AsyncCallback<List<ContragentDTO>> callback);

	void addComment(CommentaryDTO dto, AsyncCallback<Void> callback);

	void getCommentaries(String agreementId, AsyncCallback<List<CommentaryDTO>> callback);

	void deleteAgreement(String id, AsyncCallback<Void> callback);

	void updateAgreement(AgreementDTO dto, AsyncCallback<AgreementWholeDTO> callback);

}
