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

<s:if test="%{#session.logged}">
	<nav>
		<a href="<s:url action="getAccountAction"><s:param name="model.id" value="%{#session.user.id}"/></s:url>">Account</a> |
		<a href="<s:url action="searchAction"/>">Search</a> |
		<a href="<s:url action="userLogoutAction"/>">Logout</a>
		<s:if test="%{#session.user.editor}">
			| <a href="<s:url action="addArtistAction"/>">Add Artist</a> |
			<a href="<s:url action="addAlbumAction"/>">Add Album</a> |
			<a href="<s:url action="listUsersAction"/>">List Users</a>
		</s:if>
	</nav>
</s:if>