<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Authorization</title>
</head>
<body>
	<form action="Authorization" method="get">
		<input type="hidden" name="command" value="authorization"/>
		
		Enter login: <input type="text" name="login" value=""/><br>
		Enter password: <input type="password" name="password" value=""/><br>
		
		<input type="submit" value="Sign in">
	</form>
</body>
</html>