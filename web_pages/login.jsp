<%@page import="uts.iotbay.Database"%>
<%@page import="uts.iotbay.User"%>
<%@page import="uts.iotbay.LoginService"%>
<%@page import="java.io.PrintWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <form id="loginForm" action="login.jsp" method="POST">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>
            <button type="submit">Login</button>
            <p id="errorMessage" style="display: none;">Please fill in all fields.</p>
            <p id="loginErrorMessage" style="display: none;">Incorrect email or password.</p>
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

    <%//-- Database initialization --%>
    <% 
        Database db = (Database)application.getAttribute("database");
        if (db == null) {
            db = new Database();
            application.setAttribute("database", db);
            out.println("Had to make a new db :(");
        }

        String form_type = request.getParameter("form_type");
        if (form_type != null && form_type.equals("login_user")) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Authenticate user
            LoginService loginService = new LoginService();
            if (loginService.authenticate(db, email, password)) {
                // Successful login, create a session
                int local_session_id = db.add_user_login(email);
                if (local_session_id != -1) {
                    session.setAttribute("session_id", local_session_id);
                    response.sendRedirect("main.html"); // Redirect to main page
                    return; // Ensure the rest of the JSP is not processed
                }
            } else {
                // Login failed, display error message
                out.println("<script>document.getElementById('loginErrorMessage').style.display = 'block';</script>");
                return; // Ensure the rest of the JSP is not processed
            }
        }
    %>
</body>
</html>
