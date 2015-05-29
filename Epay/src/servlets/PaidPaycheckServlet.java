package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.fabric.Response;

import dbObjects.Invoice;
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

		long userid=-1;
		Cookie[] cookies=request.getCookies();
		//System.out.println(cookies.length);
		if (cookies != null) {
			for (int i=0;i<cookies.length;i++) {
				Cookie cookie=cookies[i];
				//System.out.println("kuki name-"+cookie.getName());
				if (cookie.getName().equals("user")) {	
					//System.out.println("found");
				    userid=Long.parseLong(cookie.getValue());
			     }
			}
		}
		User user=null;
	    if(userid!=-1){
	      // ako ima id zemi go userot od baza
		try {
			user = new User(userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    }
	    else{
	    	//ako nema zemi cel objekt user od session
	    	user=(User) request.getSession().getAttribute("user");
	    }
	    Paycheck paycheck=null;
	    Invoice invoice=null;
	    String idString=request.getParameter("paycheckSelected");
	    String type=request.getParameter("typeOfItem");
	    System.out.println("idpaycheck "+idString);
	    if(type.equals("paycheckSent") || type.equals("paychecksReceived")){
	    	try {
	    		paycheck=new Paycheck(Long.parseLong(idString));
	    	} catch (Exception e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    }else{
	    	
	    }
	    
	    if(type.equals("paycheckSent")){
	    	request.setAttribute("applicant", user);
	    	request.setAttribute("paycheck", paycheck);
	        RequestDispatcher dispatcher=request.getRequestDispatcher("PaycheckPaid.jsp");
	 	    dispatcher.forward(request, response);
	    	
	    }else if(type.equals("paycheckReceived")){
	    	request.setAttribute("receiver", user);
	    	request.setAttribute("paycheck", paycheck);
	    	RequestDispatcher dispatcher=request.getRequestDispatcher("PaycheckPaid.jsp");
	 	    dispatcher.forward(request, response);
	    	
	    }else if(type.equals("invoiceSent")){
	    	
	    }else if(type.equals("invoiceReceived")){
	    	
	    }
	    
		
		
	}

}
