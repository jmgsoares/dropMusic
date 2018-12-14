<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Register</title>
	<s:include value="template/scripts.jsp" />
</head>
<body>
<s:i18n name="en-US">
	<h1>Register</h1>
	<s:include value="template/header.jsp" />
	<s:form action="RegisterAction" method="POST">
		<p>
			<s:text name="userName" />
			<s:textfield name="model.username" />
		</p>
		<p>
			<s:text name="passWord" />
			<s:password name="model.password" /><br>
		</p>
		<p><s:submit value="Register"/></p>
	</s:form>
	<a href="<s:url value="login.jsp"/>">LogIn</a>
</s:i18n>
</body>
</html>