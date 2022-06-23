<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Main page</title>

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.button.name.signUp" var="signUp_btn"/>
    <fmt:message bundle="${loc}" key="local.button.name.signIn" var="signIn_btn"/>
    <fmt:message bundle="${loc}" key="local.button.name.ru" var="ru_button"/>
    <fmt:message bundle="${loc}" key="local.button.name.en" var="en_button"/>

    <link rel="stylesheet" href="css/Index.css">
</head>
<body>

    <div class="login">
        <form action="restaurant" method="get">
            <input type="hidden" name="command" value="move_to_authorization_page"/>
            <input type="submit" value="${signIn_btn}"/>
        </form>
        <form action="restaurant" method="get">
            <input type="hidden" name="command" value="move_to_registration_page"/>
            <input type="submit" value="${signUp_btn}"/>
        </form>
    </div><br>

    <div class="locale">
            <form action="restaurant" method="get">
                <input type="hidden" name="command" value="change_locale_to_eng"/>
                <input type="submit" value="${en_button}"/>
            </form>
            <form action="restaurant" method="get">
                <input type="hidden" name="command" value="change_locale_to_ru"/>
                <input type="submit" value="${ru_button}"/>
            </form>
    </div><br>

    <c:if test="${order != null}">
        <label style="float: right; border: 1px solid black; padding: 10px">
                Order: ${order.getDishes().size()}
        </label>
    </c:if>

    <jsp:include page="/WEB-INF/jsp/Menu.jsp"/>

</body>
</html>