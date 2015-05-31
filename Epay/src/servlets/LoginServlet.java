package servlets;

import java.io.IOException;
import java.net.HttpCookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbObjects.Queries;
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
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String username = req.getParameter("username");
			String pass = req.getParameter("pass");
			

			if(Queries.userAuthentication(username, pass)) {
				//succesfull  
				
				User user = new User(username, pass);
				Cookie cookie=new Cookie("user", Long.toString(user.getIdUser()));
				cookie.setMaxAge(48 * 60 * 60);
				resp.addCookie(cookie);
				
				req.getSession().setAttribute("user", Long.toString(user.getIdUser()));

				req.getRequestDispatcher("LoginToHomeServlet").forward(req, resp);

			}
			else {
				resp.sendRedirect("invalidLogin.jsp");
			}
		}

		catch (Throwable theException) {
			System.out.println(theException);
		}
	}
}