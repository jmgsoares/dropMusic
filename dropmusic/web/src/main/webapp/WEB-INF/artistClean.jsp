<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Artists Cleanup</title>
	<s:include value="../template/scripts.jsp"/>

</head>

<body>

<s:i18n name="en-US">
	<h1>Artists Cleanup</h1>
	<s:include value="../template/header.jsp"/>

		<p><s:text name="artistCleanWarning" /></p>
		<s:form action="cleanArtistsAction">
			<s:submit value="Clean Artists" />
		</s:form>

	<s:include value="../template/footer.jsp"/>
</s:i18n>

</body>
</html>