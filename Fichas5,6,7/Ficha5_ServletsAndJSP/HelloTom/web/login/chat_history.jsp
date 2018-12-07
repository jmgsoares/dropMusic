<%@page import="sd.jspsupport.AddMessage"%>
<% for (String msg : AddMessage.messages) { %>
	<%= msg %><br />
<% } %>