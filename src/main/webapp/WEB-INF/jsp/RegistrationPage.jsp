<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration page</title>
    <link rel="stylesheet" href="css/RegistrationFailed.css">
</head>
<body>
	<form action="restaurant" method="post">
		<input type="hidden" name="command" value="registration"/>

        <c:if test="${invalidSignUp == true}">
            <label id="error">A user with this name already exists</label><br>
        </c:if>

        <label>login: </label>
            <input type="text" name="login" value=""/><br>
        <label>password: </label>
            <input type="password" name="password" value=""/><br>
        <label>name: </label>
            <input type="text" name="name" value=""/><br>
        <label>phone number: </label>
            <input type="text" placeholder="+375" name="phoneNumber" value=""/><br>
        <label>email: </label>
            <input type="email" name="email" value=""/><br>

        <input type="hidden" name="roleId" value=""/><br>

        <input type="submit" value="Sign up">
	</form>
</body>
</html>