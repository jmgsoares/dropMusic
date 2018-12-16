<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Music</title>
	<s:include value="../template/scripts.jsp"/>
</head>
<body>
<s:i18n name="en-US">
	<h1>Music</h1>
	<s:include value="../template/header.jsp"/>

	<p><s:text name="musicName" /> : <s:property value="model.name" /></p>

</s:i18n>
</body>
</html>