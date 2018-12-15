<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Album</title>
	<s:include value="../template/scripts.jsp"/>
	<script type="text/javascript" src="template/scripts/toggleReview.js"></script>
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
		<s:property value="model.description" />
	</p>

	<p><s:text name="musics" /></p>
	<table>
		<s:iterator value="model.musics">
			<tr>
				<td>
					<a>
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

	<button onclick="showFunction()">Toggle Review</button>

	<div id="review">

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