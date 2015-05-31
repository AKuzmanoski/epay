package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import security.PasswordHash;
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
			// Parametrite se prateni od registrationServlet. NE BRISI
			Object usName = req.getAttribute("username");
			Object password = req.getAttribute("password");
			if (usName == null) {
				// Parametrite se prateni od loginPage.js
				usName = req.getParameter("username");
				password = req.getParameter("password");
			}
			String username = usName.toString();
			String pass = password.toString();
			if(Queries.userAuthentication(username, pass)) {
				System.out.println("Hello");
				User user = new User(username, pass);
				System.out.println("wow");
				//succesfull  
				System.out.println("Hello");
				System.out.println(user);
				
				Cookie cookie=new Cookie("user", Long.toString(user.getIdUser()));
				cookie.setMaxAge(48 * 60 * 60);
				resp.addCookie(cookie);
				
				req.getSession().setAttribute("user", Long.toString(user.getIdUser()));

				req.getRequestDispatcher("LoginToHomeServlet").forward(req, resp);

			}
			else {
				System.out.println("No such user");
				resp.sendRedirect("invalidLogin.jsp");
			}
		}

		catch (Throwable theException) {
			System.out.println("NOOOOOOOOOO");
			theException.printStackTrace();
		}
	}
}