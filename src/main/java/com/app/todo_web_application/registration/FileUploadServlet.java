package com.app.todo_web_application.registration;

import java.io.File;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet(urlPatterns="/upload.do", initParams={@WebInitParam(name="basePath", value="/Users/SrikanthReddy")})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
				 maxFileSize = 1024 * 1024 * 10, // 10MB
				 maxRequestSize = 1024 * 1024 * 50) // 50MB
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LoggerFactory.getLogger(FileUploadServlet.class);

	
	private static final String SAVE_DIR = "uploadFiles";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			int fileID=(int) request.getSession().getAttribute("user_id");
			String name=(String) request.getSession().getAttribute("name");
			String fileName=name+"_"+fileID;
			Part filePart = request.getPart("input-file-preview");
			String str = filePart.getContentType();
			LOG.info("doPost : File content type : "+str);

			
			// gets absolute path of the web application
			String appPath = request.getServletContext().getRealPath("");
			
			String savePath = appPath + File.separator + SAVE_DIR;

			System.out.println(savePath);

			// creates the save directory if it does not exists
			File fileSaveDir = new File(savePath);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdir();
			}

			for (Part part : request.getParts()) {
				fileName = fileName+extractFileExtention(part);
				// refines the fileName in case it is an absolute path
				fileName = new File(fileName).getName();
				part.write(savePath + File.separator + fileName);
			}
			LOG.info("doPost : File uploaded sucessfully : "+savePath + File.separator + fileName);
			request.setAttribute("message", "Upload has been done successfully!");
			LOG.info("doPost : Request from FileUploadServlet to accountUpdate.jsp");
			request.getRequestDispatcher("todo_web_application/view/accountUpdate.jsp").forward(request, response);
				
			
			
			
		} catch (Exception e) {
			LOG.info("doPost : File upload failed : "+e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Upload has error");
			LOG.info("doPost : Request from FileUploadServlet to accountUpdate.jsp");
			request.getRequestDispatcher("todo_web_application/view/accountUpdate.jsp").forward(request, response);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	private String extractFileExtention(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				System.out.println("aaaaaa :"+s.substring(s.indexOf(".") , s.length() - 1));
				return s.substring(s.indexOf(".") , s.length() - 1);
			}
		}
		return "";
	}

}
