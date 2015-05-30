<%@page import="java.util.ArrayList"%>
<%@page import="dbObjects.Account"%>
<%@page import="dbObjects.Document"%>
<%@page import="dbObjects.Paycheck"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ePay - Income</title>

<link type="text/css" rel="stylesheet" href="Invoice/Invoice.css"></link>
<link type="text/css" rel="stylesheet" href="jQuery/jquery-ui.min.css"></link>

<script type="text/javascript" src="jQuery/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="jQuery/jquery-ui.min.js"></script>
<script type="text/javascript" src="Invoice/Invoice.js"></script>
</head>
<body>
	<div id="invoiceHeader">
		<div id="senderInfo" class="info">
			Issuer: <b>${senderName}</b><br />
			Address: <b>${senderAddress}</b><br />
			Address: <b>${senderContact}</b><br />
			Address: <b>${senderEmail}</b>
		</div>
		<div id="recieverInfo" class="info">
			Reciever: <b>${recieverName}</b><br />
			Address: <b>${recieverAddress}</b>
		</div>
	</div>
	<hr />
	<div id="content1" class="content">
		<div id="files" class="panel">
			<h3>Documents:</h3>
			<form id="documentListForm" action="InvoiceServlet" method="post"
				target="_blank">
				<input type="text" class="viewState" name="invoiceid"
					value="${invoiceid}" /> 
				<input type="text" class="viewState"
					id="document" name="document" value="${invoiceid}" /> 
				<input type="text" class="viewState" id="operation" name="operation" />
				<ul id="listDocuments" class="lists">
					<c:forEach items="${documents}" var="entry">
						<li id="${entry.getIdDocument()}" class="ui-widget-content"
							type="paycheckSent"><img src="Images/document.png"
							alt="Document icon" height="30px" /> <label class="title">${entry.getTitle()}</label>
							<br /> <label class="description">${entry.getDescription()}</label>
							<br /> <input type="submit" class="download" value="Download" />
							<input type="submit" class="delete" value="Delete" /></li>
					</c:forEach>
				</ul>
			</form>
		</div>

		<div id="fileUpload" class="panel">
			<h3>Add document:</h3>
			<form id="documentForm" action="InvoiceServlet" method="post"
				enctype="multipart/form-data">
				<input type="hidden" class="viewState" name="invoiceid"
					value="${invoiceid}" />
				<table>
					<tr>
						<td>Description</td>
						<td><textarea id="fileDescription" class="prominent"
								name="fileDescription"></textarea></td>
					</tr>
					<tr>
						<td>Select file to upload</td>
						<td><input type="file" class="prominent" name="file"
							id="file" size="100" /> <img id="firstnameErr" class="err"
							src="Images/error.png" title="Plese select file." /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" class="prominent"
							value="Upload File" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="content2" class="content">
		<div id="paychecks" class="panel">
			<h3>Paychecks:</h3>
			<form id="patcheckListForm" action="PaycheckServlet" method="post">
				<input type="text" class="viewState" name="invoiceid"
					value="${invoiceid}" /> <input id="idpaycheck"
					class="viewState" type="hidden" name="paycheckSelected" />
				<ul id="listPaychecks" class="selectable lists">
					<c:forEach items="${paychecks}" var="entry">
						<li id="${entry.getIdPaycheck()}" class="ui-widget-content"
							type="paycheckSent"><img src="Images/paycheck.png"
							alt="Document icon" height="30px" /> <br /> <label
							class="title">${entry.getDescription()}</label> <br /> <label
							class="title">Amount: </label> <label class="description">${entry.getAmount()}</label>
						</li>
					</c:forEach>
				</ul>
			</form>
		</div>
		<div id="paycheckCreate" class="panel">
			<h3>Add Paycheck:</h3>
			<form id="paycheckCreateForm" action="PaycheckServlet" method="post">
				<input type="text" class="viewState" name="invoiceid"
					value="${invoiceid}" />
				<table>
					<tr>
						<td>${senderName}\'saccount</td>
						<td><select class="prominent" id="senderAccounts"
							name="accountFrom">
								<c:forEach var="entry" items="${senderAccounts}">
									<option value="${entry.getAccountId()}">${entry.getCardNumber()}
										(${entry.getBank()})</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td>${recieverName}\'saccount</td>
						<td><select class="prominent" id="recieverAccounts"
							name="accountTo">
								<c:forEach var="entry" items="${recieverAccounts}">
									<option value="${entry.getAccountId()}">${entry.getCardNumber()}
										(${entry.getBank()})</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td></td>
						<td><input class="prominent" type="submit"
							id="paycheckSubmit" value="Add New Paycheck" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="content3" class="content">
		<h3 style="text-align: right;">Total: ${total}</h3>
	</div>
</body>
</html>