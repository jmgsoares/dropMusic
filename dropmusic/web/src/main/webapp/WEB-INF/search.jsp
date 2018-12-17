<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Search</title>
	<s:include value="../template/scripts.jsp" />
</head>
<body>
<s:i18n name="en-US">
	<h1>Search</h1>
	<s:include value="../template/header.jsp" />
	<s:form action="searchAction" method="POST">
		<p>
			<s:text name="search" />
			<s:textfield name="query" />
		</p>

		<p><s:submit value="Search"/></p>
	</s:form>

	<s:include value="../template/footer.jsp"/>
</s:i18n>
</body>
</html>