<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>DropBox File List</title>
	<s:include value="../template/scripts.jsp"/>

</head>
<body>
<s:i18n name="en-US">
	<h1>DropBox File List</h1>
	<s:include value="../template/header.jsp"/>

	<h3><s:text name="disclaimer" /></h3>
	<h4><s:text name="dropBoxFolderRestrictions" /></h4>

	<table>
		<tr>
			<td>
				<s:text name="fileName" />
			</td>
			<td>
				<s:text name="remoteFilePath" />
			</td>
		</tr>
		<s:iterator value="files">
			<tr>
				<td>
					<s:property value="dropBoxFileName" />

				</td>

				<td>
					<s:property value="dropBoxFilePath" />

				</td>

			</tr>
		</s:iterator>

	</table>

	<s:include value="../template/footer.jsp"/>
</s:i18n>
</body>
</html>