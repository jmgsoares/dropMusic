<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Simple JSP Calculator</title>
</head>
<body>
<form action="math.jsp" method="get">
Operand 1: <input type="text" name="var1" size="5">
<br>
Operand 2: <input type="text" name="var2" size="5">
<br>
Operation:  <select name="op">
<option value="add">+</option>
<!-- ADD MORE OPTIONS HERE -->
</select>
<br>
<input type="submit" value="Do the Math!">
</form>
</body>
</html>
