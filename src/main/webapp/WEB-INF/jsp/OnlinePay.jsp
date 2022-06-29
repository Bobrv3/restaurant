<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <fmt:setLocale value="${sessionScope.local}" />
            <fmt:setBundle basename="local" var="loc" />

            <fmt:message bundle="${loc}" key="local.button.toPay" var="toPayFmt" />

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="utf-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Your order</title>
                <link rel="stylesheet" href="css/OnlinePay.css">
            </head>

            <body>
                <jsp:include page="/WEB-INF/jsp/Header.jsp" />

                <a href="/finishingTheOrder">
                    <button>${toPayFmt}</button>
                </a>

                <jsp:include page="/WEB-INF/jsp/Footer.jsp" />
            </body>

            </html>