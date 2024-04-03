<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IoTBay - Login</title>
    </head>
    <body>
        <b>Login</b>
        <form method="POST" action="/IoTBay/welcome.jsp">
        <table>
            <tr>
                <td><label for="email">Email:</label></td>
                <td><input type="text" id="email" name="email"></td>
            </tr>
            <tr>
                <td><label for="password">Password:</label></td>
                <td><input type="password" id="password" name="password"></td>
            </tr>
        </table>
        <input type="submit" value="Login">
        </form>
    </body>
</html>
