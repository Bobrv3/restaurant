<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Main page</title>
    <link rel="stylesheet" href="css/Index.css">
</head>
<body>
    <div class="login">
        <form action="restaurant" method="get">
            <input type="hidden" name="command" value="move_to_authorization_page"/>
            <input type="submit" value="Sign in"/>
        </form>
        <form action="restaurant" method="get">
                <input type="hidden" name="command" value="move_to_registration_page"/>
                <input type="submit" value="Sign up"/>
        </form>
    </div>
    <h1>Main page</h1>
    <ul>
        <li>Dish</li>
        <li>Dish</li>
    </ul>
</body>
</html>