<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<link rel="shortcut icon" href="Images/logo.ico">
<title>ePay - Login</title>
<link type="text/css" rel="stylesheet"
	href="Registration/Registration.css"></link>
<link type="text/css" rel="stylesheet" href="MainCSS.css"></link>
<link type="text/css" rel="stylesheet" href="jQuery/jquery-ui.min.css"></link>


<script type="text/javascript" src="jQuery/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="jQuery/jquery-ui.min.js"></script>
<script type="text/javascript" src="MainJavaScript.js"></script>
</head>

<body>
	<div class="header">
		<img src="Images/logo.png" />
		<div id="logoutForm">
			<form action="LogoutServlet">
				<input type="hidden" value="Log Out" class="prom" />
			</form>
		</div>
	</div>
	<div id="content" class="content">
		<h3>Log in</h3>
		<form action="LoginServlet" method="post">
			<table>
				<tr>
					<td>Username</td>
					<td><input type="text" name="username" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td></td>
					<td style="padding-left: 245px;"><input class="prom" type="submit" value="Log in"></td>
				</tr>
			</table>
		</form>
		<hr />
	</div>
	<div id="registration" class="content">
		<h3>Registration</h3>
		<form action="Registration/userRegistration.jsp" method="get">
			<table>
				<tr>
					<td style="text-align:center;"><input class="prom" type="submit" value="Register"></td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>