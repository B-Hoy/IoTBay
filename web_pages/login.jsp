<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.UserLogEntry"%>
<%@page import="uts.iotbay.Product"%>
<%@page import="java.util.Random" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" href="login.css">
</head>
<body>
    <div class="container">
        <h1>Login</h1>
        
        <!-- Display error message if login attempt fails -->
        <p style="color: red;">
            <% if (request.getParameter("error") != null) { %>
                <%= request.getParameter("error") %>
            <% } %>
        </p>
        
        <form id="loginForm" action="login.jsp" method="POST">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>
            <button type="submit">Login</button>
            <p id="errorMessage" style="display: none;">Please fill in all fields.</p>
        </form>
    </div>
    <script>
        document.getElementById("loginForm").addEventListener("submit", function(event) {
            var email = document.getElementById("email").value;
            var password = document.getElementById("password").value;

            if (!email || !password) {
                document.getElementById("errorMessage").textContent = "Please fill in all fields.";
                document.getElementById("errorMessage").style.display = "block";
                event.preventDefault();
            }
        });
    </script>
</body>
</html>

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
            // If login fails, redirect back to login.jsp with error message
            System.out.println("Login failed");
            response.sendRedirect("login.jsp?error=Incorrect email or password");
            return; // Ensure the rest of the JSP is not processed
        }
    } else {
        // If no credentials provided, redirect back to login.jsp with error message
        System.out.println("Missing credentials");
        response.sendRedirect("login.jsp?error=Please fill in all fields");
        return; // Ensure the rest of the JSP is not processed
    }
%>
