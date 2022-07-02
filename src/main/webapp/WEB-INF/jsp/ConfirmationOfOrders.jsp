<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <fmt:setLocale value="${sessionScope.local}" />
            <fmt:setBundle basename="local" var="loc" />

            <fmt:message bundle="${loc}" key="local.txt.confirmationOfOrders" var="confirmationOfOrdersFmt" />
            <fmt:message bundle="${loc}" key="local.h2.methodOfReceiving" var="methodOfReceivingFmt" />
            <fmt:message bundle="${loc}" key="local.label.totalPrice" var="totalFmt" />
            <fmt:message bundle="${loc}" key="local.txt.date" var="dateFmt" />

            <fmt:message bundle="${loc}" key="local.txt.userName" var="userNameFmt" />
            <fmt:message bundle="${loc}" key="local.txt.userPhoneNumber" var="userPhoneNumberFmt" />
            <fmt:message bundle="${loc}" key="local.txt.userEmail" var="userEmailFmt" />

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>${confirmationOfOrdersFmt}</title>
                <link rel="stylesheet" href="css/ConfirmationOfOrders.css">
            </head>

            <body>

                <jsp:include page="/WEB-INF/jsp/Header.jsp" />

                <div class="wrapper">
                    <main class="main">
                        <h1>${confirmationOfOrdersFmt}</h1>

                        <table>
                            <th>№</th>
                            <th>${totalFmt}</th>
                            <th>${dateFmt}</th>
                            <th>${methodOfReceivingFmt}</th>
                            <th>${userNameFmt}</th>
                            <th>${userPhoneNumberFmt}</th>
                            <th>${userEmailFmt}</th>

                            <c:forEach items="${ordersForConfirmation}" var="order">
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
                                    <td>
                                        ${userData.name}
                                    </td>
                                    <td>
                                        ${userData.phoneNumber}
                                    </td>
                                    <td>
                                        ${userData.email}
                                    </td>
                                </tr>

                            </c:forEach>
                        </table>

                    </main>
                </div>

                <jsp:include page="/WEB-INF/jsp/Footer.jsp" />

            </body>

            </html>