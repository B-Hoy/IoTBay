<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="uts.iotbay.LoginService"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="java.io.*"%>

<%
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    boolean isAuthenticated = false;

    if (email != null && password != null) {
        LoginService loginService = new LoginService();
        isAuthenticated = loginService.authenticate(email, password); // Using authenticate method from LoginService
    }

    if (isAuthenticated) {
        // Redirect to main.html if authentication is successful
        response.sendRedirect("main.html");
    } else {
        // Display error message if authentication fails
        out.println("<p>Incorrect email or password.</p>");
    }
%>
