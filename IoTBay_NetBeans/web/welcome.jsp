<%@page import="uts.isd.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.time.LocalDateTime" %>
<%@page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="iotbay.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IoTBay Welcome</title>
    </head>
    <%
        String name = request.getParameter("name"); 
        String email = request.getParameter("email"); 
        LocalDateTime reg_date = LocalDateTime.now();
        String password = request.getParameter("password"); 
        String card_num = request.getParameter("card_num");
        String card_exp = request.getParameter("card_exp");
        String admin = request.getParameter("admin");
    %>
    <body>
        <h1>Welcome!</h1>
        Welcome <% out.println(name); %>
        <br>
        Your email is <%=email %>
        <br>
        Your password is <%=password %>
        <br>
        Your account registration date is <%=User.get_formatted_date(reg_date) %>
        <br>
        Your debit/credit card number is is <%=card_num %>
        <br>
        Your debit/credit card expiry date is <%=card_exp %>
        <br>
        <% if (admin == null || admin.equals("0")){%>
        You are not an administrative user :(
        <%}else{%>
        You are an admin!
        <%}%>
        <br>
        <a href="index.html"><div class="login_button">Cancel</div></a>
        <br>
        <a href="main.jsp"><div class="login_button">Continue</div></a>
        <% 
            User user = new User(name, email, password, reg_date, admin, card_num, card_exp);
            session.setAttribute("user", user);
        %>
    </body>
</html>
