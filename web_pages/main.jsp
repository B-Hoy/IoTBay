<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.UserLogEntry"%>

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
}%>

<body>
    <div class="topnav">
        <a class="active" href="#home">Home</a>
        <a href="search.jsp">Search</a>
        <a href="myProfile.jsp">My Profile</a>
        <a href="cart.jsp">Cart</a>
        <a href="logout.jsp" style="float:right;">Logout</a>
    </div>

    <div class="product-list"> 
        <!-- all these products need to come from the database or something so they can be added to cart -->
        <div class="row">
            <div class="column">
                <div class="card">
                    <img src="https://cdn.thewirecutter.com/wp-content/media/2023/06/macbooks-2048px-23790-2x1-1.jpg?auto=webp&quality=75&crop=2:1&width=768&dpr=1.5" alt="Product1" style="width:100%">
                    <h1>Laptop</h1>
                    <p class="price">$900</p>
                    <p>cool laptop</p>
                    <p><button>Add to Cart</button></p>
                </div>
            </div>
            <div class="column">
                <div class="card">
                    <img src="https://m.media-amazon.com/images/I/618ihEBwiSL.__AC_SX300_SY300_QL70_FMwebp_.jpg" alt="Product2" style="width:100%">
                    <h1>Mouse</h1>
                    <p class="price">$50</p>
                    <p>cool mouse</p>
                    <p><button>Add to Cart</button></p>
                </div>
            </div>
            <div class="column">
                <div class="card">
                    <img src="product2.jpg" alt="Product2" style="width:100%">
                    <h1>Product 2</h1>
                    <p class="price">$50</p>
                    <p>Description</p>
                    <p><button>Add to Cart</button></p>
                </div>
            </div>
            <div class="column">
                <div class="card">
                    <img src="product2.jpg" alt="Product2" style="width:100%">
                    <h1>Product 2</h1>
                    <p class="price">$50</p>
                    <p>Description</p>
                    <p><button>Add to Cart</button></p>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="column">
                <div class="card">
                    <img src="product1.jpg" alt="Product1" style="width:100%">
                    <h1>Product 1</h1>
                    <p class="price">$20</p>
                    <p>Description</p>
                    <p><button>Add to Cart</button></p>
                </div>
            </div>
            <div class="column">
                <div class="card">
                    <img src="product2.jpg" alt="Product2" style="width:100%">
                    <h1>Product 2</h1>
                    <p class="price">$50</p>
                    <p>Description</p>
                    <p><button>Add to Cart</button></p>
                </div>
            </div>
            <div class="column">
                <div class="card">
                    <img src="product2.jpg" alt="Product2" style="width:100%">
                    <h1>Product 2</h1>
                    <p class="price">$50</p>
                    <p>Description</p>
                    <p><button>Add to Cart</button></p>
                </div>
            </div>
            <div class="column">
                <div class="card">
                    <img src="product2.jpg" alt="Product2" style="width:100%">
                    <h1>Product 2</h1>
                    <p class="price">$50</p>
                    <p>Description</p>
                    <p><button>Add to Cart</button></p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>