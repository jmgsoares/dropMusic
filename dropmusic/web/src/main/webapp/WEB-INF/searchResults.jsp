<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Search Results</title>
	<s:include value="../template/scripts.jsp"/>
</head>
<body>
<s:i18n name="en-US">
	<h1>Search Results</h1>
	<s:include value="../template/header.jsp"/>
	<table>
		<tr>
			<td>Album</td>
			<td>Artist</td>
			<td>Score</td>
		</tr>
		<s:iterator value="albums">
			<tr>
				<td>
					<a>
						<s:property value="name"/>
					</a>
				</td>
				<td>
					<s:property value="artist.name"/>
				</td>
				<td>
					<s:property value="score"/>
				</td>
			</tr>
		</s:iterator>

	</table>
</s:i18n>
</body>
</html>