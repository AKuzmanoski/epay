package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import security.PasswordHash;
import dbObjects.Queries;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pw = response.getWriter();
		DateFormat df = new SimpleDateFormat("dd/mm/yyyy");

		String userName = request.getParameter("username");
		String accountNumber = request.getParameter("accountnumber");
		String password = request.getParameter("password");
		System.out.println("Registration pass: " + password);
		try {
			password = PasswordHash.createHash(request.getParameter("password"));
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Registration hash: " + password);
		String isIndividual = request.getParameter("isIndividual");
		String fullName = null;
		String socialSecurity = null;
		Date dateOfBirth = null;
		if (isIndividual.equals("individual")) {
			fullName = request.getParameter("firstname") + " "
					+ request.getParameter("lastname");
			socialSecurity = request.getParameter("socialsecurity");
			try {
				dateOfBirth = df.parse(request.getParameter("dateofbirth"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				dateOfBirth = null;
			}
		} else {
			fullName = request.getParameter("companyname");
		}
		String email = request.getParameter("email");
		String contactNumber = request.getParameter("contactnumber");
		String address = request.getParameter("address") + "\n"
				+ request.getParameter("city") + "\n"
				+ request.getParameter("country");

		String bank = request.getParameter("bank");
		Date dateFrom = null;
		Date dateTo = null;
		try {
			dateFrom = df.parse(request.getParameter("datefrom"));
			dateTo = df.parse(request.getParameter("dateto"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}

		// Queries
		try {
			if (!Queries.isUsernameFree(userName)) {
				request.setAttribute("ErrorMessage",
						"The username is alredy taken by another user.");
				request.getRequestDispatcher("Registration/NotRegistrated.jsp")
						.forward(request, response);
				return;
			}

			if (Queries.isAccountFree(accountNumber)) {//need to be changed these values [Goran]
				Queries.insertNewAccount(accountNumber, null, null, 0, 0, bank);	
			}

			if (!Queries.isAccountNotInOwnership(accountNumber)) {
				request.setAttribute("ErrorMessage",
						"The Accaunt Number you entered is used by another user.");
				request.getRequestDispatcher("Registration/NotRegistrated.jsp")
						.forward(request, response);
				return;
			}

			int balance = 0, limit = 0;
			Queries.insertNewUser(userName, password, fullName, email,
					contactNumber, new java.sql.Date(dateOfBirth.getTime()),
					address, isIndividual.equals("individual"), socialSecurity,
					accountNumber, balance, limit, bank);
			// LoginToHome
			request.setAttribute("username", userName);
			request.setAttribute("password", request.getParameter("password"));
			request.getRequestDispatcher("LoginServlet").forward(request,
					response);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			pw.println(e.getMessage());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			pw.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			pw.println(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			pw.println(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}