<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hey!</title>
</head>
<body>

	<c:choose>
		<c:when test="${session.loggedin == true}">
			<p>Welcome, ${session.username}. Say HEY to someone.</p>
		</c:when>
		<c:otherwise>
			<p>Welcome, anonymous user. Say HEY to someone.</p>
		</c:otherwise>
	</c:choose>

	<c:forEach items="${heyBean.allUsers}" var="value">
		<s:checkbox action="call" name="checkMe" fieldValue="false"/>
		<c:out value="${value}" /><br>
	</c:forEach>

	<s:text name="User that called: " /> <c:out value="${value}" /><br>
	<p><a href="<s:url action="index" />">Say Hey</a></p>

</body>
</html>