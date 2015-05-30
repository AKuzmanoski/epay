package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbObjects.Account;
import dbObjects.Invoice;
import dbObjects.Paycheck;
import dbObjects.Queries;
import dbObjects.User;

/**
 * Servlet implementation class PaycheckServlet
 */
@WebServlet("/PaycheckServlet")
public class PaycheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaycheckServlet() {
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
	    User applicant=null;
	    User receiver=null;
	    String idString=request.getParameter("paycheckSelected");
	    request.setAttribute("createNew", "false");
	    request.setAttribute("createdPaycheckVisible", "visibility:hidden");
	    
	    if(request.getParameter("paycheckSelected")!=null){
	    	System.out.println("Scenario 1");
	    	//znaci doagja od lista na paycheck i treba da se prikaze popolnetata paycheck
	    	System.out.println("paycheckSelected "+Long.parseLong(request.getParameter("paycheckSelected")));
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
	    	try {
				applicant=accountFrom.accountOwner();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	try {
				receiver=accountTo.accountOwner();
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	
	    	request.setAttribute("applicantName", applicant.getFullName());
	    	request.setAttribute("applicantAddress", applicant.getAddress());
	    	request.setAttribute("applicantBank", accountFrom.getBank());
	    	request.setAttribute("applicantAccount", accountFrom.getCardNumber());
	    	request.setAttribute("applicantEmbg", applicant.getEmbg());
	    	
	    	request.setAttribute("description", paycheck.getDescription());
	    	request.setAttribute("amount", paycheck.getAmount());
	    	request.setAttribute("receiverName", receiver.getFullName());
	    	request.setAttribute("receiverBank", accountTo.getBank());
	    	request.setAttribute("receiverAccount", accountTo.getCardNumber());

	    	request.setAttribute("buttonVisible", "visibility:hidden");
	    	
	    	RequestDispatcher dispatcher=request.getRequestDispatcher("Paycheck.jsp");
			dispatcher.forward(request, response);
	    	
	    }else if(request.getParameter("accountFrom")!=null && request.getParameter("accountTo")!=null){
	    	System.out.println("Scenario 2");
	    	// znaci doagja od invoice i treba da se kreira nova uplatnica i da se dodade vo baza i da se vrati na fakturata so dadeno id
	    	//+da se dodade i invoice paycheck !!!
	
	    }else if(request.getParameter("createNew")!=null && request.getParameter("createNew").equals("true")){
	    	//kreirame nova uplatnica
	    	System.out.println("scenario 3");
	    	
	    	String applicantName = request.getParameter("applicantName");
			String applicantAddress = request.getParameter("applicantAddress");
			String applicantBank = request.getParameter("applicantBank");
			String applicantAccount = request.getParameter("applicantAccount");
			String applicantSS=request.getParameter("applicantSS");
			String description=request.getParameter("description");
			
			String receiverName = request.getParameter("receiverName");
			String receiverBank = request.getParameter("receiverBank");
			String receiverAccount = request.getParameter("receiverAccount");
			String amount=request.getParameter("amount");
			
			Account appAccount=null;
			try {
				appAccount=new Account(applicantAccount);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			Account recAccount=null;
			try {
				recAccount=new Account(receiverAccount);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				recAccount=null;
			} 
			System.out.println("rec "+recAccount);
		    long newId=-1;
		    System.out.println(receiverAccount+ " "+receiverBank);
			if(recAccount.getAccountId()==0){
				try {
					newId=Queries.insertNewAccount(receiverAccount, null, null, 0.0, 0, receiverBank);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("neuspesno dodavanje account");
					e.printStackTrace();
				} 
				try {
					recAccount=new Account(receiverAccount);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				} 
			}
			System.out.println(appAccount.getAccountId());
			System.out.println(recAccount.getAccountId());
			try {
				Queries.insertNewPaycheck(appAccount.getAccountId(), recAccount.getAccountId(), Double.parseDouble(amount), description, receiverName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Neuspesno dodavanje na nova uplatnica");
			}
	    	//na kraj
			request.setAttribute("applicantName", applicantName);
	    	request.setAttribute("applicantAddress", applicantAddress);
	    	request.setAttribute("applicantBank", applicantBank);
	    	request.setAttribute("applicantAccount", applicantAccount);
	    	request.setAttribute("applicantEmbg", applicantSS);
	    	
	    	request.setAttribute("description", description);
	    	request.setAttribute("amount", amount);
	    	request.setAttribute("receiverName", receiverName);
	    	request.setAttribute("receiverBank", receiverBank);
	    	request.setAttribute("receiverAccount", receiverAccount);

	    	request.setAttribute("buttonVisible", "visibility:hidden");
	    	request.setAttribute("createdPaycheckVisible", "");
	    	RequestDispatcher dispatcher=request.getRequestDispatcher("Paycheck.jsp");
			dispatcher.forward(request, response);
	    }
	    else{
	    	//kreiranje na celosno nova uplatnica (popolneti se samo podatocite za userot)
	    	System.out.println("Scenario 4");
	    	Account selectedAccount=null;
	    	Long accId=Long.parseLong(request.getParameter("selectedAccount"));
	    	
	    	try {
				selectedAccount=new Account(accId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	System.out.println(user.getFullName());
            request.setAttribute("buttonVisible", "");
	    	request.setAttribute("applicantName", user.getFullName());
	    	request.setAttribute("applicantAddress", user.getAddress());
	    	request.setAttribute("applicantBank", selectedAccount.getBank());
	    	request.setAttribute("applicantAccount", selectedAccount.getCardNumber());
	    	request.setAttribute("applicantEmbg", user.getEmbg());
	    	request.setAttribute("createNew", "true");
	    	RequestDispatcher dispatcher=request.getRequestDispatcher("Paycheck.jsp");
			dispatcher.forward(request, response);
	    	
	    }
	    String type=request.getParameter("typeOfItem");
	    System.out.println("idpaycheck "+idString);
	   
		
		
		/*
		try {
			String applicantName = request.getParameter("applicantName");
			String applicantAddress = request.getParameter("applicantAddress");
			String applicantBank = request.getParameter("applicantBank");
			String applicantAccount = request.getParameter("applicantAccount");
			String applicantSS=request.getParameter("applicantSS");
			String description=request.getParameter("description");
			
			String receiverName = request.getParameter("receiverName");
			String receiverBank = request.getParameter("receiverBank");
			String receiverAccount = request.getParameter("receiverAccount");
			String amount=request.getParameter("amount");
            
			System.out.println(applicantName+" "+applicantAddress+" "+receiverName+" "+amount);

//				HttpSession session = request.getSession(true);
//				session.setAttribute("currentSessionUser", user);
//				response.sendRedirect("userLogged.jsp"); // logged-in page

		}

		catch (Throwable theException) {
			System.out.println(theException);
		}
		*/
	}

}
