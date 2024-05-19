<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.UserLogEntry"%>
<%@page import="uts.iotbay.Order"%>
<%@page import="uts.iotbay.Product"%>
<%@page import="uts.iotbay.Cart"%>
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
int session_id = -1;
int form_id = 0;
int form_id2 = 0;
int form_quantity = 0;
if (session.getAttribute("session_id") != null){
    session_id = (Integer)session.getAttribute("session_id");
    logged_in_user = db.get_user(db.get_user_log(session_id).get_email());
}
if (request.getParameter("id") != null){
	form_id = Integer.parseInt(request.getParameter("id"));
}
if (request.getParameter("id2") != null){
	form_id2 = Integer.parseInt(request.getParameter("id2"));
}
if (request.getParameter("quantity") != null){
	form_quantity = Integer.parseInt(request.getParameter("quantity"));
}
String form_type = request.getParameter("form_type");
if (form_type != null){
    switch(form_type){
        case "create_order":
			if (session.getAttribute("cart") != null){ // No cart, no order
				db.create_order((Cart)session.getAttribute("cart"), (Integer)session.getAttribute("session_id"));
			}
			break;
		case "delete_order":
			db.delete_order(form_id);
			break;
		case "insert_order":
			db.insert_item_order(form_id, form_id2, form_quantity);
			break;
		case "remove_order":
			db.remove_item_order(form_id, form_id2);
			break;
    }
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

            <br>
            <br>
            <br>
            <h2>Update Account Information</h2>
            <form action="/iotbay/web_pages/myProfile.jsp" method="POST">
                <input type="hidden" id="form_type" name="form_type" value="update_user">
                <label for="email">Email:</label><br>
                <input type="text" id="email" name="email" value=<%= logged_in_user.get_email()%>
                <br><br>

                <label for="first_name">First name:</label><br>
                <input type="text" id="first_name" name="first_name" value=<%= logged_in_user.get_first_name() %>
                <br><br>

                <label for="last_name">Last name:</label><br>
                <input type="text" id="last_name" name="last_name" value=<%= logged_in_user.get_last_name() %>
                <br><br>

                <label for="password">Password:</label><br>
                <input type="text" id="password" name="password" value=<%= logged_in_user.get_password() %>
                <br><br>

                <label for="card_num">Credit/Debit Card Number:</label><br>
                <input type="text" id="card_num" name="card_num" value=<%= logged_in_user.get_card_num() %>
                <br><br>
                
                <label for="card_exp">Credit/Debit Card Expiry:</label><br>
                <input type="text" id="card_exp" name="card_exp" value=<%= logged_in_user.get_card_exp() %>
                <br><br>

                <label for="phone_num">Phone Number:</label><br>
                <input type="text" id="phone_num" name="phone_num" value=<%= logged_in_user.get_phone_num() %>
                <br><br>
                <input type="submit" value="Submit">
            </form>
        </div>
        <div class="order-history">
            <h2>Saved Order</h2>
            <ul>
                <table class="order_table">
                    <% Order[] orders = db.get_orders_by_email(logged_in_user.get_email()); %>
                    <thead><th colspan="10"><b>Saved Orders</b></th></thead>
                    <thead><th>Order ID</th><th>Owner User ID</th><th>Finalised</th><th>Payment ID</th><th>Date/Time Created</th><th>Order Items</th>
                    <% for (int i = 0; i < orders.length; i++){
                        Product[] order_products = orders[i].get_items();
                    %>
                    <tr><td><%=orders[i].get_id()%></td><td><%=orders[i].get_owner_email()%></td><td><%=orders[i].get_finalised()%></td><td><%=orders[i].get_payment_id()%></td><td><%=orders[i].get_date_created()%></td>
                    <td>
                    <table class="order_item_table">
                        <thead><th>Product ID</th><th>Product Name</th><th>Price</th><th>Rating</th><th>Brand</th><th>Quantity</th><th>Image Location</th>
                        <% for (int j = 0; j < order_products.length; j++){%>
                        <tr><td><%=order_products[j].get_id()%></td><td><%=order_products[j].get_name()%></td><td><%=order_products[j].get_price()%></td><td><%=order_products[j].get_rating()%></td><td><%=order_products[j].get_brand()%></td><td><%=order_products[j].get_quantity()%></td><td><%=order_products[j].get_image_location()%></td>
                        <%}%>
                        </table>
                    </td>
                    </tr>
                    <%}%>
                </table>
                
<table class="order_func_table">
	<thead><th><b>Create order from cart:</b></th><th><b>Delete order:</b></th><th><b>Delete item from order:</b></th><th><b>Insert item into order:</b></th></thead>
	<tr><td>
	<form action="/iotbay/web_pages/myProfile.jsp" method="POST">
		<input type="hidden" id="form_type" name="form_type" value="create_order">
		<input type="submit" value="Submit">
	</form> 	
	</td>
	<td>
	<form action="/iotbay/web_pages/myProfile.jsp" method="POST">
		<input type="hidden" id="form_type" name="form_type" value="delete_order">
		<label for="id">Order ID:</label><br>
		<input type="text" id="id" name="id"><br>
		<input type="submit" value="Submit">
	</form>
	</td>
	<td>
	<form action="/iotbay/web_pages/myProfile.jsp" method="POST">
	<input type="hidden" id="form_type" name="form_type" value="remove_order">
		<label for="id">Order ID:</label><br>
		<input type="text" id="id" name="id"><br>
		<label for="id2">Product ID:</label><br>
		<input type="text" id="id2" name="id2"><br>
	<input type="submit" value="Submit">
	</form>
	</td>
	<td>
	<form action="/iotbay/web_pages/myProfile.jsp" method="POST">
		<input type="hidden" id="form_type" name="form_type" value="insert_order">
		<label for="id">Order ID:</label><br>
		<input type="text" id="id" name="id"><br>
		<label for="id2">Product ID:</label><br>
		<input type="text" id="id2" name="id2"><br>
		<label for="quantity">Quantity:</label><br>
		<input type="text" id="quantity" name="quantity"><br><br>
		<input type="submit" value="Submit">
	</form>
	</td>
	</tr>
</table>
            </ul>
            <h3>
                <ul>
                    <li>
                        <% UserLogEntry[] user_logs = db.get_user_log_by_user(session_id); %>
                        <table class="user_table">
                            <thead><th colspan="10"><b>User Login Table</b></th></thead>
                            <thead><th>Session ID</th><th>Email</th><th>Login Date/Time</th><th>Logout Date/Time</th>
                            <% for (int i = 0; i < user_logs.length; i++){%>
                            <tr><td><%=user_logs[i].get_session_id()%></td><td><%=user_logs[i].get_email()%></td><td><%=user_logs[i].get_login_date()%></td><td><%=user_logs[i].get_logout_date()%></td>
                            <%}%>
                        </table>
                    </li>
                </ul>
            </h3>
            <form action="/iotbay/web_pages/logout.jsp" method="POST">
                <input type="hidden" id="form_type" name="form_type" value="delete_user">
                <input type="submit" value="DELETE Account">
            </form>
        </div>
    </div>

    


</body>


