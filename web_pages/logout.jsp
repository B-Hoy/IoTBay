<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.UserLogEntry"%>
<%@page import="uts.iotbay.Product"%>
<%@page import="uts.iotbay.Cart"%>
<%@page import="uts.iotbay.Order"%>
<%
Database db = (Database)application.getAttribute("database"); 		// enter into every class
if (db == null){
	db = new Database();
	application.setAttribute("database", db);
}
String form_type = request.getParameter("form_type");
if (form_type != null ){
    switch (form_type){
        case "delete_user":
            db.delete_user(db.get_user_log((int)session.getAttribute("session_id")).get_email());
            break;
    }
}
if (session.getAttribute("session_id") != null){ // just to make sure the user is actually logged in
    db.set_user_logout((int)session.getAttribute("session_id"));
    session.removeAttribute("session_id");
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Logout</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            text-align: center;
            margin-top: 100px; /* Adjust as needed */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Thank you for visiting IoT Bay Store</h1>
        <p>You have been successfully logged out</p>
        <a href="index.html">Return to the homepage</a>
    </div>
</body>
</html>
