<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>DropMusic - Player</title>
	<s:include value="../template/scripts.jsp"/>
</head>
<body>
<s:i18n name="en-US">
	<h1>DropMusic - Player</h1>
	<s:include value="../template/header.jsp"/>

	<br>

	<h3><s:text name="listeningTo" /></h3>
	<h5><s:property value="model.dropBoxFileName" /></h5>

	<audio preload="auto" controls="controls" loop="loop" autoplay="autoplay">

		<source src="<s:property value="model.dropBoxPrevUrl" />" type="audio/ogg">

		Your browser does not support the audio element.
	</audio>

	<s:include value="../template/footer.jsp"/>
</s:i18n>
</body>
</html>