<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uts.iotbay.Database"%>
<%@ page import="uts.iotbay.User"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <link rel="stylesheet" type="text/css" href="login.css">
    </head>
    <body>
        <h1>Login</h1>
        <form method="post">
            Email: <input type="text" name="email" required><br>
            Password: <input type="password" name="password" required><br>
            <input type="submit" value="Login">
        </form>
        <%
            // Initialize database if not already done
            Database db = (Database)application.getAttribute("database");
            if (db == null) {
                db = new Database();
                application.setAttribute("database", db);
            }

            // Process the login form submission
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                boolean hasError = false;

                // Validate email format
                if (!email.contains("@")) {
                    out.println("<p>Invalid email format. Please include '@'.</p>");
                    hasError = true;
                }

                if (!hasError) {
                    // Validate user credentials
                    if (db.email_match_password(email, password)) {
                        int session_id = db.add_user_login(email, password);
                        if (session_id != -1) {
                            session.setAttribute("session_id", session_id);
                            session.setAttribute("email", email);
                            response.sendRedirect("main.jsp");
                            return;
                        } else {
                            out.println("<p>Error logging in. Please try again.</p>");
                        }
                    } else {
                        out.println("<p>Invalid email or password. Please try again.</p>");
                    }
                }
            }
        %>
    </body>
</html>
