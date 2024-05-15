<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.UserLogEntry"%>
<%@page import="uts.iotbay.Product"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home | Ecommerce</title>
    <link rel="stylesheet" href="main.css">
</head>

<%
// This check is *required* to use the db, otherwise data isn't fully persistent
Database db = (Database)application.getAttribute("database"); 		// enter into every class
if (db == null){
	db = new Database();
	application.setAttribute("database", db);

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
			int local_session_id = db.add_user_login(form_email, form_password);
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
}%>

<body>
    <div class="topnav">
        <a class="active" href="#home">Home</a>
        <a href="search.jsp">Search</a>
        <a href="myProfile.jsp">My Profile</a>
        <a href="cart.jsp">Cart</a>
        <a href="logout.html" style="float:right;">Logout</a>
    </div>

    <div class="product-list"> 
        <%
            //Get all products from database
            Product[] products = db.get_all_products();
            for (Product product : products) {
        %>

        <div class="column">
            <div class="card">
                <img src="images/<%= product.get_image_location() %>" alt="<%= product.get_name() %>"
                style="width:100%">
                <h1><%= product.get_name() %></h1>
                <p class="price">$<%= product.get_price() %></p>
                <form action="/iotbay/web_pages/cart.jsp" method="POST">
                    <input type="hidden" name="form_type" value="insert_cart">
                    <input type="hidden" name="id" value="<%= product.get_id() %>">
                    <input type="hidden" name="quantity" value="1">
                    <button type="submit">Add to Cart</button>
                </form>
            </div>
        </div>
        <% } %>
    </div>

</body>
</html>
