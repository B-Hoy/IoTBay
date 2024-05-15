<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.UserLogEntry"%>
<%@page import="uts.iotbay.Product"%>
<%@page import="uts.iotbay.Cart"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home | Ecommerce</title>
        <link rel="stylesheet" href="search.css">
    </head>
    <%
    // Ensure database connection
    Database db = (Database)application.getAttribute("database");
    if (db == null){
        db = new Database();
        application.setAttribute("database", db);
    }

    // Retrieving form parameters
    String form_search_query = request.getParameter("search");
    String[] ratings = request.getParameterValues("rating");
    String[] brands = request.getParameterValues("brand");
    String priceRange = request.getParameter("priceRange");

    // Declare search_results array
    Product[] search_results = null;

    if (form_search_query != null) {
        search_results = db.search_for_product(form_search_query, ratings, brands, priceRange);
    } else {
        search_results = db.get_all_products();
    }
%>

    <body>
        <div class="topnav">
            <a href="main.html">Home</a>
            <a class="active" href="#search">Search</a>
            <a href="myProfile.html">My Profile</a>
            <a href="cart.jsp">Cart</a>
            <a href="logout.jsp" style="float:right;">Logout</a>
        </div>

        <!--search bar -->
        <div class="search-container">
            <form action="/iotbay/web_pages/search.jsp" method="GET">
                <input type="text" id="search" name="search" placeholder="Search products..." class="search-input">
                <button type="submit" class="search-button">Search</button> 
            </form>
        </div>
        
        
        

        
        <div class="content">
             <!-- Example filters-->
             
                <div class="filters">
                    <form action="/iotbay/web_pages/search.jsp" method="POST">
                <div class="filter-section">
                    <h3>Customer Review</h3>
                    <ul>
                        <li><input type="checkbox" id="rating" name="rating" value="4">&nbsp;<label for="rating">★★★★ & Up</label></li>
                        <li><input type="checkbox" id="rating" name="rating" value="3">&nbsp;<label for="rating">★★★ & Up</label></li>
                        <li><input type="checkbox" id="rating" name="rating" value="2">&nbsp;<label for="rating">★★ & Up</label></li>
                        <li><input type="checkbox" id="rating" name="rating" value="1">&nbsp;<label for="rating">★ & Up</label></li>
                    </ul>
                </div>
            
                <!-- Simplified for brevity -->
                <div class="filter-section">
                    <h3>Brand</h3>
                    <ul>
                        <li><input type="checkbox" id="brand1" name="brand" value="Connect SmartHome">&nbsp;<label for="brand1">Connect SmartHome</label></li>
                        <li><input type="checkbox" id="brand2" name="brand" value="Arlo">&nbsp;<label for="brand2">Arlo</label></li>
                        <li><input type="checkbox" id="brand3" name="brand" value="Ring">&nbsp;<label for="brand3">Ring</label></li>
                    </ul>
                </div>

            
                <div class="filter-section">
                    <h3>Price</h3>
                    <ul>
                        <li><input type="checkbox" id="upto25" name="price" value="25">&nbsp;<label for="upto25">Up to $25</label></li>
                        <li><input type="checkbox" id="25to50" name="price" value="50">&nbsp;<label for="25to50">$25 to $50</label></li>
                        <li><input type="checkbox" id="50to100" name="price" value="100">&nbsp;<label for="50to100">$50 to $100</label></li>
                        <li><input type="checkbox" id="100to200" name="price" value="200">&nbsp;<label for="100to200">$100 to $200</label></li>
                        <li><input type="checkbox" id="200toabove" name="price" value="200">&nbsp;<label for="200toabove">$200 & above</label></li>
                        <!-- Add more price ranges as needed -->
                    </ul>
                    <div>
                        <button type="submit" class="filter-button">Go</button>
                    </div>
                </div>
            </form>
            </div>
        
            <div class="product-list"> 
                <!-- all these products need to come from the database or something so they can be added to cart -->
                <div class="row">
                    <% if (search_results != null && search_results.length > 0) {
                        for (Product product : search_results) { %>
                            <div class="column">
                                <div class="card">
                                    <img src="<%= "images/" + product.get_image_location() %>" alt="<%= product.get_name() %>" style="width:100%">
                                    <h1><%= product.get_name() %></h1>
                                    <p class="price">$<%= product.get_price() %></p>
                                    <p><button>Add to Cart</button></p>
                                </div>
                            </div>
                        <%  }
                       } else { %>
                            <p>No products found.</p>
                        <% } %>
                </div>
            </div>
        </div>
    </body>
</html>
