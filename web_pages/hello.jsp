<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.UserLogEntry"%>
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
</table>
<%
// This check is *required* to use the db, otherwise data isn't fully persistent
Database db = (Database)application.getAttribute("database"); 		// enter into every class
if (db == null){
	db = new Database();
	application.setAttribute("database", db);
	%>
	Had to make a new db :(
	<%
}

String form_email = request.getParameter("email");
String form_first_name = request.getParameter("first_name");
String form_last_name = request.getParameter("last_name");
String form_password = request.getParameter("password");
String form_card_num = request.getParameter("card_num");
String form_card_exp = request.getParameter("card_exp");
String form_phone_num = request.getParameter("phone_num");
String form_type = request.getParameter("form_type");
if (form_type != null){ // if we got here through a form
	%><%= form_type %><%
	switch (form_type){
		case "insert":
			db.create_user(form_email, form_first_name, form_last_name, form_password, false, form_card_num, form_card_exp, form_phone_num); // make user
			break;
		case "update":
			db.update_user(form_email, form_first_name, form_last_name, form_password, form_card_num, form_card_exp, form_phone_num);
			break;
		case "delete":
			db.delete_user(form_email);
			break;
		case "login":
			int local_session_id = db.add_user_login(form_email);
			if (local_session_id != -1){
				session.setAttribute("session_id", local_session_id);
			}else{
				// Login failed, reload the page or throw a popup or something
			}
			break;
		case "logout":
			if (session.getAttribute("session_id") != null){ // just to make sure the user is actually logged in
				db.set_user_logout((int)session.getAttribute("session_id"));
				session.removeAttribute("session_id");
			}
			break;
	}
}

User[] users = db.get_all_users();
if (session.getAttribute("session_id") == null){%>
You are not logged in.
<%
}else{%>
	You are logged in as <%=db.get_user_log((int)session.getAttribute("session_id")).get_email()%>
<%
}
%>
<table class="user_table">
	<thead><th colspan="10"><b>User Table</b></th></thead>
	<thead><th>ID</th><th>Email</th><th>First Name</th><th>Last Name</th><th>Password</th><th>Register Date</th><th>Is Admin</th><th>Card Number</th><th>Card Expiry</th><th>Phone Number</th>
	<% for (int i = 0; i < users.length; i++){%>
	<tr><td><%=users[i].get_id()%></td><td><%=users[i].get_email()%></td><td><%=users[i].get_first_name()%></td><td><%=users[i].get_last_name()%></td><td><%=users[i].get_password()%></td><td><%=users[i].get_reg_date()%></td><td><%=users[i].get_is_admin_string().toString()%></td><td><%=users[i].get_card_num()%></td><td><%=users[i].get_card_exp()%></td><td><%=users[i].get_phone_num()%></td>
	<%}%>
</table>

<%= new String("It Works!") %>
<br>
<table class="user_form_table">
<thead><th><b>Make a new user:</b></th><th><b>Update User Details:</b></th><th><b>Delete User:</b> </th></thead>
<tr><td>
<form action="/iotbay/web_pages/hello.jsp" method="POST">
	<input type="hidden" id="form_type" name="form_type" value="insert">
	<label for="email">Email:</label><br>
	<input type="text" id="email" name="email"><br>
	<label for="first_name">First name:</label><br>
	<input type="text" id="first_name" name="first_name"><br>
	<label for="last_name">Last name:</label><br>
	<input type="text" id="last_name" name="last_name"><br><br>
	<label for="password">Password:</label><br>
	<input type="text" id="password" name="password"><br><br>
	<label for="card_num">Credit/Debit Card Number:</label><br>
	<input type="text" id="card_num" name="card_num"><br><br>
	<label for="card_exp">Credit/Debit Card Expiry:</label><br>
	<input type="text" id="card_exp" name="card_exp"><br><br>
	<label for="phone_num">Phone Number:</label><br>
	<input type="text" id="phone_num" name="phone_num"><br><br>
	<input type="submit" value="Submit">
</form> 
</td>
<td>
<form action="/iotbay/web_pages/hello.jsp" method="POST">
	<input type="hidden" id="form_type" name="form_type" value="update">
	<label for="email">Email:</label><br>
	<input type="text" id="email" name="email"><br>
	<label for="first_name">First name:</label><br>
	<input type="text" id="first_name" name="first_name"><br>
	<label for="last_name">Last name:</label><br>
	<input type="text" id="last_name" name="last_name"><br><br>
	<label for="password">Password:</label><br>
	<input type="text" id="password" name="password"><br><br>
	<label for="card_num">Credit/Debit Card Number:</label><br>
	<input type="text" id="card_num" name="card_num"><br><br>
	<label for="card_exp">Credit/Debit Card Expiry:</label><br>
	<input type="text" id="card_exp" name="card_exp"><br><br>
	<label for="phone_num">Phone Number:</label><br>
	<input type="text" id="phone_num" name="phone_num"><br><br>
	<input type="submit" value="Submit">
</form>
</td>
<td>
<form action="/iotbay/web_pages/hello.jsp" method="POST">
	<input type="hidden" id="form_type" name="form_type" value="delete">
	<label for="email">Email:</label><br>
	<input type="text" id="email" name="email"><br>
	<input type="submit" value="Submit">
</form>
</td>
</tr>
</table>
<br>
<% UserLogEntry[] user_logs = db.get_all_user_logs(); %>
<table class="user_table">
	<thead><th colspan="10"><b>User Login Table</b></th></thead>
	<thead><th>Session ID</th><th>Email</th><th>Login Date/Time</th><th>Logout Date/Time</th>
	<% for (int i = 0; i < user_logs.length; i++){%>
	<tr><td><%=user_logs[i].get_session_id()%></td><td><%=user_logs[i].get_email()%></td><td><%=user_logs[i].get_login_date()%></td><td><%=user_logs[i].get_logout_date()%></td>
	<%}%>
</table>
<table class="user_form_table">
	<thead><th><b>Login As User</b></th><th><b>Logout as Current User</b></th></thead>
	<tr>
		<td>
		<form action="/iotbay/web_pages/hello.jsp" method="POST">
			<input type="hidden" id="form_type" name="form_type" value="login">
			<label for="email">Email:</label><br>
			<input type="text" id="email" name="email"><br>
			<input type="submit" value="Login">
		</form>
		</td>
		<td>
			<form action="/iotbay/web_pages/hello.jsp" method="POST">
				<input type="hidden" id="form_type" name="form_type" value="logout">
				<input type="submit" value="Logout">
			</form>
		</td>
	</tr>
</table>
</body>
</html>
