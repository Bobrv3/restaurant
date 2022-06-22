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

    <h1>Menu</h1>

    <c:if test="${category == null}">
        <c:url value="/restaurant" var="controller">
            <c:param name="command" value="get_menu"/>
        </c:url>

        <c:redirect url = "${controller}"/>
    </c:if>

    <sql:setDataSource var="db" driver="com.mysql.cj.jdbc.Driver"
         url="jdbc:mysql://localhost:3306/restaurant"
         user="root"  password="1234"/>

    <sql:query dataSource="${db}" var="rs_category">
        SELECT * FROM categories ORDER BY id;
    </sql:query>

    <c:forEach items="${rs_category.rows}" var="category">
        <sql:query dataSource="${db}" var="rs_menu">
            SELECT name, description, price, category_id
            FROM menu
            where status != 1 AND category_id = ${category.id};
        </sql:query>

        <h2 class="CategoryName">${category.name}</h2>

        <table>
            <c:forEach items="${rs_menu.rows}" var="dish">
                <tr>
                    <td>
                        <h3 class="DishName"><li/>${dish.name}</h3>
                        ${dish.description}
                    </td>
                    <td>
                        ${dish.price}
                    </td>
                    <td>
                        <form action="restaurant" method="get">
                            <input type="hidden" name="command" value="add_${dish}_to_order"/>
                            <input type="checkbox"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:forEach>



</body>
</html>