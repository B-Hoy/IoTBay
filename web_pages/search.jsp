<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.UserLogEntry"%>
<%@page import="uts.iotbay.Product"%>
<%@page import="uts.iotbay.Cart"%>


<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home | Ecommerce</title>
        <link rel="stylesheet" href="search.css">
    </head>
    <%
        // This check is *required* to use the db, otherwise data isn't fully persistent
        Database db = (Database)application.getAttribute("database");
        if (db == null){
	        db = new Database();
	        application.setAttribute("database", db);}
        String form_search_query = request.getParameter("search");
        Product[] search_results;
        if (form_search_query != null){
            search_results = db.search_for_product(form_search_query);
        }else{
            search_results = db.get_all_products();
        }
	%>
    <body>
        <div class="topnav">
            <a href="main.html">Home</a>
            <a class="active" href="#search">Search</a>
            <a href="myProfile.html">My Profile</a>
            <a href="cart.jsp">Cart</a>
            <a href="logout.html" style="float:right;">Logout</a>
        </div>
        
        <!--Testing the search bar -->
        <div class="search-container">
            
            <form action="/iotbay/web_pages/search.jsp" method="POST">
                <input type="hidden" id="form_type" name="form_type" value="search">
                <label for="search"></label><br>
	            <input type="text" id="search" name="search" placeholder="Search products, brands...">
                <input type="submit" value="Submit">
            </form> 
        </div>
        

        
        <div class="content">
             <!-- Example filters-->
            <div class="filters">
                <div class="filter-section">
                    <h3>Customer Review</h3>
                    <ul>
                        <li><input type="checkbox" id="4stars" name="rating" value="4">&nbsp;<label for="4stars">★★★★ & Up</label></li>
                        <li><input type="checkbox" id="3stars" name="rating" value="3">&nbsp;<label for="3stars">★★★ & Up</label></li>
                        <li><input type="checkbox" id="2stars" name="rating" value="2">&nbsp;<label for="2stars">★★ & Up</label></li>
                        <li><input type="checkbox" id="1star" name="rating" value="1">&nbsp;<label for="1star">★ & Up</label></li>
                    </ul>
                </div>
            
                <div class="filter-section">
                    <h3>Brand</h3>
                    <ul>
                        <li><input type="checkbox" id="brand1" name="brand" value="brand1">&nbsp;<label for="brand1">brand1</label></li>
                        <li><input type="checkbox" id="brand2" name="brand" value="brand2">&nbsp;<label for="brand2">brand2</label></li>
                        <!-- Add more brands as needed -->
                        <li><a href="#">See more</a></li>
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
                        <input type="text" id="minPrice" name="minPrice" placeholder="$ Min">
                        <button type="button">Go</button>
                    </div>
                </div>
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
        </div>
    </body>
</html>