<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>DropMusic Linked Musics - File</title>
	<s:include value="../template/scripts.jsp"/>

</head>
<body>
<s:i18n name="en-US">
	<h1>DropMusic Linked Musics - File</h1>
	<s:include value="../template/header.jsp"/>

	<table>
		<s:iterator value="files">
			<tr>
				<td>

					<s:property value="musicName" />

				</td>

				<td>
					<a href="<s:url value="%{dropBoxPrevUrl}" />">
					<s:property value="dropBoxFileName" />
					</a>

				</td>

			</tr>
		</s:iterator>

	</table>


	<s:include value="../template/footer.jsp"/>
</s:i18n>
</body>
</html>