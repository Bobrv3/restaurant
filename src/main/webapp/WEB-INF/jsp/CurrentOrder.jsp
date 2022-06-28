<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <fmt:setLocale value="${sessionScope.local}" />
            <fmt:setBundle basename="local" var="loc" />

            <fmt:message bundle="${loc}" key="local.label.yourOrder" var="yourOrder_lbl" />
            <fmt:message bundle="${loc}" key="local.button.placeOrder" var="placeOrder_btn" />

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="utf-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Your order</title>
                <link rel="stylesheet" href="css/CurrentOrder.css">
            </head>

            <body>
                <jsp:include page="/WEB-INF/jsp/Header.jsp" />

                <c:if test="${order != null}">
                    <h1>${yourOrder_lbl}:</h1>

                    <form style="position: relative;" action="restaurant" method="get" id="placeOrderForm">
                        <input type="hidden" name="command" value="place_order">

                        <table>
                            <c:forEach items="${order.getOrderList().keySet()}" var="orderedDish">
                                <tr>
                                    <td>
                                        <h3 class="DishName">
                                            <li />${orderedDish.name}
                                        </h3>
                                        ${orderedDish.description}
                                    </td>
                                    <td>
                                        ${orderedDish.price}
                                    </td>
                                    <td>
                                        <input type="hidden" name="dish_id" value="${orderedDish.id}">

                                        <button onclick="reduceOne(event)">-</button>
                                        <input type="text" name="quantity"
                                            value="${order.getOrderList().get(orderedDish)}" required>
                                        <button onclick="addOne(event)">+</button> <br>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>

                        <input type="submit" value="${placeOrder_btn}" style="position: relative; left: 80%;">
                    </form>
                </c:if>

                <c:if test="${order == null}">
                    <h1>Your order is empty</h1>
                </c:if>

                <jsp:include page="/WEB-INF/jsp/Footer.jsp" />
            </body>

            <script src="../../js/CurrentOrder.js"></script>

            </html>