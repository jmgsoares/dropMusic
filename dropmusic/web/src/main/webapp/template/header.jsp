<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<s:if test="hasActionErrors()">
	<div class="errorMessage">
		<s:actionerror/>
	</div>
</s:if>

<s:if test="hasActionMessages()">
	<div class="message">
		<s:actionmessage/>
	</div>
</s:if>
