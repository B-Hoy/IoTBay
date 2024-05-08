<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.PeopleData"%>

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
String[][] dbResults = db.driver();
db.disconnect();%>
<table class="test_table">
	<thead>
		<th>Number</th>
		<th>String</th>
	</thead>
	<% for (int i = 0; i < dbResults.length; i++){%>
	<tr>
		<td><%=dbResults[i][0]%></td>
		<td><%=dbResults[i][1]%></td>
	</tr>
	<%}%>
</table>

<br>

<%
PeopleData pd = new PeopleData();
String[][] pdResults = pd.driver();
pd.disconnect();%>
<table class="test_table2">
	<thead>
		<th>Number</th>
		<th>String</th>
	</thead>
	<% for (int i = 0; i < pdResults.length; i++){%>
	<tr>
		<td><%=pdResults[i][0]%></td>
		<td><%=pdResults[i][1]%></td>
	</tr>
	<%}%>
</table>

<%= new String("It Works!") %>
</body>
</html>
