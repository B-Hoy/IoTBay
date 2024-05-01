<%@page import="uts.iotbay.Database"%>
<html>
<head>
<title>JSP Database Testing</title>
<link rel="stylesheet" href="style.css">
</head>
<body bgcolor=white>
<table border="0">
<tr>
<td align=center>
<img src="images/cat.gif">
</td>
<td>
<h1>JSP Database Testing</h1>
This is the output of a JSP page that is supposed to connect to a SQLite database, generate, get and print some driver code
</td>
</tr>
<%
Database db = new Database();
String[][] results = db.driver();
db.disconnect();%>
<table class="test_table">
	<thead><th>Number</th><th>String</th>
	<% for (int i = 0; i < results.length; i++){%>
	<tr><td><%=results[i][0]%></td><td><%=results[i][1]%></td>
	<%}%>
</table>
<%= new String("It Works!") %>
</body>
</html>
