<%@page import="dbObjects.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home page</title>
</head>
<body>
	<% User user = null;
	System.out.println("Hello");
	try {
		 user = (User) session.getAttribute("currentSessionUser"); }
		catch(Exception ex) {
		System.out.println(user); 
		}%>
	}
	Welcome <%= user %>
</body>
</html>