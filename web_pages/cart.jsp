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
        <a href="main.html">Home</a>
        <a href="search.html">Search</a>
        <a href="myProfile.html">My Profile</a>
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
    Cart cart == (Cart) session.getAttribute("cart");
    if(cart = null){
      cart = new Cart(); session.setAttribute("cart", cart);
    }



    //Get Form parameters
    String formType = request.getParameter("form_type");
    String productIdStr = request.getParameter("id");
    String quantityStr = request.getParameter("quantity");

    if (formType != null && productIdStr != null) {
      int productId = Integer.parseInt(productIdStr);

      if("insert_cart".equals(formType)) {
          int quantity = Integer.parseInt(quantityStr);

          //Get product details from database
          Product product = db.get_product(productId);

          //Add product to cart
          cart.addItem(product, quantity);

      }else if("update_quantity".equals(formType)) {
          int quantity = Integer.parseInt(quantityStr);

          //Update product quantity in cart
          cart.updateItem(productId, quantity);

      }else if ("remove_item".equals(formType)) {
          //Remove item from cart
          cart.removeItem(productId);
      }
  }


  //Get products in cart
  Product[] cartProducts = cart.get_cart_inventory(db);
   
  %>  
   

   <h1>Shopping Cart</h1>

   <div class="cart-item">
      <% double totalPrice = 0.0; %>
      <% for(Product product : cartProducts){ %>
         <div class="cart-item">
            <h4><%= product.get_name() %></h4>
            <p>Price: $<%= product.get_price() %></p>

            <div class="counter">
               <form action="/iotbay/web_pages/cart.jsp" method="POST">
                  <input type="hidden" name="form_type" value="update_quantity">
                  <input type="hidden" name="id" value="<%= product.get_id() %>">
                  <input type="hidden" name="quantity" value="<%= product.get_quantity() - 1 %>">
                  <button type="submit">-</button>

                  <input type="number" id="quantity" name="quantity" min="1" value="<%= product.get_quantity() %>" readonly>
               </form>

               <form action="/iotbay/web_pages/cart.jsp" method="POST">
                  <input type="hidden" name="form_type" value="update_quantity">
                  <input type="hidden" name="id" value="<%= product.get_id() %>">
                  <input type="hidden" name="quantity" value="<%= product.get_quantity() + 1 %>">
                  <button type="submit">+</button>
               </form>   

               <form action="/iotbay/web_pages/cart.jsp" method="POST">
                  <input type="hidden" name="form_type" value="remove_item">
                  <input type="hidden" name="id" value="<%= product.get_id() %>">
                  <button type="submit">Remove</button>
               </form>
            </div>

            <p>Total: $<%= product.get_price() * product.get_quantity() %></p>
            <% totalPrice = totalPrice + product.get_price() * product.get_quantity(); %>

      </div>  
      <% 
      } %>
   </div>

   <div class="checkout-section">
      <p>Total: $<%= totalPrice %></p> 
      <button>Checkout</button>
   </div>

 </body>
</html>





   


















