<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Album List</title>
	<s:include value="../template/scripts.jsp"/>
</head>
<body>
<s:i18n name="en-US">
	<h1>Album List</h1>
	<s:include value="../template/header.jsp"/>

	<br>

	<table>
		<tr>
			<td><s:text name="albumName" /></td>
			<td><s:text name="score" /></td>
		</tr>
		<s:iterator value="albums">
			<tr>
				<td>
					<a href=" <s:url action="readAlbumAction"> <s:param name="model.id" value="id"/> </s:url>">
						<s:property value="name" />
					</a>
				</td>
				<td>
					<s:property value="score" />
				</td>
			</tr>
		</s:iterator>

	</table>
	<s:include value="../template/footer.jsp"/>
</s:i18n>
</body>
</html>