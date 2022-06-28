<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <fmt:setLocale value="${sessionScope.local}" />
            <fmt:setBundle basename="local" var="loc" />

            <fmt:message bundle="${loc}" key="local.label.yourOrder" var="yourOrder_lbl" />

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="utf-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Your order</title>
                <link rel="stylesheet" href="css/PlaceOrder.css">
            </head>

            <body>
                <jsp:include page="/WEB-INF/jsp/Header.jsp" />
                <div class="grid-container">
                    <div>
                        <h1>Оформление заказа</h1>
                        <h2>Способ получения</h2>
                        <form action="restaurant" method="get">
                            <input type="hidden" name="command" value="MoveToPaymentMethod">
                            <label for="withYou">С собой</label>
                            <input type="radio" name="obtaining" id="withYou" value="withYou"><br>
                            <label for="inPlace">На месте</label>
                            <input type="radio" name="obtaining" id="inPlace" value="inPlace">
                            <input type="submit" value="Go to the payment method">
                        </form>
                    </div>
                    <div>
                        <fieldset>
                            <legend>${yourOrder_lbl}</legend>

                            <c:forEach items="${order.getOrderList().keySet()}" var="orderedDish">
                                <label class="dishName">${orderedDish.name}
                                    x${order.getOrderList().get(orderedDish)}</label>
                                <label class="dishPrice">${orderedDish.price}</label>
                                <br>
                            </c:forEach>
                            <hr>

                            <label>Итого: ${order.getTotalPrice()}</label>

                        </fieldset>
                    </div>
                </div>

                <jsp:include page="/WEB-INF/jsp/Footer.jsp" />
            </body>

            </html>