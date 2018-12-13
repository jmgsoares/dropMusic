<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="dropBoxBean" type="pt.onept.sd1819.dropmusic.web.bean.DropBoxBean" scope="session"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<title>AuthCode - Dropbox</title>
</head>
<body>
<c:redirect url="${dropBoxBean.authUrl}" />
</body>
</html>
