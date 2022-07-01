<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <fmt:setLocale value="${sessionScope.local}" />
            <fmt:setBundle basename="local" var="loc" />

            <fmt:message bundle="${loc}" key="local.label.yourOrder" var="yourOrder_lbl" />
            <fmt:message bundle="${loc}" key="local.button.goToPlacingAnOrder" var="GoToPlacingAnOrder_btn" />

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

                <div class="wrapper">
                    <main class="main">
                        <c:if test="${order != null}">
                            <h1>${yourOrder_lbl}:</h1>

                            <form action="restaurant" method="get" id="placeOrderForm">
                                <input type="hidden" name="command" value="move_to_place_order">

                                <table>
                                    <c:forEach items="${order.getOrderList().keySet()}" var="orderedDish">
                                        <tr>
                                            <td>
                                                <h3 class="DishName">
                                                    <li />${orderedDish.name}
                                                </h3>
                                                ${orderedDish.description}
                                            </td>
                                            <td style="width: 5%;">
                                                <a href="/restaurant?command=removeFromOrder&dishId=${orderedDish.id}">
                                                    <img style="width: 100%;" src="../../images/remove.png"
                                                        alt="remove">
                                                </a>
                                            </td>
                                            <td>
                                                ${orderedDish.price}
                                            </td>
                                            <td>
                                                <input type="hidden" name="dishId" value="${orderedDish.id}">

                                                <button onclick="reduceOne(event)">-</button>
                                                <input type="text" name="quantity"
                                                    value="${order.getOrderList().get(orderedDish)}" required>
                                                <button onclick="addOne(event)">+</button> <br>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>

                                <input type="submit" value="${GoToPlacingAnOrder_btn}" id="placeOrderBtn">
                            </form>
                        </c:if>

                        <c:if test="${order == null}">
                            <h1>Your order is empty</h1>
                        </c:if>
                    </main>
                </div>

                <jsp:include page="/WEB-INF/jsp/Footer.jsp" />

            </body>

            <script src="../../js/CurrentOrder.js"></script>

            </html>