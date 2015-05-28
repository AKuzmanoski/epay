package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
import dbObjects.Account;
import dbObjects.Paycheck;
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
		//ova bi se zemalo od cookie
		
		//account id, card number za accountite na toj user
		try{
			
			User user=new User("angela","angela123");
			System.out.println(user.getFullName());
		Map<String,String> accounts=user.getAccountsCards();
		System.out.println("acc"+accounts.size());
		Account selectedAccount=null;
		//ako ima izbrano smetka
        if(request.getParameter("dropdown")!=null){
        	String id=request.getParameter("dropdown");   
        	selectedAccount=new Account(Long.parseLong(id));
        }
        else{
        	System.out.println("hello");
        	String id="";
        	for (String idd : accounts.keySet()) {
				id=idd;
				break;
			}
        	System.out.println("id"+id);
        	selectedAccount=new Account(Long.parseLong(id));
        }
        System.out.println("sa"+selectedAccount.toString());
        List<Paycheck> receivedPaychecks=selectedAccount.getReceivedPaychecks();
        System.out.println("r"+receivedPaychecks.size());
        List<Paycheck> sentPaychecks=selectedAccount.getSentPaychecks();
     
        System.out.println("s"+sentPaychecks.size());
        request.setAttribute("user", user);
        request.setAttribute("accounts", accounts);
        request.setAttribute("sentPaychecks", sentPaychecks);
        request.setAttribute("receivedPaychecks", receivedPaychecks);
        System.out.println("sending");
        RequestDispatcher dispatcher=request.getRequestDispatcher("HomePage/userHomePage.jsp");
		dispatcher.forward(request, response);
       
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}

	}

}
