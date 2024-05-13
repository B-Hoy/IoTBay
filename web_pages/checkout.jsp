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

    <% int orderNumber=new Random().nextInt(1000000000); %>



    <div class="container">
      <form action="checkout.jsp" method="post">
        <h3>Shipping Information</h3>
        <label for="first_name">First Name:</label>
        <input type="text" id="first_name" name="first_name" value='<%=logged_in_user.get_first_name()  != null ? logged_in_user.get_first_name() : "" %>' required>
        <label for="last_name">First Name:</label>
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
    
        <input type="checkbox" id="sameAddress">
        <label for="sameAddress">Use same billing address for shipping</label>
    
        <button type="submit">Complete Payment</button>
      </form>
    </div>
    
    
    <div id="successPopup" class="popup">
      <div class="popup-content">
        <span class="close">&times;</span>
        <p>Your payment is successful!</p>
        <p>Your order number is: <%= orderNumber %>
        </p>
      </div>
    </div>
   
  </body>
</html>