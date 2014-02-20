package com.silverheart.server.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.silverheart.server.dao.AttachedFileDao;
import com.silverheart.server.domain.AttachedFile;

public class GetAttachedFileServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		//http://stackoverflow.com/questions/13725198/how-can-a-user-download-a-file-in-client-side-google-web-toolkit
		String fileId = req.getParameter( "fileId" );

		AttachedFileDao dao = new AttachedFileDao();
		AttachedFile file = dao.retrieveAttachedFile(Long.valueOf(fileId));
		
		byte[] data = file.getData();
		// do not cache
		response.setHeader("Expires", "0");  
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");  
		response.setHeader("Pragma", "public");
		response.setContentLength(data.length);
		// set the filename and the type
		//response.setContentType("application/pdf");
		byte[] fileName = ( "attachment;filename=" + getEncodedFilename(req, file.getName()+ "." + file.getExt())).getBytes();
		response.addHeader("Content-Disposition", new String(fileName));  
		ServletOutputStream out = response.getOutputStream();
		out.write(data);
		out.flush();
	}

	public static String getEncodedFilename(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        if (request.getHeader("User-Agent").indexOf("Opera") != -1 || request.getHeader("User-Agent").indexOf("MSIE") == -1) {
            return new String(fileName.getBytes("UTF-8"), "iso-8859-1"); //mozilla
        } else {
            StringBuilder encoded_filename = new StringBuilder();
            char[] hexdigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'}; //MSIE
            byte[] fileNameBytes = fileName.getBytes("UTF-8");
            for (byte fileNameByte : fileNameBytes) {
                if ((fileNameByte | 0x7F) == 0xFFFFFFFF) {
                    encoded_filename.append('%');
                    encoded_filename.append(hexdigits[(fileNameByte & (15 * 16)) / 16]);
                    encoded_filename.append(hexdigits[fileNameByte & 15]);
                } else {
                    encoded_filename.append((char) fileNameByte);
                }
            }
            return encoded_filename.toString();
        }
    }
}
