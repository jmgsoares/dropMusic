<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Artist</title>
	<s:include value="../template/scripts.jsp"/>
</head>
<body>
<s:i18n name="en-US">
	<h1>Artist</h1>
	<s:include value="../template/header.jsp"/>

	<p><s:text name="artistName" /> : <s:property value="model.name" /></p>

	<p><s:text name="albums" /></p>
	<table>
		<tr>
			<td><s:text name="name" /></td>
			<td><s:text name="score" /></td>
		</tr>
		<s:iterator value="model.albums">
			<tr>
				<td>
					<a href=" <s:url action="readAlbumAction"> <s:param name="model.id" value="id"/> </s:url>">
						<s:property value="name"/>
					</a>
				</td>
				<td>
					<s:property value="score"/>
				</td>
			</tr>
		</s:iterator>

	</table>

	<br>

	<s:if test="%{#session.user.editor}">

		<button onclick="$('#toggleableAdminDiv').toggle()">Edit Artist</button>

		<div id="toggleableAdminDiv">

			<s:form action="updateArtistAction" method="post">

				<s:hidden name="model.id" value="%{model.id}" />

				<p><s:text name="artistName" /> : <s:textfield name="model.name" placeholder="model.name" /></p>

				<s:submit value="Update" />

			</s:form>

		</div>

	</s:if>

	<s:include value="../template/footer.jsp"/>
</s:i18n>
</body>
</html>