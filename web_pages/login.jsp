<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.UserLogEntry"%>
<%@page import="uts.iotbay.Product"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<!-- ^^^ Include these to access JSP functions --> 
<%@page import="your.package.name.LoginService"%> <!-- Import the LoginService class -->

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
    <input type="hidden" id="form_type" name="form_type" value="insert_user">
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
    <input type="hidden" id="form_type" name="form_type" value="update_user">
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
    <input type="hidden" id="form_type" name="form_type" value="delete_user">
    <label for="email">Email:</label><br>
    <input type="text" id="email" name="email"><br><br>
    <input type="submit" value="Submit">
</form>
</td>
</tr>
</table>
</body>
</html>
