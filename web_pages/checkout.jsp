<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.UserLogEntry"%>
<%@page import="uts.iotbay.Product"%>
<%@ page import="java.util.Random" %>

<!-- ^^^ Include these to access JSP functions --> 
<html>

  <head>
    <title>Checkout</title>
    <link rel="stylesheet" href="checkout.css">
  </head>

  <div class="topnav">
    <a href="cart.jsp">Back to Cart</a>
  </div>

  <body bgcolor=white>
  
    <% // This check is *required* to use the db, otherwise data isn't fully persistent 
      Database db = (Database)application.getAttribute("database");
      if (db == null){
	      db = new Database();
	      application.setAttribute("database", db);
	  %>
	    
	  <%
    }

    int this_session_id=0;

    if (session.getAttribute("session_id") !=null) { 
      // Means the user is either logged in (returns an Object that you have to cast as an Integer) or not (returns null) 
      this_session_id=(Integer)session.getAttribute("session_id");   
    } 

    User logged_in_user = db.get_user((db.get_user_log(this_session_id)).get_email()); 
      
    // gets the email of the currently logged in user, email gets used as an argument to pull the full user's data from the db
    %>

  
    <div class="container">
      <form id="paymentForm" action="checkout.jsp" method="post">
        <h3>Shipping Information</h3>
        <label for="first_name">First Name:</label>
        <input type="text" id="first_name" name="first_name" value='<%=logged_in_user.get_first_name()  != null ? logged_in_user.get_first_name() : "" %>' required>
        <label for="last_name">Last Name:</label>
        <input type="text" id="last_name" name="last_name" value='<%=logged_in_user.get_last_name()  != null ? logged_in_user.get_last_name() : "" %>'required>
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" value='<%=logged_in_user.get_email()  != null ? logged_in_user.get_email() : "" %>' required>
        <label for="phone_num">Phone Number:</label><br>
        <input type="text" id="phone_num" name="phone_num" value='<%=logged_in_user.get_phone_num()  != null ? logged_in_user.get_phone_num() : "" %>' required>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" required>
        <label for="city">City:</label>
        <input type="text" id="city" name="city" required>
        <label for="country">Country:</label>
        <input type="text" id="country" name="country" required>
        <label for="state">State:</label>
        <input type="text" id="state" name="state" required>
    
    
        <h3>Payment Information</h3>
        <label for="cardName">Name on Card:</label>
        <input type="text" id="cardName" name="cardName" required>
        <label for="card_num">Card Number:</label>
        <input type="text" id="card_num" name="card_num" value='<%=logged_in_user.get_card_num()  != null ? logged_in_user.get_card_num() : "" %>' required>
        <label for="card_exp">Expiry Date:</label>
        <input type="text" id="card_exp" name="card_exp" value='<%=logged_in_user.get_card_exp()  != null ? logged_in_user.get_card_exp() : "" %>' required>
        <label for="cvv">CVV:</label>
        <input type="text" id="cvv" name="cvv" required>
  
        
        <h3>Shipping Information</h3>

        <input type="checkbox" id="sameAddress" onchange="copyShippingAddress()">
        <label for="sameAddress">Use same billing address for shipping</label>

        <div style="margin-top: 10px;"></div>
        <label for="billing_address">Address:</label>
        <input type="text" id="billing_address" name="billing_address" required>
        <label for="billing_city">City:</label>
        <input type="text" id="billing_city" name="billing_city" required>
        <label for="billing_country">Country:</label>
        <input type="text" id="billing_country" name="billing_country" required>
        <label for="billing_state">State:</label>
        <input type="text" id="billing_state" name="billing_state" required>
        
        <div style="margin-top: 10px;"></div>
        <button type="button" onclick="completePayment()">Complete Payment</button>

      </form>

      <div id="paymentSuccess" class="hidden">
        <h2>Your payment was successful!</h2>
        <p>Order Number: <span id="orderNumber"></span></p>
         <button onclick="returnToHomePage()">Return to Home Page</button>
      </div>
    </div>
        <script>
          function completePayment() {

            var requiredFields = document.querySelectorAll('input[required]');
                var isValid = true;
                requiredFields.forEach(function(field) {
                    if (!field.value) {
                        isValid = false;
                    }
                });
                if (isValid) {
                  var orderNumber = Math.floor(Math.random() * 1000000) + 1;
        
                document.getElementById("paymentForm").classList.add("hidden");
                document.getElementById("paymentSuccess").classList.remove("hidden");

                document.getElementById("orderNumber").textContent = orderNumber;
                } else {
                    alert('Please fill out all required fields.');
                }

    
          }
        
          function returnToHomePage() {
            window.location.href = 'main.jsp';
          }
        </script>


    <script>
      function copyShippingAddress() {
        if (document.getElementById("sameAddress").checked) {
          document.getElementById("billing_address").value = document.getElementById("address").value;
          document.getElementById("billing_city").value = document.getElementById("city").value;
          document.getElementById("billing_country").value = document.getElementById("country").value;
          document.getElementById("billing_state").value = document.getElementById("state").value;
        } else {
          document.getElementById("billing_address").value = "";
          document.getElementById("billing_city").value = "";
          document.getElementById("billing_country").value = "";
          document.getElementById("billing_state").value = "";
        }
      }
    </script>
  
   
  </body>
</html>