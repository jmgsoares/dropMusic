<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Link File</title>
	<s:include value="../template/scripts.jsp"/>

</head>

<body>

<s:i18n name="en-US">
	<h1>Link File</h1>
	<s:include value="../template/header.jsp"/>

	<h3><s:text name="disclaimer" /></h3>
	<h4><s:text name="dropBoxFolderRestrictions" /></h4>

	<s:form action="linkFileAction" method="POST">


		<p> <s:text name="dropBoxFile" />
			<s:select list="remoteFiles" listKey="dropBoxFileId" listValue="dropBoxFileName" name="model.dropBoxFileId" /></p>

		<p> <s:text name="localMusic" />
			<s:select list="musics" listKey="id" listValue="name" name="model.musicId"  /></p>

		<s:submit value="Link File" />

	</s:form>

	<s:include value="../template/footer.jsp"/>
</s:i18n>

</body>
</html>