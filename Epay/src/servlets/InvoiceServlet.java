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

import dbObjects.Account;
import dbObjects.Document;
import dbObjects.Invoice;
import dbObjects.Paycheck;
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
	private File folder;
	List<FileItem> fileItems;

	public void init() {
		// Get the file location where it would be stored.
		// filePath = getServletContext().getInitParameter("file-upload");
		Map<String, String> env = System.getenv();
		filePath = env.get("CATALINA_HOME") + "/webapps/data/";
		repoPath = env.get("CATALINA_HOME") + "/webapps/repository/";
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			initFileItems(request);
		}

		Invoice invoice = getInvoice(request);

		// Check that we have a file upload request
		if (isMultipart) {
			uploadFile(request, invoice.getIdInvoice());
		}

		// Generate new page
		setAttributes(request, invoice);
		request.getRequestDispatcher("Invoice/Invoice.jsp").forward(request,
				response);
	}

	private void setAttributes(HttpServletRequest request, Invoice invoice) {
		try {
			List<Paycheck> paychecks = invoice.getPaychecks();
			request.setAttribute("invoiceid", invoice.getIdInvoice());
			request.setAttribute("documents", invoice.getDocuments());
			request.setAttribute("paychecks", paychecks);
			User sender = new User(invoice.getSender());
			User reciever = new User(invoice.getReceiver());
			request.setAttribute("senderAccounts", sender.getCompleteAccounts());
			request.setAttribute("recieverAccounts",
					reciever.getCompleteAccounts());
			request.setAttribute("senderId", sender.getIdUser());
			request.setAttribute("senderName", sender.getFullName());
			request.setAttribute("recieverID", reciever.getIdUser());
			request.setAttribute("recieverName", reciever.getFullName());

			// Additional data
			request.setAttribute("senderAddress", sender.getAddress());
			request.setAttribute("senderContact", sender.getContact());
			request.setAttribute("senderEmail", sender.getEmail());
			request.setAttribute("recieverAddress", reciever.getAddress());

			float total = 0F;
			for (Paycheck pc : paychecks) {
				total += pc.getAmount();
			}
			request.setAttribute("total", total);
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
		Object invoiceID = null;
		isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			invoiceID = getInvoiceIdFromMultipart(request);
		} else {
			invoiceID = request.getParameter("invoiceid");
		}
		if (invoiceID == null)
			invoiceID = request.getAttribute("invoiceid");
		Long destinationUser = null;
		Long sourceUser = null;
		if (invoiceID != null) {
			try {
				return new Invoice((long) Double.parseDouble(invoiceID
						.toString()));
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
			sourceUser = Long.parseLong(request.getParameter("sourceUser")
					.toString());
			destinationUser = Long.parseLong(request.getParameter(
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
			return new Invoice(Long.parseLong(invoiceID.toString()),
					sourceUser, destinationUser);
		}
	}

	private void initFileItems(HttpServletRequest request) {
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
			fileItems = upload.parseRequest(request);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Uploads file
	 * 
	 * @param request
	 */
	private void uploadFile(HttpServletRequest request, long invoice) {
		// Check that we have a file upload request
		folder = new File(filePath + "\\" + invoice + "\\");
		folder.mkdir();

		Iterator<FileItem> i = fileItems.iterator();

		while (i.hasNext()) {
			FileItem fi = (FileItem) i.next();
			if (!fi.isFormField()) {
				// Get the uploaded file parameters
				String fileName = fi.getName();
				// Create new file in database
				float fileId = 0F;
				// Write the file
				file = new File(filePath
						+ "\\"
						+ invoice
						+ "\\"
						+ ((long)fileId)
						+ fileName.substring(fileName.indexOf("."),
								fileName.length()));
				try {
					fi.write(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (fi.getFieldName().equals("fileDescription")) {
					System.out.println(fi.getString());
				}
			}
		}
	}

	private Float getInvoiceIdFromMultipart(HttpServletRequest request) {
		for (FileItem item : fileItems) {
			if (item.isFormField()) {
				if (item.getFieldName().equals("invoiceid"))
					return Float.parseFloat(item.getString());
			}
		}
		return null;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		throw new ServletException("GET method used with "
				+ getClass().getName() + ": POST method required.");
	}
}