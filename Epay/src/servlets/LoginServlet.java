package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbObjects.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "Home page shown to user", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		try {

			String pass = request.getParameter("pass");
			String username = request.getParameter("username");
			System.out.println(username);
			
			User user = new User(username, pass);
			
			if(user.getPass().equals(pass)) {
				//succesfull  
				HttpSession session = request.getSession(true);
				session.setAttribute("currentSessionUser", user);
//				response.sendRedirect("userHomePage.jsp");
				response.sendRedirect("userLogged.jsp");
			}
			else {
				response.sendRedirect("invalidLogin.jsp");
			}

//			if (user.isValid()) {
//
//				HttpSession session = request.getSession(true);
//				session.setAttribute("currentSessionUser", user);
//				response.sendRedirect("userLogged.jsp"); // logged-in page
//			}
//
//			else
//				response.sendRedirect("invalidLogin.jsp"); // error page
		}

		catch (Throwable theException) {
			System.out.println(theException);
		}
	}
}