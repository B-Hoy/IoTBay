<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh" content="1; url=index.html">
        <title>IoTBay - Logout</title>
    </head>
    <body>
        You have logged out, redirecting you to the main page shortly...
        <% 
            session.invalidate();
            response.setIntHeader("Refresh", 3000);
            
        %>
    </body>
</html>
