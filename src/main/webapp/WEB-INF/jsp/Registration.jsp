<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration</title>
</head>
<body>
	<form action="Registration" method="post">
		<input type="hidden" name="command" value="registration"/>
		
		login: <input type="text" name="login" value=""/><br>
		password: <input type="password" name="password" value=""/><br>
		name: <input type="text" name="name" value=""/><br>
		phone number: <input type="text" placeholder="+375" name="phoneNumber" value=""/><br>
		email: <input type="email" name="email" value=""/><br>
		<input type="hidden" name="roleId" value=""/><br>
		
		<input type="submit" value="Sign up">
	</form>
</body>
</html>