<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ePay - Income</title>
<link type="text/css" rel="stylesheet" href="Invoice.css"></link>
<link type="text/css" rel="stylesheet"
	href="../jQuery/jquery-ui.min.css"></link>

<script type="text/javascript" src="../jQuery/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../jQuery/jquery-ui.min.js"></script>
<script type="text/javascript" src="Invoice.js"></script>
</head>
<body>
	<div id="content1" class="content">
		<div id="files" class="panel">
			<h3>Add document:</h3>
			<form id="documentForm" action="../InvoiceServlet" method="post"
				enctype="multipart/form-data">
				<table>
					<tr>
						<td>File Name</td>
						<td><input type="text" id="fileName" name="fileName">
						<img id="firstnameErr" class="err" src="Images/error.png" title="This field is required." /></td>
					</tr>
					<tr>
						<td>Description</td>
						<td><textarea id="fileDescription" name="fileDescription"></textarea></td>
					</tr>
					<tr>
						<td>Select file to upload</td>
						<td><input type="file" name="file" id="file" size="100" />
						<img id="firstnameErr" class="err" src="Images/error.png" title="Plese select file." /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Upload File" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>