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
		<a href="searchAction.action">Search</a> |
		<a href="addArtistAction.action">Add Artist</a> |
		<a href="addAlbumAction.action">Add Album</a> |
		<a href="userLogoutAction.action">Logout</a>
		<s:if test="%{#session.user.editor}">
			| <a href="listUsersAction.action">List Users</a>
		</s:if>
	</nav>
</s:if>