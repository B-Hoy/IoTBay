<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.User"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="iotbay.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IoTBay Main Page</title>
    </head>
    <body>
        <%
            User user = (User)session.getAttribute("user");
        %>
        <h1>User Profile</h1>
        <table class="profile_table">
            <thead><th>Email</th><th>Name</th><th>Password</th><th>Date Of Registration</th><th>Debit/Credit Card Number</th><th>Debit/Credit Card Expiry Date</th><th>Admin Privileges</th>
            <tr><td>${user.email}</td><td>${user.name}</td><td>${user.password}</td><td>${user.get_formatted_date()}</td><td>${user.card_num}</td><td>${user.card_exp}</td><td>${user.get_admin_string()}</td>
        </table>
        <a href="logout.jsp"><div class="login_button">Logout</div></a>
    </body>
</html>
