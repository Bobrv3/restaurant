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
	<form action="restaurant" method="get">
		<input type="hidden" name="command" value="authorization"/>

        <label>Enter login: </label>
        <input type="text" name="login" value=""/><br>
        <label>Enter password: </label>
        <input type="password" name="password" value=""/><br>

        <input type="submit" value="sign in">
	</form>
</body>
</html>