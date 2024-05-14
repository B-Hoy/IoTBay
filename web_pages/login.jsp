<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.UserLogEntry"%>
<%@page import="uts.iotbay.Product"%>
<%@ page import="java.util.Random" %>
<%
    // Initialize database
    Database db = (Database) application.getAttribute("database");
    if (db == null) {
        db = new Database();
        application.setAttribute("database", db);
    }

    // Get email and password from form fields
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    // Debug statements
    System.out.println("Email: " + email);
    System.out.println("Password: " + password);

    // Handle login logic
    if (email != null && password != null) {
        // Authenticate user using form fields
        int local_session_id = db.authenticateUser(email, password);
        System.out.println("Local session ID: " + local_session_id);
        if (local_session_id != -1) {
            // Successful login
            System.out.println("Successful login");

            // Set session ID
            session.setAttribute("session_id", local_session_id);

            // Redirect to main page
            response.sendRedirect("main.jsp");
            return; // Ensure the rest of the JSP is not processed
        } else {
            // If login fails, redirect back to login.html with error message
            System.out.println("Login failed");
            response.sendRedirect("login.html?error=Incorrect email or password");
            return; // Ensure the rest of the JSP is not processed
        }
    } else {
        // If no credentials provided, redirect back to login.html with error message
        System.out.println("Missing credentials");
        response.sendRedirect("login.html?error=Please fill in all fields");
        return; // Ensure the rest of the JSP is not processed
    }
%>
