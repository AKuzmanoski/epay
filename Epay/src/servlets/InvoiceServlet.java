package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

import java.io.*;
import java.util.*;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class InvoiceServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isMultipart;
	private String filePath;
	private String repoPath;
	private int maxFileSize = 50 * 1024;
	private int maxMemSize = 4 * 1024;
	private File file;

	public void init() {
		// Get the file location where it would be stored.
		// filePath = getServletContext().getInitParameter("file-upload");
		Map<String, String> env = System.getenv();
		filePath = env.get("CATALINA_HOME") + "/webapps/data/";
		repoPath = env.get("CATALINA_HOME") + "/webapps/repository/";
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		Long invoiceID = Long.parseLong(request.getParameter("invoiceid"));
		Long destinationAccount = null;
		Long destinationUser = null;
		Long sourceAccount = null;
		Long sourceUser = null;
		if (invoiceID == null) {
			// The invoice is not created, so this is create step
			sourceUser = Long.parseLong(request.getAttribute("sourceUser").toString());
			sourceAccount = Long.parseLong(request.getAttribute("sourceAccount").toString());
			destinationUser = Long.parseLong(request.getAttribute("destinationUser").toString());
			destinationAccount = Long.parseLong(request.getAttribute("destinationAccount").toString());
			
		} else {
			// Check that we have a file upload request
			isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				uploadFile(request, invoiceID);
			}
		}
	}
	
	/**
	 * Uploads file 
	 * @param request
	 */
	private void uploadFile(HttpServletRequest request, long invoice) {
		// Check that we have a file upload request
				
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// maximum size that will be stored in memory
				factory.setSizeThreshold(maxMemSize);
				// Location to save data that is larger than maxMemSize.
				factory.setRepository(new File(repoPath));

				// Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload(factory);
				// maximum file size to be uploaded.
				upload.setSizeMax(maxFileSize);

				try {
					// Parse the request to get file items.
					List<FileItem> fileItems = upload.parseRequest(request);

					// Process the uploaded file items
					Iterator<FileItem> i = fileItems.iterator();

					while (i.hasNext()) {
						FileItem fi = (FileItem) i.next();
						if (!fi.isFormField()) {
							// Get the uploaded file parameters
							String fieldName = fi.getFieldName();
							String fileName = fi.getName();
							String contentType = fi.getContentType();
							boolean isInMemory = fi.isInMemory();
							long sizeInBytes = fi.getSize();
							// Write the file
							if (fileName.lastIndexOf("\\") >= 0) {
								file = new File(
										filePath + "\\" + invoice
												+ fileName.substring(fileName
														.lastIndexOf("\\")));
							} else {
								file = new File(
										filePath + "\\" + invoice 
												+ fileName.substring(fileName
														.lastIndexOf("\\") + 1));
							}
							fi.write(file);
						}
					}
				} catch (Exception ex) {
					System.out.println(ex);
				}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		throw new ServletException("GET method used with "
				+ getClass().getName() + ": POST method required.");
	}
}