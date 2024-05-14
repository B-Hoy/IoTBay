<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="uts.iotbay.Database"%>
<%@ page import="uts.iotbay.User"%>
<%@ page import="java.io.*"%>

<%
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    // Retrieve the database object
    Database db = (Database) application.getAttribute("database");

    // Check if email and password are provided
    if (email != null && password != null) {
        // Authenticate user using LoginService
        LoginService loginService = new LoginService();
        boolean isAuthenticated = loginService.authenticate(email, password);

        // Check if authentication is successful
        if (isAuthenticated) {
            // Successful login
            User user = db.get_user(email); // Retrieve user details
            session.setAttribute("user", user);
            response.sendRedirect("main.html");
        } else {
            // Failed login
            out.println("<p>Incorrect email or password.</p>");
        }
    } else {
        // Display error message if email or password is not provided
        out.println("<p>Please provide email and password.</p>");
    }
%>
