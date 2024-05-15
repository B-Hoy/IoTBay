<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uts.iotbay.Database"%>
<%@ page import="uts.iotbay.User"%>
<%@ page import="uts.iotbay.UserLogEntry"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
    </head>
    <body>
        <h1>Login</h1>
        <form method="post">
            Email: <input type="text" name="email" required><br>
            Password: <input type="password" name="password" required><br>
            <input type="submit" value="Login">
        </form>
        <%
            // This check is *required* to use the db, otherwise data isn't fully persistent
            Database db = (Database)application.getAttribute("database");
            if (db == null){
                db = new Database();
                application.setAttribute("database", db);
            }

            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

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
        %>
    </body>
</html>
