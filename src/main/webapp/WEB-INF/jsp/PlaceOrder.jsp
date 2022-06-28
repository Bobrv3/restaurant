<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <fmt:setLocale value="${sessionScope.local}" />
            <fmt:setBundle basename="local" var="loc" />

            <fmt:message bundle="${loc}" key="local.legend.order" var="orderFmt" />
            <fmt:message bundle="${loc}" key="local.h1.placingAnOrder" var="placingAnOrderFmt" />
            <fmt:message bundle="${loc}" key="local.h2.methodOfReceiving" var="methodOfReceivingFmt" />
            <fmt:message bundle="${loc}" key="local.label.takeaway" var="takeawayFmt" />
            <fmt:message bundle="${loc}" key="local.label.inPlace" var="inPlaceFmt" />

            <fmt:message bundle="${loc}" key="local.label.paymentMethod" var="paymentMethodFmt" />
            <fmt:message bundle="${loc}" key="local.label.totalPrice" var="totalPriceFmt" />
            <fmt:message bundle="${loc}" key="local.label.paymentByCardOnline" var="paymentByCardOnlineFmt" />
            <fmt:message bundle="${loc}" key="local.label.paymentByCardInPlace" var="paymentByCardInPlaceFmt" />
            <fmt:message bundle="${loc}" key="local.label.paymentByCash" var="paymentByCashFmt" />
            <fmt:message bundle="${loc}" key="local.label.placeOrder" var="placeOrderFmt" />

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
                        <h1>${placingAnOrderFmt}</h1>
                        <form id="placingOrder" action="finishingTheOrder" method="get">
                            <h2>${methodOfReceivingFmt}</h2>
                            <label for="takeaway">${takeawayFmt}</label>
                            <input type="radio" name="obtaining" id="takeaway" value="takeaway" checked><br>
                            <label for="inPlace">${inPlaceFmt}</label>
                            <input type="radio" name="obtaining" id="inPlace" value="inPlace">

                            <h2>${paymentMethodFmt}</h2>
                            <label for="paymentByCash">${paymentByCashFmt}</label>
                            <input type="radio" id="paymentByCash" name="paymentBy" value="cash" checked><br>
                            <label for="paymentByCardOnline">${paymentByCardOnlineFmt}</label>
                            <input type="radio" id="paymentByCardOnline" name="paymentBy" value="cardOnline"><br>
                            <label for="paymentByCardInPlace">${paymentByCardInPlaceFmt}</label>
                            <input type="radio" id="paymentByCardInPlace" name="paymentBy" value="cardInPlace"><br>
                            <br>
                            <input type="submit" value="${placeOrderFmt}">
                        </form>
                    </div>
                    <div>
                        <fieldset>
                            <legend>${orderFmt}</legend>

                            <c:forEach items="${order.getOrderList().keySet()}" var="orderedDish">
                                <label class="dishName">${orderedDish.name}
                                    x${order.getOrderList().get(orderedDish)}</label>
                                <label class="dishPrice">${orderedDish.price}</label>
                                <br>
                            </c:forEach>
                            <hr>

                            <label>${totalPriceFmt}: ${order.getTotalPrice()}</label>

                        </fieldset>
                    </div>
                </div>

                <jsp:include page="/WEB-INF/jsp/Footer.jsp" />
            </body>

            </html>