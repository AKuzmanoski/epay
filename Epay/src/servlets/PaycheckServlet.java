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
		long selectedAccountId=1;
		if(request.getParameter("selectedAccount")!=null){
		 selectedAccountId=Long.parseLong(request.getParameter("selectedAccount"));
		}else if(request.getSession().getAttribute("selectedAccount")!=null){
			 selectedAccountId=Long.parseLong(request.getSession().getAttribute("selectedAccount").toString());
		}else{
			Account account=null;
			try {
				account = user.getCompleteAccounts().get(0);
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
			selectedAccountId=account.getAccountId();
		}
		
		System.out.println("Logged user "+user.getFullName());
	    Paycheck paycheck=null;
	    Invoice invoice=null;
	    Account accountFrom=null;
	    Account accountTo=null;
	    User applicant=null;
	    User receiver=null;
	    String idString=request.getParameter("paycheckSelected");
	    request.setAttribute("createNew", "false");
	    request.setAttribute("createdPaycheckVisible", "visibility:hidden");
	    request.setAttribute("OKbuttonVisible", "visibility:hidden");
	    request.setAttribute("buttonVisible", "visibility:hidden");
	    request.setAttribute("payingMode", "false");
	    request.setAttribute("PayButtonVisible", "visibility:hidden");
	    request.setAttribute("PaidPaycheckVisible", "visibility:hidden"); 
	    request.setAttribute("noMoney", "visibility:hidden"); 
	    
	    if(request.getParameter("paycheckSelected")!=null){
	    	System.out.println("Scenario 1");
	    	//znaci doagja od lista na paycheck i treba da se prikaze popolnetata paycheck
	    	System.out.println("paycheckSelected "+Long.parseLong(request.getParameter("paycheckSelected")));
	    	request.setAttribute("paycheckSelectedPay", idString);
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
	    	
	    	request.setAttribute("applicantName", applicant.getFullName());
	    	request.setAttribute("applicantAddress", applicant.getAddress());
	    	request.setAttribute("applicantBank", accountFrom.getBank());
	    	request.setAttribute("applicantAccount", accountFrom.getCardNumber());
	    	request.setAttribute("applicantEmbg", applicant.getEmbg());
	    	
	    	request.setAttribute("description", paycheck.getDescription());
	    	request.setAttribute("amount", paycheck.getAmount());
	    	request.setAttribute("receiverName", paycheck.getReceiverName());
	    	request.setAttribute("receiverBank", accountTo.getBank());
	    	request.setAttribute("receiverAccount", accountTo.getCardNumber());
	    	
	    	//ako ne e platena i ako doagja od invoice i ako e za mene (ne sum ja pratila jas) treba da ja platam
	    	System.out.println("linija 165 ");
	    	System.out.println("platena "+paycheck.isPaid()+" true="+(applicant.getIdUser()!=user.getIdUser())+" invoiceid="+request.getParameter("invoiceid") );
	    	if(!paycheck.isPaid() && applicant.getIdUser()!=user.getIdUser() && request.getParameter("invoiceid")!=null){
	    	request.setAttribute("payingMode", "true");
	    	request.setAttribute("PayButtonVisible", "");	
	    	}
	    	else{
	    	request.setAttribute("PayButtonVisible", "visibility:hidden");    	
	    	}
	    	RequestDispatcher dispatcher=request.getRequestDispatcher("Paycheck.jsp");
			dispatcher.forward(request, response);
	    	
	    }else if(request.getParameter("payingMode")!=null && request.getParameter("payingMode").equals("true")){
	    	System.out.println("plakjanje");
	    	request.setAttribute("PayButtonVisible", "visibility:hidden");  
	    	
	    	
	    	long idP=Long.parseLong(request.getParameter("paycheckSelectedPay"));
    	    Account account=null;
			try {
				account = new Account(selectedAccountId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			System.out.println("account "+account.getAccountId()+" paycheck "+idP);
			boolean isSuccessfull=false;
    	    try {
				isSuccessfull=account.tryToPay(idP);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("neuspesno plakjanje");
			} 
    	    if(isSuccessfull){
    	    	request.setAttribute("PaidPaycheckVisible", ""); 
    	    }else{
    	    	request.setAttribute("noMoney", ""); 
    	    }
    	    
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
	    	
    		request.setAttribute("OKbuttonVisible", "");
    		long invoiceid=-1;
    		if(request.getAttribute("invoiceid")!=null){
    		 invoiceid=Long.parseLong(request.getAttribute("invoiceid").toString());
    		request.setAttribute("invoiceid", invoiceid);
    		}else if(request.getParameter("invoiceid")!=null){
    			 invoiceid=Long.parseLong(request.getParameter("invoiceid").toString());
        		request.setAttribute("invoiceid", invoiceid);
    		}
    		if(invoiceid==-1){
    			request.setAttribute("backDestination", "LoginToHomeServlet");
    		}else{
    		request.setAttribute("backDestination", "InvoiceServlet");
    		}
    	    RequestDispatcher dispatcher=request.getRequestDispatcher("Paycheck.jsp");
		    dispatcher.forward(request, response);
	    }
	    else if(request.getParameter("accountFrom")!=null && request.getParameter("accountTo")!=null){
	    	System.out.println("Scenario 2");
	    	 request.setAttribute("OKbuttonVisible", "visibility:hidden");
	    	// znaci doagja od invoice i treba da se kreira nova uplatnica i da se dodade vo baza i da se vrati na fakturata so dadeno id
	    	//+da se dodade i invoice paycheck !!!
	    	 // OVDE NE SE PLAKJA!!!!!!
	    	long accountFromId=Long.parseLong(request.getParameter("accountFrom").toString());
	    	long accountToId=Long.parseLong(request.getParameter("accountTo").toString());
	    	request.setAttribute("backDestination", "Invoice/InvoiceServlet");
	        
	    	
	    	try {
				accountFrom=new Account(accountFromId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	try {
				accountTo=new Account(accountToId);
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
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	request.setAttribute("applicantName", applicant.getFullName());
	    	request.setAttribute("applicantAddress", applicant.getAddress());
	    	request.setAttribute("applicantBank", accountFrom.getBank());
	    	request.setAttribute("applicantAccount", accountFrom.getCardNumber());
	    	request.setAttribute("applicantEmbg", applicant.getEmbg());
	    	
	    
	    	request.setAttribute("receiverName",receiver.getFullName());
	    	request.setAttribute("receiverBank", accountTo.getBank());
	    	request.setAttribute("receiverAccount", accountTo.getCardNumber());

	    	request.setAttribute("buttonVisible", "");
	    	request.setAttribute("createInvoice", "true");
	    	long invoiceid=Long.parseLong(request.getParameter("invoiceid"));
	    	System.out.println("invoice id !!! "+invoiceid);
	    	request.setAttribute("invoiceid", invoiceid);
	    	RequestDispatcher dispatcher=request.getRequestDispatcher("Paycheck.jsp");
			dispatcher.forward(request, response);
        	
	    }else if((request.getParameter("createNew")!=null && request.getParameter("createNew").equals("true"))|| 
	    		(request.getParameter("createInvoice")!=null && request.getParameter("createInvoice").equals("true"))){
	    	
	    	//kreirame nova uplatnica
	    	System.out.println("scenario 4");
	    	
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
			long newPaycheckId=-1;
			
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
	    	
	    	//ako ne e invoice, obicna uplatnica, jas ja kreiram, jas ja plakjam
	    	System.out.println("389 ajde "+ (request.getParameter("createNew").toString()));
	    	if((request.getParameter("createNew")!=null && request.getParameter("createNew").equals("true"))){
	    		System.out.println("plakjam uplatnica bez invoice");
	    		boolean isSuccessfull=false;
	    		try {
					isSuccessfull=appAccount.tryToPay(newPaycheckId);
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
	    		if(isSuccessfull){
	    			System.out.println("isSuccessful");
	    			  request.setAttribute("PaidPaycheckVisible", ""); 
		    		    request.setAttribute("noMoney", "visibility:hidden"); 
		    		    try {
		    				newPaycheckId=Queries.insertNewPaycheck(appAccount.getAccountId(), recAccount.getAccountId(), Double.parseDouble(amount), description, receiverName,true);
		    			} catch (Exception e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    				System.out.println("Neuspesno dodavanje na nova uplatnica");
		    			}
	    		}else{
	    			System.out.println("isNOTSuccessful");
	    			request.setAttribute("PaidPaycheckVisible", "visibility:hidden"); 
		    		request.setAttribute("noMoney", ""); 
	    		}
	    		request.setAttribute("createInvoice", "false");
	    		request.setAttribute("backDestination", "LoginToHomeServlet");
	    		 
	    		request.setAttribute("OKbuttonVisible", "");
	    	
	    	}else{
	    		//ako e invoice
	    		System.out.println("kreiram uplatnice so invoice");
	    		long invoiceid=Long.parseLong(request.getParameter("invoiceId").toString());
	    		Paycheck created=null;
	    		 try {
	    				newPaycheckId=Queries.insertNewPaycheck(appAccount.getAccountId(), recAccount.getAccountId(), Double.parseDouble(amount), description, receiverName,false);
	    			} catch (Exception e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    				System.out.println("Neuspesno dodavanje na nova uplatnica");
	    			}
	    		try {
					created=new Paycheck(newPaycheckId);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
	    		
	    		created.setPaid(false);
	    		System.out.println("najnovo - invoice - paycheck "+invoiceid+" "+newPaycheckId);
	    		try {
					Queries.insertNewInvoicePaycheck(invoiceid, newPaycheckId);
				} catch (Exception e) {
					// TODO Auto-generated catch blockz
					System.out.println("Cannot insert new invoice paycheck");
					e.printStackTrace();
				} 
	    		request.setAttribute("createNew", "false");
	    		request.setAttribute("backDestination", "InvoiceServlet");
	    		request.setAttribute("OKbuttonVisible", "");
	    		request.setAttribute("invoiceid", invoiceid);
	    		request.setAttribute("createdPaycheckVisible", "");
	    	}
	    	
	    	request.setAttribute("createNew", "false");
	    	request.setAttribute("createInvoice", "false");
	    	request.setAttribute("buttonVisible", "visibility:hidden");
	    	RequestDispatcher dispatcher=request.getRequestDispatcher("Paycheck.jsp");
			dispatcher.forward(request, response);
	    }
	    else{
	    	//kreiranje na celosno nova uplatnica (popolneti se samo podatocite za userot)
	    	System.out.println("Scenario 5");
	    	Account selectedAccount=null;
	    	//Long accId=Long.parseLong(request.getParameter("selectedAccount"));
	    	
	    	try {
				selectedAccount=new Account(selectedAccountId);
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
	    	request.setAttribute("backDestination", "LoginToHomeServlet");
    		//request.setAttribute("OKbuttonVisible", "");
	    	RequestDispatcher dispatcher=request.getRequestDispatcher("Paycheck.jsp");
			dispatcher.forward(request, response);
	    	
	    }
	    String type=request.getParameter("typeOfItem");
	    System.out.println("idpaycheck "+idString);
	   
	
	}

}
