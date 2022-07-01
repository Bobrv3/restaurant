<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <fmt:setLocale value="${sessionScope.local}" />
            <fmt:setBundle basename="local" var="loc" />

            <fmt:message bundle="${loc}" key="local.txt.historyOfOrders" var="historyOfOrdersFmt" />
            <fmt:message bundle="${loc}" key="local.h2.methodOfReceiving" var="methodOfReceivingFmt" />
            <fmt:message bundle="${loc}" key="local.label.totalPrice" var="totalFmt" />
            <fmt:message bundle="${loc}" key="local.txt.date" var="dateFmt" />

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>History of orders</title>
                <link rel="stylesheet" href="css/HistoryOfOrders.css">
            </head>

            <body>

                <jsp:include page="/WEB-INF/jsp/Header.jsp" />

                <div class="wrapper">
                    <main class="main">
                        <h1>${historyOfOrdersFmt}</h1>

                        <c:if test="${ordersHistory == null}">
                            <jsp:forward page="/restaurant">
                                <jsp:param name="command" value="get_history_of_orders" />
                            </jsp:forward>
                        </c:if>

                        <table>
                            <th>№</th>
                            <th>${totalFmt}</th>
                            <th>${dateFmt}</th>
                            <th>${methodOfReceivingFmt}</th>

                            <c:forEach items="${ordersHistory}" var="order">
                                <tr>

                                    <td>
                                        ${order.id}
                                    </td>
                                    <td>
                                        ${order.totalPrice}
                                    </td>
                                    <td>
                                        ${order.dateTime}
                                    </td>
                                    <td>
                                        ${order.methodOfReceiving}
                                    </td>
                                </tr>

                            </c:forEach>
                        </table>

                    </main>
                </div>

                <jsp:include page="/WEB-INF/jsp/Footer.jsp" />

            </body>

            </html>