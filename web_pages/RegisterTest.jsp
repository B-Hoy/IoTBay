<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.UserLogEntry"%>

<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home | Ecommerce</title>
    <link rel="stylesheet" href="myProfile.css">
</head>
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

<body>

    <div class="container">
        <h1>Register</h1>


        <form action="/iotbay/web_pages/hello.jsp" method="POST">          
            <input type="hidden" id="form_type" name="form_type" value="insert">

            <div class="account-info">
                <h2>Account Information</h2>
                <p><strong>First Name:</strong></p>
                <div class="container">
                    <p>Enter Name</p>
                    <input id="first_name"
                        type="text" name="first_name"
                        placeholder="John">
                </div>

                <div class="account-info">
                    <p><strong>Last Name:</strong></p>
                    <div class="container">
                        <p>Enter Name</p>
                        <input id="last_name"
                            type="text" name="last_name"
                            placeholder="Smith">
                    </div>

                <p><strong>Email:</strong></p>
                <div class="container">
                    <p>Enter Email</p>
                    <input id="email"
                        type="text" name="email"
                        placeholder="Email@email.com">
                </div>

                <p><strong>Password:</strong></p>
                <div class="container">
                    <p>Enter Password</p>
                    <input id="password"
                        type="password" name="password"
                        placeholder="********">
                </div>

                <p><strong>Credit Card Info:</strong></p>
                <div class="container">
                    <p>Enter Credit Card Info</p>
                    <input id="card_num"
                        type="text" name="card_num"
                        placeholder="************1234">
                </div>

                <p><strong>Card Expiry:</strong></p>
                <div class="container">
                    <p>Enter Card Expiry</p>
                    <input id="card_exp"
                        type="text" name="card_exp"
                        placeholder=" 0412345678 ">
                </div>

                <p><strong>Phone Number:</strong></p>
                <div class="container">
                    <p>Enter Phone Number</p>
                    <input id="phone_num"
                        type="text" name="phone_num"
                        placeholder=" 0412345678 ">
                </div>
                <br>
                <br>
                <input type="submit" value="Submit">

            </form>
            
        </div>
    </div>


</body>

</html>