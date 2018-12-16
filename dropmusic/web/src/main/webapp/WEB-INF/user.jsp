<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>User</title>
	<s:include value="../template/scripts.jsp"/>
	<script type="text/javascript" src="template/scripts/toggleDiv.js"></script>
</head>
<body>
<s:i18n name="en-US">
	<h1>User</h1>
	<s:include value="../template/header.jsp"/>

	<p><s:text name="userName" /> : <s:property value="model.userName" /></p>
	<p><s:text name="editorPermissions" /> : <s:property value="model.editor" /> </p>

	<button onclick="toggleDiv()">Change Editor Status</button>

	<br><br>

	<div id="toggleableDiv">

		<s:form action="updateUserAction" method="POST">

			<s:hidden name="model.id" value="%{model.id}" />
			<s:hidden name="model.username" value="%{model.username}" />

			<s:text name="newEditoStatus" /> <s:checkbox name="model.editor" value="model.editor" />

			<p><s:submit/></p>

		</s:form>

	</div>

	<s:include value="../template/footer.jsp"/>
</s:i18n>
</body>
</html>