<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>User Login</title>
</head>
<body>

<% if (request.getMethod().equals("POST")) { %>
<h3>Wrong login data.</h3>
<% } %>

<form action="Login" method="post">
Username: <input type="text" name="username" size="10">
<br>
Password: <input type="password" name="password" size="10">
<br>
<input type="submit" value="Login">
</form>
</body>
</html>
