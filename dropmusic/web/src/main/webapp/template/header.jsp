<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<s:if test="%{#session.logged}">
<p><s:text name="loggedAs" /> <s:property value="%{#session.user.username}" /></p>

	<nav>
		<a href="<s:url action="getAccountAction"><s:param name="model.id" value="%{#session.user.id}"/></s:url>">Account</a> |
		<a href="<s:url action="searchAction"/>">Search</a> |
		<a href="<s:url action="listArtistsAction"/>">List Artists</a> |
		<a href="<s:url action="listAlbumsAction"/>">List Albums</a> |
		<a href="<s:url action="listMusicsAction"/>">List Musics</a> |
		<a href="<s:url action="listRemoteFilesAction"/>">List DropBox Files</a> |
		<a href="<s:url action="linkFileAction"/>">Link Music to DropBox File</a> |
		<a href="<s:url action="listLocalFilesAction"/>">List Linked Files</a> |
		<a href="<s:url action="userLogoutAction"/>">Logout</a>
	</nav>

	<br>

	<s:if test="%{#session.user.editor}">
	<nav>

		<a href="<s:url action="addArtistAction"/>">Add Artist</a> |
		<a href="<s:url action="addAlbumAction"/>">Add Album</a> |
		<a href="<s:url action="addMusicAction"/>">Add Music</a> |
		<a href="<s:url action="showArtistCleanPage"/>">Clean Artists</a> |
		<a href="<s:url action="listUsersAction"/>">List Users</a>

	</nav>
	</s:if>
<br>
</s:if>

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