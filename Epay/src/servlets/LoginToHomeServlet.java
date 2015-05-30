package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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











//import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
import dbObjects.Account;
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

				
			long userid=-1;
			//prvo baraj go vo session
			userid=Long.parseLong(request.getSession().getAttribute("user").toString());
			//ako nema baraj go vo cookie
			if(userid==-1){
				Cookie[] cookies=request.getCookies();
				if (cookies != null) {
					for (int i=0;i<cookies.length;i++) {
						Cookie cookie=cookies[i];
						if (cookie.getName().equals("user")) {	
						    userid=Long.parseLong(cookie.getValue());
					     }
					}
				}
			}
					
			User user=null;
		   
			try {
				user = new User(userid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    
			System.out.println(user.getFullName());
			Map<String, String> accounts=null;
			try {
				accounts = user.getAccountsCards();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("acc"+accounts.size());
			
			Account selectedAccount=null;
			//ako ima izbrano smetka
		    if(request.getParameter("dropdown")!=null){
		        String id=request.getParameter("dropdown");   
		        try {
					selectedAccount=new Account(Long.parseLong(id));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		    }
		    else{
		        System.out.println("hello");
		        String id="";
		        for (String idd : accounts.keySet()) {
					id=idd;
					break;
				}
		        System.out.println("id"+id);
		        try {
					selectedAccount=new Account(Long.parseLong(id));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		     }
		    System.out.println("sa"+selectedAccount.toString());
		    List<Paycheck> receivedPaychecks=null;
		    try {
				receivedPaychecks=selectedAccount.getReceivedPaychecks();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    System.out.println("r"+receivedPaychecks.size());
		    List<Paycheck> sentPaychecks=null;
		    try {
				sentPaychecks=selectedAccount.getSentPaychecks();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    List<User> useri=null;
		     try {
				useri=Queries.getAllUsers();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    Map<Long,String> users=new HashMap<Long, String>();
		    for (User user2 : useri) {
		    	if(user.getIdUser()!=user2.getIdUser()){
				users.put(user2.getIdUser(), user2.getFullName()+"("+user2.getUserName()+")");
		    	}
			}
		    
		    
		    System.out.println("s"+sentPaychecks.size());
		    request.setAttribute("users", users);
		    request.setAttribute("userid", user.getIdUser());
		    request.setAttribute("userFullName", user.getFullName());
		    request.setAttribute("accounts", accounts);
		    request.setAttribute("sentPaychecks", sentPaychecks);
		    request.setAttribute("receivedPaychecks", receivedPaychecks);
		    System.out.println("sending");
		    RequestDispatcher dispatcher=request.getRequestDispatcher("HomePage/userHomePage.jsp");
		    dispatcher.forward(request, response);
						 
				
	}
}

	
		

