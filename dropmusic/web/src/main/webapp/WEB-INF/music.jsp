<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
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

	<p> <a href=" <s:url action="readAlbumAction"> <s:param name="model.id" value="model.albumid"/> </s:url>">View Album</a></p>

	<s:if test="%{#session.user.editor}">

		<button onclick="$('#toggleableAdminDiv').toggle()">Edit Music</button>

		<div id="toggleableAdminDiv">

			<s:form action="updateMusicAction" method="post">
				<s:hidden name="model.id" value="%{model.id}" />

				<p><s:text name="musicName" /> : <s:textfield name="model.name" placeholder="model.name" /></p>

				<s:submit value="Update" />

			</s:form>

		</div>

	</s:if>

</s:i18n>
</body>
</html>