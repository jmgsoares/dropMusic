<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Simple JSP Calculator (Results)</title>
</head>
<body>
<h2>The operation selected is:</h2>
<%= request.getParameter("op") %>
<br>
<h2>The result of your request is:</h2>
<%
	double result = 0;
	int num1 = Integer.parseInt(request.getParameter("var1")), num2 = Integer.parseInt(request.getParameter("var2"));
	String op = request.getParameter("op");
	if (op.equals("add"))
		result = num1 + num2;
%>
<%= result %>
<br>
<br>
<br>
<b>If you wish to perform another operation please go back the <a href="index.jsp">Simple JSP Calculator</a></b>
<br>
</body>
</html>
