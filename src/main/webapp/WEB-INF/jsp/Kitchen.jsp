<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <fmt:setLocale value="${sessionScope.local}" />
            <fmt:setBundle basename="local" var="loc" />

            <fmt:message bundle="${loc}" key="local.h2.methodOfReceiving" var="methodOfReceivingFmt" />
            <fmt:message bundle="${loc}" key="local.txt.kitchen" var="kitchenFmt" />
            <fmt:message bundle="${loc}" key="local.txt.done" var="doneFmt" />
            <fmt:message bundle="${loc}" key="local.txt.dish" var="dishFmt" />
            <fmt:message bundle="${loc}" key="local.txt.orders" var="ordersFmt" />

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>${kitchenFmt}</title>
                <link rel="stylesheet" href="css/Kitchen.css">
            </head>

            <body>

                <jsp:include page="/WEB-INF/jsp/Header.jsp" />

                <div class="wrapper">
                    <main class="main">
                        <h1>${ordersFmt}</h1>

                        <table>
                            <th>№</th>
                            <th>${dishFmt}</th>
                            <th>${methodOfReceivingFmt}</th>
                            <th>${doneFmt}</th>

                            <c:forEach items="${ordersForCooking}" var="order">
                                <tr>
                                    <td>
                                        ${order.orderId}
                                    </td>
                                    <td>
                                        ${order.dishName}
                                    </td>
                                    <td>
                                        ${order.methodOfReceiving}
                                    </td>
                                    <td>
                                        <form action="restaurant" method="post">
                                            <input type="hidden" name="command" value="order_cooked">
                                            <input type="hidden" name="cookedOrderId" value="${order.orderId}">
                                            <input type="submit" value="${doneFmt} &#10004;">
                                        </form>
                                    </td>
                                </tr>

                            </c:forEach>
                        </table>

                    </main>
                </div>

                <jsp:include page="/WEB-INF/jsp/Footer.jsp" />

            </body>

            </html>