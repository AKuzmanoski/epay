<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ePay - Paycheck</title>
<link type="text/css" rel="stylesheet" href="MainCSS.css"></link>
<link type="text/css" rel="stylesheet" href="jQuery/jquery-ui.min.css"></link>
<link rel="shortcut icon" href="Images/logo.ico">

<script type="text/javascript" src="jQuery/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="jQuery/jquery-ui.min.js"></script>
<script type="text/javascript" src="MainJavaScript.js"></script>
<script type="text/javascript" src="Paycheck.js"></script>
<style type="text/css">
.body {
	margin-left: 100px;
	margin-top: 50px;
	height: 320px;
	width: 600px;
	font-family: sans-serif;
}

label {
	display: block;
	width: 200px;
	margin-left: 5px;
	margin-right: 5px;
	float: left;
}

input {
	width: 270px;
	margin-left: 5px;
	margin-right: 5px;
	float: left;
}

.left, .right {
	padding-left: 5px;
	padding-right: 5px;
	align: center;
	height: 350px;
	background-color: pink;
	font: black;
	width: 280px;
	float: left
}

p, h3 {
	text-align: right;
	margin-right: 10px;
}

#btnPay {
	width: 70px;
	margin: 50px;
	align: middle;
}
</style>
</head>
<body>
	<div class="header">
		<a href="LoginToHomeServlet"><img src="Images/logo.png" /></a>
		<div id="logoutForm">
			<form action="LogoutServlet">
				<input type="submit" value="Log Out" class="prom" />
			</form>
		</div>
	</div>
	<div class="content">
		<form action="PaycheckServlet" method="post">
			<div class="body">
				<div class='left'>
					<br />
					<br />
					<h3>Applicant</h3>
					<label>Applicant name</label> <input type=text name="applicantName"
						value="<c:out value="${applicantName}"></c:out>" /> <label>Applicant
						address</label> <input type=text name="applicantAddress"
						value="<c:out value="${applicantAddress}"></c:out>" /> <label>Applicant
						bank</label> <input type=text name="applicantBank"
						value="<c:out value="${applicantBank}"></c:out>" /> <label>Applicant
						account</label> <input type=text name="applicantAccount"
						value="<c:out value="${applicantAccount}"></c:out>" /> <label>Applicant
						social security</label> <input type=text name="applicantSS"
						value="<c:out value="${applicantEmbg}"></c:out>" /> <label>Description</label>
					<input type=text name="description"
						value="<c:out value="${description}"></c:out>" />
				</div>
				<div class='right'>
					<p>Public revenue</p>
					<h3>Receiver</h3>
					<label>Receiver name</label> <input type=text name="receiverName"
						value="<c:out value="${receiverName}"></c:out>" /> <label>Receiver
						bank</label> <input type=text name="receiverBank"
						value="<c:out value="${receiverBank}"></c:out>" /> <label>Receiver
						account</label> <input type=text name="receiverAccount"
						value="<c:out value="${receiverAccount}"></c:out>" /> <label>Amount</label>
					<input type=text name="amount"
						value="<c:out value="${amount}"></c:out>" /> <br />
					<br /> <label>PP50</label>
				</div>
				<br /> <input type="hidden" id="createInvoice" name="createInvoice"
					value="${createInvoice}"> <input type="hidden"
					id="createNew" name="createNew" value="${createNew}"> <label
					style="${createdPaycheckVisible}">Your paycheck has been
					created</label> <label style="${PaidPaycheckVisible}">Your paycheck
					has been successfuly paid</label> <input id="btnCreate" type="submit"
					value="CREATE" style="${buttonVisible}" /> <input id="btnPay"
					type="submit" value="PAY" style="${PayButtonVisible}" /> <input
					type="hidden" name="invoiceId" value="${invoiceid}"> <input
					type="hidden" name="payingMode" value="${payingMode}"> <input
					type="hidden" name="paycheckSelectedPay"
					value="${paycheckSelectedPay}">
			</div>

		</form>
		<form action="${backDestination}" method=post>
			<input type="hidden" name="invoiceid" value="${invoiceid}"> <input
				type="submit" style="${OKbuttonVisible}" value="OK">
		</form>


	</div>
</body>
</html>