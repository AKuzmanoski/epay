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

import dbObjects.Invoice;
import dbObjects.Queries;
import dbObjects.User;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/InvoiceServlet")
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
		Invoice invoice = getInvoice(request);
		System.out.println("Tuka sum");

		// Check that we have a file upload request
		isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			uploadFile(request, invoice.getIdInvoice());
		}
		
		// Generate new page
		setAttributes(request, invoice);
		request.getRequestDispatcher("Invoice/Invoice.jsp").forward(
				request, response);
	}
	
	private void setAttributes(HttpServletRequest request, Invoice invoice) {
		Object sourceAccount = request.getAttribute(
				"sourceAccount");
		Object destinationAccount = request.getAttribute(
				"destinationAccount");
		if (sourceAccount == null) {
			sourceAccount = request
					.getParameter("sourceAccount");
			destinationAccount = request
					.getParameter("destinationAccount");
		}
		System.out.println(invoice);
		try {
			request.setAttribute("documents", invoice.getDocuments());
			request.setAttribute("paychecks", invoice.getPaychecks());
			User sender = new User(invoice.getSender());
			User reciever = new User(invoice.getReceiver());
			request.setAttribute("senderAccounts", sender.getAccountsCards());
			request.setAttribute("recieverAccounts", reciever.getAccountsCards());
			request.setAttribute("senderId", sender.getIdUser());
			request.setAttribute("senderName", sender.getFullName());
			request.setAttribute("senderAccount", sourceAccount.toString());
			request.setAttribute("recieverID", reciever.getIdUser());
			request.setAttribute("recieverName", reciever.getFullName());
			request.setAttribute("recieverAccount", destinationAccount.toString());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Invoice getInvoice(HttpServletRequest request) {
		Object invoiceID = request.getParameter("invoiceid");
		if (invoiceID == null)
			invoiceID = request.getAttribute("invoiceid");
		Long destinationUser = null;
		Long sourceUser = null;
		if (invoiceID != null) {
			try {
				return new Invoice(Long.parseLong(invoiceID.toString()));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
			// The invoice is not created, so this is create step
			sourceUser = Long.parseLong(request.getAttribute("sourceUser")
					.toString());
			destinationUser = Long.parseLong(request.getAttribute(
					"destinationUser").toString());
			try {
				invoiceID = Queries.insertNewInvoice(sourceUser,
						destinationUser);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new Invoice(Long.parseLong(invoiceID.toString()), sourceUser, destinationUser);
		}
	}

	/**
	 * Uploads file
	 * 
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
								filePath
										+ "\\"
										+ invoice
										+ fileName.substring(fileName
												.lastIndexOf("\\")));
					} else {
						file = new File(
								filePath
										+ "\\"
										+ invoice
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