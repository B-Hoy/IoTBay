<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.UserLogEntry"%>
<%@page import="uts.iotbay.Product"%>
<%@page import="uts.iotbay.Cart"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home | Ecommerce</title>
    <link rel="stylesheet" href="cart.css">
</head>
 <body>
    <div class="topnav">
        <a href="main.jsp">Home</a>
        <a href="search.jsp">Search</a>
        <a href="myProfile.jsp">My Profile</a>
        <a class="active" href="cart.jsp">Cart</a>
        <a href="logout.html" style="float:right;">Logout</a>
    </div>

   <%
    // This check is *required* to use the db, otherwise data isn't fully persistent
    Database db = (Database)application.getAttribute("database");
    if (db == null){
       db = new Database();
       application.setAttribute("database", db);
    }



    //Get cart from session and if doesn't exist than make new cart
    Cart cart = (Cart) session.getAttribute("cart");
    if(cart == null){
      cart = new Cart(); 
      session.setAttribute("cart", cart);
    }

    //Get From parameters
    String formType = request.getParameter("form_type");
    String productIdString = request.getParameter("id");
    String quantityString = request.getParameter("quantity");

    if(productIdString != null && formType != null){
      int productId = Integer.parseInt(productIdString);

      if("insert_cart".equals(formType)){
         int quantity = Integer.parseInt(quantityString);
         //Adding product
         cart.add_product(productId, quantity);

      //Delete product
      }else if("remove_item".equals(formType)){
         cart.delete_product(productId);
      }else if("update_quantity".equals(formType)){
         int quantity = Integer.parseInt(quantityString);
      }else if("reduce_cart".equals(formType)){
         cart.add_product(productId, Integer.valueOf(quantityString) * -1);
      }
    }

  //Get products in cart
  Product[] cartProducts = cart.get_cart_inventory(db);
   
  %>  
   

   <h1>Shopping Cart</h1>

   <div class="cart-item">
      <% double totalPrice = 0.0; %>

      <% for(Product product : cartProducts){ %>
         <% int productQuantity = product.get_quantity(); %>

         <div class="cart-item">
            <!-- <img src="images/<%= product.get_image_location() %>" alt="<%= product.get_name() %>" style="width:100%"> -->
            <h4><%= product.get_name() %></h4>
            <p>Price: $<%= product.get_price() %></p>

            <div class="counter">
               <%= productQuantity %><br>
               <form action="/iotbay/web_pages/cart.jsp" method="POST">
                  <input type="hidden" name="form_type" value="insert_cart"> 
                  <label for="quantity">Amount to add:</label><br>
                  <input type="text" id="quantity" name="quantity"><br><br>
                  <input type="hidden" name="id" value="<%= product.get_id() %>"> 
                  <button type="submit">+</button>
               </form>
               <form action="/iotbay/web_pages/cart.jsp" method="POST">
                  <input type="hidden" name="form_type" value="reduce_cart"> 
                  <label for="quantity">Amount to remove:</label><br>
                  <input type="text" id="quantity" name="quantity"><br><br>
                  <input type="hidden" name="id" value="<%= product.get_id() %>"> 
                  <button type="submit">+</button>
               </form>
               <form action="/iotbay/web_pages/cart.jsp" method="POST">
                  <input type="hidden" name="form_type" value="remove_item"> 
                  <input type="hidden" name="id" value="<%= product.get_id() %>"> 
                  <button type="submit">Remove</button>
               </form>
            </div>

            <p>Product Cost: $<%= product.get_price() * productQuantity %></p>
            <% totalPrice += product.get_price() * productQuantity; %>

      </div>  
      <% } %>
   </div>

   <div class="checkout-section">
      <p>Total: $<%= totalPrice %></p> 
      <form action="/iotbay/web_pages/checkout.jsp" method="POST">
      <button type="submit">Checkout</button>
      </form>
   </div>

 </body>
</html>



   


















