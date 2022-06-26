<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Registration page</title>
            <link rel="stylesheet" href="css/RegistrationPage.css">
        </head>

        <body>
            <form action="restaurant" method="post">
                <input type="hidden" name="command" value="registration" />
                <input type="hidden" name="roleId" value="" /><br>

                <fieldset>
                    <legend>Registration</legend>

                    <c:if test="${invalidSignUp == true}">
                        <label id="error">A user with this name already exists</label><br>
                    </c:if>

                    <p>
                        <label>login: </label>
                        <input type="text" name="login" value="" required /><br>
                    </p>
                    <p>
                        <label>password: </label>
                        <input type="password" name="password" value="" required /><br>
                    </p>
                    <p>
                        <label>name: </label>
                        <input type="text" name="name" value="" required /><br>
                    </p>
                    <p>
                        <label>phone number: </label>
                        <input type="text" placeholder="+375" name="phoneNumber" value="" required /><br>
                    </p>
                    <p>
                        <label>email: </label>
                        <input type="email" name="email" value="" /><br>
                    </p>
                    <p>
                        <input type="submit" value="Sign up" id="signUp_button">
                    </p>
                </fieldset>
            </form>
        </body>

        </html>