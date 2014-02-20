package com.silverheart.server.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.silverheart.server.dao.AttachedFileDao;
import com.silverheart.server.domain.AttachedFile;

public class AttachedFileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 4549523473973451764L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (ServletFileUpload.isMultipartContent(req)) {
			String paramId = req.getParameterValues("id")[0];
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List<FileItem> items = upload.parseRequest(req);
				for (FileItem item : items) {
					AttachedFileDao dao = new AttachedFileDao();
					AttachedFile file = new AttachedFile();
					file.setAgreementId(paramId);
					file.setData(item.get());
					file.setDate(new Date());
					file.setSize(item.getSize());
					file.setName(FilenameUtils.getBaseName(item.getName()));
					file.setExt(FilenameUtils.getExtension(item.getName()));
					dao.createAttachedFile(file);
				}
			} catch (FileUploadException e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while creating the file : " + e.getMessage());
			}

		} else {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
					"Request contents type is not supported by the servlet.");
		}
	}
}
