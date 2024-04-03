<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IoTBay Registration</title>
    </head>
    <body>
        <b>Register</b>
        <form method="POST" action="/IoTBay/welcome.jsp">
        <table>
            <tr>
                <td><label for="email">Email:</label></td>
                <td><input type="text" id="email" name="email"></td>
            </tr>
            <tr>
                <td><label for="name">Name:</label></td>
                <td><input type="text" id="name" name="name"></td>
            </tr>
            <tr>
                <td><label for="password">Password:</label></td>
                <td><input type="password" id="password" name="password"></td>
            </tr>
            <tr>
                <td><label for="card_num">Debit/Credit Card Number:</label></td>
                <td><input type="text" id="card_num" name="card_num"></td>
            </tr>
            <tr>
                <td><label for="card_exp">Debit/Credit Card Expiry Date (MM/YY):</label></td>
                <td><input type="text" id="card_exp" name="card_exp"></td>
            </tr>
        </table>
        <input type="hidden" id="admin" name="admin" value="0">  
        <input type="submit" value="Register">
        </form>
    </body>
</html>

