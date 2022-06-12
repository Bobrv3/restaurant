<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Authorization</title>
    <link rel="stylesheet" href="css/AuthorizationFailed.css">
</head>
<body>
	<form action="restaurant" method="get">
		<input type="hidden" name="command" value="authorization"/>
		
		<label id="error">Invalid login or password</label><br>
		<label>Enter login: </label>
		<input type="text" name="login" value=""/><br>
		<label>Enter password: </label>
		<input type="password" name="password" value=""/><br>

		<input type="submit" value="sign in">
	</form>
</body>
</html>