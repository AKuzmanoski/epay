package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.fabric.Response;

import dbObjects.Paycheck;
import dbObjects.User;

/**
 * Servlet implementation class PaidPaycheckServlet
 */
@WebServlet("/PaidPaycheckServlet")
public class PaidPaycheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaidPaycheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			User user =new User(1);
			Paycheck paycheck=new Paycheck(1);
          
			if(paycheck!=null){
				////response.set
				//HttpSession session = request.getSession(true);
				//session.setAttribute("currentSessionUser", user);
				//session.setAttribute("currentSessionUser", user);	
				response.sendRedirect("PaycheckPaid.jsp");
			}
			else {
				response.sendRedirect("Paycheck.jsp");
			}

		}

		catch (Throwable theException) {
			System.out.println(theException);
		}
		
		
	}

}
