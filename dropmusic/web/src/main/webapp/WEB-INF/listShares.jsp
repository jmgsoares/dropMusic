<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>File Shared</title>
	<s:include value="../template/scripts.jsp"/>

</head>
<body>
<s:i18n name="en-US">
	<h1>Files Shared With You</h1>
	<s:include value="../template/header.jsp"/>

	<table>
		<s:iterator value="files">
			<tr>

				<td>
					<a href="<s:url value="%{dropBoxPrevUrl}" />">
						<s:property value="dropBoxFileName" />
					</a>
				</td>
				<td>

					<a href=" <s:url action="readMusicAction"> <s:param name="model.id" value="id"/> </s:url>">
						<s:text name="linkedMusic" /></a>
				</td>
			</tr>
		</s:iterator>

	</table>

	<s:include value="../template/footer.jsp"/>
</s:i18n>
</body>
</html>