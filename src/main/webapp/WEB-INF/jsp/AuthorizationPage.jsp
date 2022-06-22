<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Authorization Page</title>
    <link rel="stylesheet" href="css/AuthorizationFailed.css">
</head>
<body>
	<form action="restaurant" method="post">
		<input type="hidden" name="command" value="authorization"/>

        <c:if test="${invalidSignIn == true}">
            <label id="error">Invalid login or password</label><br>
        </c:if>

        <label>Enter login: </label>
        <input type="text" name="login" value=""/><br>
        <label>Enter password: </label>
        <input type="password" name="password" value=""/><br>

        <input type="submit" value="sign in">
	</form>
</body>
</html>