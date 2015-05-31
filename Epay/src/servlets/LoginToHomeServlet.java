package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import security.PasswordHash;
//import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
import dbObjects.Account;
import dbObjects.Invoice;
import dbObjects.Paycheck;
import dbObjects.Queries;
import dbObjects.User;

/**
 * Servlet implementation class LoginToHomeServlet
 */
@WebServlet("/LoginToHomeServlet")
public class LoginToHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginToHomeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		long userid = checkUserSign(request, response);
		User user = null;

		try {
			user = new User(userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (request.getParameter("accountnum") != null) {
			// Adding new account
			if(addUserAccount(request, userid, response))
				return;
		}

		List<Account> accounts = null;
		try {
			accounts = user.getCompleteAccounts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String id = null;
		Account selectedAccount = null;
		// ako ima izbrano smetka
		if (request.getParameter("dropdown") != null) {
			id = request.getParameter("dropdown");
			request.getSession().setAttribute("selectedAccount", id);
		} else if (request.getSession().getAttribute("selectedAccount") != null) {
			id = request.getSession().getAttribute("selectedAccount")
					.toString();
		} else {
			id = accounts.get(0).getAccountId() + "";
			request.getSession().setAttribute("selectedAccount", id);
		}
		request.getSession().setMaxInactiveInterval(10000);

		try {
			selectedAccount = new Account(Long.parseLong(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("sa" + selectedAccount.toString());
		List<Paycheck> receivedPaychecks = null;
		try {
			receivedPaychecks = selectedAccount.getReceivedPaychecks();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("r" + receivedPaychecks.size());
		List<Paycheck> sentPaychecks = null;
		try {
			sentPaychecks = selectedAccount.getSentPaychecks();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<User> useri = null;
		try {
			useri = Queries.getAllUsers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<Long, String> users = new HashMap<Long, String>();
		for (User user2 : useri) {
			if (user.getIdUser() != user2.getIdUser()) {
				users.put(user2.getIdUser(),
						user2.getFullName() + "(" + user2.getUserName() + ")");
			}
		}

		List<Invoice> sentInvoices = null;
		List<Invoice> receivedInvoices = null;
		try {
			sentInvoices = user.getSentInvoices();
			receivedInvoices = user.getReceivedInvoices();
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
		}

		System.out.println("s" + sentPaychecks.size());
		request.setAttribute("users", users);
		request.setAttribute("userid", user.getIdUser());
		request.setAttribute("userFullName", user.getFullName());
		request.setAttribute("accounts", accounts);
		request.setAttribute("sentPaychecks", sentPaychecks);
		request.setAttribute("receivedPaychecks", receivedPaychecks);
		request.setAttribute("sentInvoices", sentInvoices);
		request.setAttribute("receivedInvoices", receivedInvoices);
		request.setAttribute("selectedAccount", selectedAccount);
		System.out.println("sending");
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("HomePage/userHomePage.jsp");
		dispatcher.forward(request, response);

	}

	private long checkUserSign(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		long userid = -1;
		// prvo baraj go vo session
		userid = Long.parseLong(request.getSession().getAttribute("user")
				.toString());
		// ako nema baraj go vo cookie
		if (userid == -1) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie cookie = cookies[i];
					if (cookie.getName().equals("user")) {
						userid = Long.parseLong(cookie.getValue());
					}
				}
			}
		}

		if (userid == -1)
			response.sendRedirect("loginPage.jsp");
		return userid;
	}

	private boolean addUserAccount(HttpServletRequest request, Long userId,
			HttpServletResponse response) throws IOException, ServletException {
		DateFormat df = new SimpleDateFormat("dd/mm/yyyy");

		String accountNumber = request.getParameter("accountnum");

		String bank = request.getParameter("bank");
		Date dateFrom = null;
		Date dateTo = null;
		try {
			dateFrom = df.parse(request.getParameter("datefrom"));
			dateTo = df.parse(request.getParameter("dateto"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long idAccount = -1;
		// Queries
		try {

			if (Queries.isAccountFree(accountNumber)) {// need to be changed
														// these values [Goran]
				idAccount = Queries.insertNewAccount(accountNumber, new java.sql.Date(dateFrom.getTime()), new java.sql.Date(dateTo.getTime()), 0, 0, bank);
			} else {
				Account acc = new Account(accountNumber);
				idAccount = acc.getAccountId();
			}

			if (!Queries.isAccountNotInOwnership(accountNumber)) {
				request.setAttribute("ErrorMessage",
						"The Accaunt Number you entered is used by another user.");
				request.getRequestDispatcher("Registration/NotRegistrated.jsp")
						.forward(request, response);
				return true;
			}
			
			Queries.insertNewUserAccount(userId, idAccount, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
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
		}
		return false;
	}
}
