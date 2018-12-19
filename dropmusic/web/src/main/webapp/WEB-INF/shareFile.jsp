<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Share File</title>
	<s:include value="../template/scripts.jsp"/>

</head>

<body>

<s:i18n name="en-US">
	<h1>Share File</h1>
	<s:include value="../template/header.jsp"/>

	<h3><s:text name="disclaimer" /></h3>
	<h4><s:text name="dropBoxFolderRestrictions" /></h4>

	<s:form action="shareFileAction" method="POST">

		<p><s:text name="dropBoxFile" /> <s:select list="localFiles" listKey="id" listValue="dropBoxFileName" name="model.id" /></p>

		<p><s:text name="user" /> <s:select list="users" listKey="id" listValue="username" name="targetUser.id"  /></p>

		<s:submit value="Share File" />

	</s:form>

	<s:include value="../template/footer.jsp"/>
</s:i18n>

</body>
</html>