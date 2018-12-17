<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add Music</title>
	<s:include value="../template/scripts.jsp"/>
	<script type="text/javascript" src="template/scripts/addMusics.js"></script>

</head>
<body>
<s:i18n name="en-US">
	<h1>Add Music</h1>
	<s:include value="../template/header.jsp"/>
	<s:form action="addMusicAction" method="POST">
		<p>
			<s:text name="album" />
			<s:select list="albumList" listKey="id" listValue="name" name="model.albumId" />
		</p>
		<p>
			<s:text name="musicName"/>
			<s:textfield name="model.name"/>
		</p>

		<p><s:submit value="Add music"/></p>
	</s:form>

	<s:include value="../template/footer.jsp"/>
</s:i18n>
</body>
</html>