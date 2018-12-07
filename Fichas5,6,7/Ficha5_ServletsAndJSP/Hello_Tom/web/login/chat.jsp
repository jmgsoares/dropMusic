<jsp:include page="auth_verification.jsp"></jsp:include>

<div style="height: 500px; width: 500px; overflow-y: scroll" id="chat"></div>

<b><%= session.getAttribute("user") %></b> says: <input id="what" /> <input type="button" value="OK" id="button" />

<script src="../js/jquery.js"></script>
<script>

$("#button").click(function () {
	data = { 'what': $("#what").val(), 'who': "<%= session.getAttribute("user") %>" };
	$.post("AddMessage", data, function(w) {
		$("#what").val("");
	});
});

function update() {
	$.get("chat_history.jsp", function(data) {
    	$("#chat").html(data);
		window.setTimeout(update, 1000);
	});
}
update();
</script>