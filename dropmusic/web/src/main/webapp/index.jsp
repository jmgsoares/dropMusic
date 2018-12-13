<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>DropBoxRestServiceTester</title>
</head>
<body>
<s:i18n name="en-US">

	<s:form action="loginUserPass" method="POST">
		<p>
			<s:text name="userName" />
			<s:textfield name="username" />
		</p>
		<p>
			<s:text name="passWord" />
			<s:password name="password" /><br>
		</p>
		<p><s:submit /></p>
	</s:form>

	<s:form action="loginDb" method="POST">
		<s:submit value="Login With Dropbox" />
	</s:form>


</s:i18n>
</body>
</html>