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

import dbObjects.Account;
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
	    Paycheck paycheck=null;
	    Invoice invoice=null;
	    Account accountFrom=null;
	    Account accountTo=null;
	    String idString=request.getParameter("paycheckSelected");
	    String type=request.getParameter("typeOfItem");
	    System.out.println("idpaycheck "+idString);
	    if(type.equals("paycheckSent") || type.equals("paycheckReceived")){
	    	try {
	    		paycheck=new Paycheck(Long.parseLong(idString));
	    	} catch (Exception e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    	long accFromId=paycheck.getAccountFrom();
	    	long accToId=paycheck.getAccountTo();
	    	try {
				accountFrom=new Account(accFromId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	try {
				accountTo=new Account(accToId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	
	    }else{
	    	
	    }
	    System.out.println("aFrm"+accountFrom);
	    System.out.println("aTo"+accountTo);
	    if(type.equals("paycheckSent")){
	    	request.setAttribute("applicantName", user.getFullName());
	    	request.setAttribute("applicantAddress", user.getAddress());
	    	request.setAttribute("applicantEmbg", user.getEmbg());
	    	request.setAttribute("applicantAccount",accountFrom.getCardNumber());
	    	request.setAttribute("receiverName",paycheck.getReceiverName());
	    	request.setAttribute("receiverAccount",accountTo.getCardNumber());
	    	request.setAttribute("description", paycheck.getDescription());
	    	request.setAttribute("amount", paycheck.getAmount());
	    	request.setAttribute("applicantBank", accountFrom.getBank());
	    	request.setAttribute("receiverBank",accountTo.getBank());
	        RequestDispatcher dispatcher=request.getRequestDispatcher("Paycheck.jsp");
	 	    dispatcher.forward(request, response);
	    	
	    }else if(type.equals("paycheckReceived")){
	    	request.setAttribute("receiverName", user.getFullName());
	    
	    
	    	request.setAttribute("applicantAccount",accountFrom.getCardNumber());
	    	request.setAttribute("receiverAccount",accountTo.getCardNumber());
	    	
	    	//request.setAttribute("applicantName",paycheck.getReceiverName());
	    	request.setAttribute("description", paycheck.getDescription());
	    	request.setAttribute("amount", paycheck.getAmount());
	    	
	    	request.setAttribute("applicantBank", accountFrom.getBank());
	    	request.setAttribute("receiverBank",accountTo.getBank());
	        RequestDispatcher dispatcher=request.getRequestDispatcher("Paycheck.jsp");
	 	    dispatcher.forward(request, response);
	    	
	    }else if(type.equals("invoiceSent")){
	    	
	    }else if(type.equals("invoiceReceived")){
	    	
	    }
	    
		
		
	}

}
