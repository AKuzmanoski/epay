<%@page import="java.util.ArrayList"%>
<%@page import="dbObjects.User"%>
<%@page import="dbObjects.Account"%>
<%@page import="dbObjects.Invoice"%>
<%@page import="dbObjects.Paycheck"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ePay - Home</title>
<link type="text/css" rel="stylesheet" href="jQuery/jquery-ui.min.css"></link>
<link type="text/css" rel="stylesheet" href="MainCSS.css"></link>
<link type="text/css" rel="stylesheet" href="HomePage/HomePage.css"></link>

<script type="text/javascript" src="jQuery/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="jQuery/jquery-ui.min.js"></script>
<script type="text/javascript" src="HomePage/HomePage.js"></script>
<script type="text/javascript" src="MainJavaScript.js"></script>

<style>
#feedback {
	font-size: 1.4em;
}

#listBills .paycheck.ui-selectee {
	background: #FFADAD;
}

#listBills .paycheck.ui-selected {
	background: #FF9999;
	color: white;
}

#listBills .invoice.ui-selecting {
	background: #FFAD5C;
}

#listBills .invoice.ui-selected {
	background: #FF9933, color: white;
}

#listBills {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 40%;
}

#listBills li {
	margin: 3px;
	padding: 0.4em;
	font-size: 1em;
	height: 18px;
}
</style>
</head>
<body>

	<div class="content">
		User
		<c:out value="${userFullName}"></c:out>

		<br /> Select your account:
		<input type="hidden" id="selectedAcc" value="${selectedAccount.getAccountId()}" />
		<form id="dropdownform" action="LoginToHomeServlet" method="post">
			<select id="dropdown" name="dropdown">
				<c:forEach var="entry" items="${accounts}">
					<option value="${entry.getAccountId()}">${entry.getCardNumber()}</option>
				</c:forEach>
			</select>
		</form>
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">Overview</a></li>
				<li><a href="#tabs-2">Invoices</a></li>
				<li><a href="#tabs-3">Paychecks</a></li>
				<li><a href="#tabs-4">Add Account</a>
			</ul>
			<div class="tabs" id="tabs-1">
				<div class="panel">
					<table>
						<tr>
							<td>Balance:</td>
							<td><label class="head">${selectedAccount.getBalance()}</label></td>
						</tr>
						<tr>
							<td>Limit:</td>
							<td><label class="head">${selectedAccount.getLimit()}</label></td>
						</tr>
						<tr>
							<td>Bank:</td>
							<td><b>${selectedAccount.getBank()}</b></td>
						</tr>
						<tr>
							<td>Date From:</td>
							<td><b>${selectedAccount.getDateFrom()}</b></td>
						</tr>
						<tr>
							<td>Date To:</td>
							<td><b>${selectedAccount.getDateTo()}</b></td>
						</tr>
					</table>
				</div>
				<div style="clear: both;"></div>
			</div>
			<div class="tabs" id="tabs-2">
				<div id="receivedInvoices" class="panel">
					<h3>Received Invoices:</h3>
					<form id="invoiceReceivedForm" action="InvoiceServlet"
						method="post">
						<input id="receivedIdInvoice" class="viewState" type="hidden"
							name="invoiceid" />
						<ul id="receivedListInvoices" class="selectable lists">
							<c:forEach items="${receivedInvoices}" var="entry">
								<li id="${entry.getIdInvoice()}" class="ui-widget-content"><img
									class="floats" src="Images/paid.png" alt="isPaid icon" /></img> <img
									class="floats isPaid" ispaid="${entry.isPaid()}"
									src="Images/paid.png" alt="isPaid icon" /><img
									src="Images/invoice.png" alt="Invoice icon" height="30px" /> <br />
									<label class="title">${entry.getIdInvoice()}</label> <br /> <label
									class="title">Amount: </label> <label class="description">${entry.getAmount()}</label>
								</li>
							</c:forEach>
						</ul>
					</form>
				</div>
				<div id="sentInvoices" class="panel">
					<h3>Sent Invoices:</h3>
					<form id="invoiceSentForm" action="InvoiceServlet" method="post">
						<input id="sentIdInvoice" class="viewState" type="hidden"
							name="invoiceid" />
						<ul id="sentListInvoices" class="selectable lists">
							<c:forEach items="${sentInvoices}" var="entry">
								<li id="${entry.getIdInvoice()}" class="ui-widget-content"><img
									class="floats" src="Images/paid.png" alt="isPaid icon" /></img> <img
									class="floats isPaid" ispaid="${entry.isPaid()}"
									src="Images/paid.png" alt="isPaid icon" /><img
									src="Images/invoice.png" alt="Invoice icon" height="30px" /> <br />
									<label class="title">${entry.getIdInvoice()}</label> <br /> <label
									class="title">Amount: </label> <label class="description">${entry.getAmount()}</label>
								</li>
							</c:forEach>
						</ul>
					</form>
				</div>

				<div id="newInvoice" class="panel">
					<h3>New Invoice:</h3>
					<form action="InvoiceServlet" id="usersDropDownForm" method="post">
						<input id="sourceUser" type="hidden" name="sourceUser"
							value="${userid}" /> Send to: <br /> <select
							id="destinationUser" name="destinationUser">
							<c:forEach var="entry" items="${users}">
								<option value="${entry.key}">${entry.value}</option>
							</c:forEach>
						</select> <br /> <br /> <input id="newPaycheck" class="prominent"
							type="submit" value="Create Invoice">
					</form>
				</div>
				<div class="spacer" style="clear: both;"></div>
			</div>

			<div class="tabs" id="tabs-3">
				<div id="receivedPaychecks" class="panel">
					<h3>Received Paychecks:</h3>
					<form id="paycheckReceivedForm" action="PaycheckServlet"
						method="post">
						<input id="receivedIdPaycheck" class="viewState" type="hidden"
							name="paycheckSelected" />
						<ul id="receivedListPaychecks"
							class="selectable receivedPaychecks lists">
							<c:forEach items="${receivedPaychecks}" var="entry">
								<li id="${entry.getIdPaycheck()}" class="ui-widget-content"
									type="paycheckReceived"><img class="floats"
									src="Images/paid.png" alt="isPaid icon" /></img> <img
									class="floats isPaid" ispaid="${entry.isPaid()}"
									src="Images/paid.png" alt="isPaid icon" /><img
									src="Images/paycheck.png" alt="Paycheck icon" height="30px" />
									<br /> <label class="title">${entry.getDescription()}</label>
									<br /> <label class="title">Amount: </label> <label
									class="description">${entry.getAmount()}</label></li>
							</c:forEach>
						</ul>
					</form>
				</div>
				<div id="sentPaychecks" class="panel">
					<h3>Sent Paychecks:</h3>
					<form id="paycheckSentForm" action="PaycheckServlet" method="post">
						<input id="sentIdPaycheck" class="viewState" type="hidden"
							name="paycheckSelected" />
						<ul id="sentListPaychecks" class="selectable lists">
							<c:forEach items="${sentPaychecks}" var="entry">
								<li id="${entry.getIdPaycheck()}" class="ui-widget-content"
									type="paycheckSent"><img class="floats"
									src="Images/paid.png" alt="isPaid icon" /></img> <img
									class="floats isPaid" ispaid="${entry.isPaid()}"
									src="Images/paid.png" alt="isPaid icon" /><img
									src="Images/paycheck.png" alt="Paycheck icon" height="30px" />
									<br /> <label class="title">${entry.getDescription()}</label>
									<br /> <label class="title">Amount: </label> <label
									class="description">${entry.getAmount()}</label></li>
							</c:forEach>
						</ul>
					</form>
				</div>

				<div id="newPaycheck" class="panel">
					<h3>New Paycheck:</h3>
					<form action="PaycheckServlet" method="post">
						<input type="hidden" name="selectedAccount"
							value="${selectedAccount.getAccountId()}" /> <input
							id="newPaycheck1" class="prominent" type="submit"
							value="Create Paycheck" />
					</form>
				</div>
				<div class="spacer" style="clear: both;"></div>
			</div>
			<div class="tabs" id="tabs-4">
				<div id="accountInfo" class="panel">
					<form action="LoginToHomeServlet" name="registration"
						id="registration" method="post">
						<table>
							<tr>
								<td>Bank</td>
								<td><select id="bank" name="bank">
										<option>Stopanska Banka AD</option>
										<option>Tutunska Banka AD</option>
										<option>Ohridska Banka</option>
										<option>Halk Bank</option>
										<option>Sparkase Bank</option>
								</select></td>
							</tr>
							<tr>
								<td>Account Number</td>
								<td><input type="text" id="accountnumber"
									name="accountnumber"
									placeholder="Your unique Bank Account Number" /> <img
									id="accountnumberErr" class="err" src="Images/error.png"
									title="This field is required." /></td>
							</tr>
							<tr>
								<td>Date From</td>
								<td><input type="text" id="datefrom" name="datefrom"
									class="date" placeholder="Date of Issue" /> <img
									id="socialsecurityErr" class="err" src="Images/error.png"
									title="This field is required. Please use standard date format (mm/dd/yyyy) or choose from dropdown chooser." /></td>
							</tr>
							<tr>
								<td>Date From</td>
								<td><input type="text" id="dateto" name="dateto"
									class="date" placeholder="Date of Expiry" /> <img
									id="socialsecurityErr" class="err" src="Images/error.png"
									title="This field is required. Please use standard date format (mm/dd/yyyy) or choose from dropdown chooser." /></td>
							</tr>
						</table>
						<hr />
						<table>
							<tr>
								<td></td>
								<td class="navButtons" id="navFirst"><input type="submit"
									name="sub" id="sub" class="prominent" value="Create Account" /></td>
							</tr>
						</table>
					</form>
				</div>
				<div style="clear: both;"></div>
			</div>
		</div>
	</div>
</body>
</html>