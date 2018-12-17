<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>File List</title>
	<s:include value="../template/scripts.jsp"/>

</head>
<body>
<s:i18n name="en-US">
	<h1>File List</h1>
	<s:include value="../template/header.jsp"/>

	<h3><s:text name="disclaimer" /></h3>
	<h4><s:text name="dropBoxFolderRestrictions" /></h4>

	<button onclick="$('#remoteFileListDiv').toggle()">Toggle Remote File List</button>

	<div id="remoteFileListDiv">

	<table>
		<s:iterator value="files">
			<tr>
				<td>
					<s:property value="dropBoxFileName" />

				</td>

				<td>
					<s:property value="dropBoxFileId" />

				</td>

				<td>
					<s:property value="dropBoxFilePath" />

				</td>



			</tr>
		</s:iterator>

	</table>

	</div>


	<button onclick="$('#musicListDiv').toggle()">Toggle Music List</button>

	<div id="musicListDiv">

		<table>
			<s:iterator value="musics">
				<tr>
					<td>
						<s:property value="name" />

					</td>

				</tr>
			</s:iterator>

		</table>

	</div>


	<button onclick="$('#userListDiv').toggle()">Toggle User List</button>

	<div id="userListDiv">

		<table>
			<s:iterator value="users">
				<tr>
					<td>
						<s:property value="username" />

					</td>

				</tr>
			</s:iterator>

		</table>

	</div>



	<s:include value="../template/footer.jsp"/>
</s:i18n>
</body>
</html>