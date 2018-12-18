<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Album</title>
	<s:include value="../template/scripts.jsp"/>
	<script type="text/javascript" src="template/scripts/liveUpdate.js"></script>

</head>
<body>
<s:i18n name="en-US">
	<h1>Album</h1>
	<s:include value="../template/header.jsp"/>

	<p><s:text name="albumName" /> : <s:property value="model.name" /></p>

	<p><s:text name="artistName" /> :
		<a href=" <s:url action="readArtistAction"> <s:param name="model.id" value="model.artist.id"/> </s:url>">
				<s:property value="model.artist.name" />
		</a>
	</p>

	<p>
		<s:text name="description" /> :
		<s:property value="model.description" />
	</p>

	<p><s:text name="musics" /></p>
	<table>
		<s:iterator value="model.musics">
			<tr>
				<td>

					<a href=" <s:url action="readMusicAction"> <s:param name="model.id" value="id"/> </s:url>">
						<s:property value="name"/>
					</a>
				</td>

			</tr>
		</s:iterator>

	</table>

	<p><s:text name="reviews" /></p>
	<table>
		<tr>
			<td><s:text name="review" /></td>
			<td><s:text name="score" /></td>
		</tr>
		<s:iterator value="model.reviews">
			<tr>
				<td>
					<a>
						<s:property value="review"/>
					</a>
				</td>
				<td>
					<a>
						<s:property value="score"/>
					</a>
				</td>

			</tr>
		</s:iterator>

	</table>

	<br>

	<s:if test="%{#session.user.editor}">

		<button onclick="$('#toggleableAdminDiv').toggle()">Edit Album</button>

		<div id="toggleableAdminDiv">

			<s:form action="updateAlbumAction" method="post">
				<s:hidden name="model.id" value="%{model.id}" />

				<p><s:text name="albumName" /> : <s:textfield name="model.name" placeholder="model.name" /></p>

				<p>
					<s:text name="description" /> : <s:textarea name="model.description" placeholder="model.description"  />
				</p>
				<s:submit value="Update" />

			</s:form>

		</div>

	</s:if>

	<br>

	<button onclick="$('#toggleableDiv').toggle()">Toggle Review</button>

	<div id="toggleableDiv">

	<s:form action="reviewAlbumAction" method="POST">
		<s:hidden name="model.id" value="%{model.id}" />
		<s:hidden name="review.albumId" value="%{model.id}" />
		<p>
			<s:text name="review" />
			<s:textfield name="review.review" />
		</p>
		<p>
			<s:text name="score" />
			<s:textfield name="review.score" /><br>
		</p>
		<p><s:submit value="Submit Review" /></p>
	</s:form>

	</div>

	<s:include value="../template/footer.jsp"/>
</s:i18n>
</body>
</html>