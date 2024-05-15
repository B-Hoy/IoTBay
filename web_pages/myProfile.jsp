<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.UserLogEntry"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home | Ecommerce</title>
    <link rel="stylesheet" href="myProfile.css">
</head>

<%
// This check is *required* to use the db, otherwise data isn't fully persistent
Database db = (Database)application.getAttribute("database");     // enter into every class
if (db == null){
    db = new Database();
    application.setAttribute("database", db);
} 
%>

<%
User logged_in_user = null; // Initialize the variable

if (session.getAttribute("session_id") != null){
    int session_id = (Integer)session.getAttribute("session_id");
    logged_in_user = db.get_user(db.get_user_log(session_id).get_email());
}
%>


<body>
    <div class="topnav">
        <a href="main.jsp">Home</a>
        <a href="search.jsp">Search</a>
        <a class="active" href="#myProfile">My Profile</a>
        <a href="cart.jsp">Cart</a>
        <a href="logout.jsp" style="float:right;">Logout</a>
    </div>

    <div class="container">
        <h1>My Account</h1>
        <div class="account-info">
            <h2>Account Information</h2>
            <p><strong>First Name:</strong> <%= logged_in_user != null ? logged_in_user.get_first_name() : "" %></p>
            <p><strong>Last Name:</strong> <%= logged_in_user != null ? logged_in_user.get_last_name() : "" %></p>
            <p><strong>Email:</strong> <%= logged_in_user != null ? logged_in_user.get_email() : "" %></p>
            <p><strong>Password:</strong><%= logged_in_user != null ? logged_in_user.get_password() : "" %></p>
            <p><strong>Phone Number:</strong> <%= logged_in_user != null ? logged_in_user.get_phone_num() : "" %></p>
            <p><strong>Credit Card Info:</strong> <%= logged_in_user != null ? logged_in_user.get_card_num() : "" %></p>
            <p><strong>Credit Card Expiry:</strong> <%= logged_in_user != null ? logged_in_user.get_card_exp() : "" %></p>
        </div>
        <div class="order-history">
            <h2>Order History</h2>
            <ul>
                <li>Order #001 - Date: January 1, 2024</li>
                <li>Order #002 - Date: February 5, 2024</li>
                <li>Order #003 - Date: March 10, 2024</li>
            </ul>
        </div>
    </div>

    


</body>


