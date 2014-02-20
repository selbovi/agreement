package com.silverheart.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.silverheart.client.GreetingService;
import com.silverheart.server.dao.AgreementDao;
import com.silverheart.server.dao.AttachedFileDao;
import com.silverheart.server.dao.CommentariesDao;
import com.silverheart.server.dao.ContragentDao;
import com.silverheart.server.domain.Agreement;
import com.silverheart.server.domain.AgreementAdapter;
import com.silverheart.server.domain.AttachedFile;
import com.silverheart.server.domain.AttachedFileAdapter;
import com.silverheart.server.domain.Commentaries;
import com.silverheart.server.domain.CommentaryAdapter;
import com.silverheart.server.domain.ContragentAdapter;
import com.silverheart.shared.FieldVerifier;
import com.silverheart.shared.dto.AgreementDTO;
import com.silverheart.shared.dto.AgreementWholeDTO;
import com.silverheart.shared.dto.AttachedFileDTO;
import com.silverheart.shared.dto.CommentaryDTO;
import com.silverheart.shared.dto.ContragentDTO;
import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back
			// to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		AgreementDao d = new AgreementDao();

		Agreement a = new Agreement();
		double id = Math.random();
		a.setContragent("vasya" + id);
		a.setDate(new Date());
		a.setId("" + id);
		a.setName("договор " + id);
		a.setStage(AgreementDTO.Stages.NEW.stage);

		d.createAgreement(a);

		List<Object> retrieveObjects = d.retrieveObjects(0, 0);
		Agreement aaa = (Agreement) retrieveObjects
				.get(retrieveObjects.size() - 1);

		ContragentDao dao = new ContragentDao();
		List<Object> retrieveObjects2 = dao.retrieveObjects(10, 10);
		return "Hello, " + aaa.getContragent() + "!<br><br>I am running "
				+ retrieveObjects.size() + " " + " contragents "
				+ retrieveObjects2.size()
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public boolean createContragent(ContragentDTO dto) {
		ContragentDao cdao = new ContragentDao();
		return cdao.createContragent(ContragentAdapter.dtoToContragent(dto));

	}

	@Override
	public List<AttachedFileDTO> getFiles(String agreementId) {
		AttachedFileDao dao = new AttachedFileDao();
		List<AttachedFile> retrieveObjects = dao
				.retrieveFilesForAgreement(agreementId);
		ArrayList<AttachedFileDTO> result = new ArrayList<AttachedFileDTO>();
		for (AttachedFile f : retrieveObjects) {
			result.add(AttachedFileAdapter.toAttachedFileDTO(f));
		}
		return result;
	}

	@Override
	public List<AgreementDTO> getAgreements() {
		AgreementDao d = new AgreementDao();
		ArrayList<AgreementDTO> list = new ArrayList<AgreementDTO>();
		List<Object> retrieveObjects = d.retrieveObjects(0, 0);
		for (Object o : retrieveObjects) {
			Agreement a = (Agreement) o;
			list.add(AgreementAdapter.toAgreementDTO(a));
		}

		return list;
	}

	@Override
	public AgreementWholeDTO createAgreement(AgreementDTO dto) {
		AgreementDao d = new AgreementDao();
		Agreement a = new Agreement(dto);
		d.createObject(a);
		
		return getAgreement(dto.getId());
	}

	@Override
	public AgreementWholeDTO getAgreement(String id) {
		AgreementWholeDTO dto = new AgreementWholeDTO();
		AgreementDao dao = new AgreementDao();
		Agreement retrieve = dao.retrieveAgreement(id);

		AgreementDTO agreementDTO = AgreementAdapter.toAgreementDTO(retrieve);

		dto.setaDto(agreementDTO);
		dto.setComments(getCommentaries(id));
		dto.setFiles(getFiles(id));

		return dto;
	}

	@Override
	public List<ContragentDTO> getContragents() {
		ContragentDao dao = new ContragentDao();
		List<Object> retrieveObjects = dao.retrieveObjects(1, 3);
		List<ContragentDTO> asList = ContragentAdapter.asList(retrieveObjects);
		return asList;
	}

	@Override
	public void addComment(CommentaryDTO dto) {
		CommentariesDao dao = new CommentariesDao();
		dao.createCommentaries(CommentaryAdapter.toCommentaries(dto));
	}

	@Override
	public List<CommentaryDTO> getCommentaries(String agreementId) {
		CommentariesDao dao = new CommentariesDao();
		List<Commentaries> retrieveCommentaries = dao
				.retrieveCommentaries(agreementId);
		ArrayList<CommentaryDTO> result = new ArrayList<CommentaryDTO>();
		for (Commentaries c : retrieveCommentaries)
			result.add(CommentaryAdapter.toDto(c));
		return result;
	}

	@Override
	public void deleteAgreement(String id) {
		AgreementDao dao = new AgreementDao();
		dao.deleteAgreement(id);
	}

	@Override
	public AgreementWholeDTO updateAgreement(AgreementDTO dto) {
		AgreementDao dao = new AgreementDao();
		dao.updateAgreement(AgreementAdapter.toAgreement(dto));
		return getAgreement(dto.getId());
	}
}
