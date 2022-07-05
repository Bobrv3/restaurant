<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <fmt:setLocale value="${sessionScope.local}" />
            <fmt:setBundle basename="local" var="loc" />

            <fmt:message bundle="${loc}" key="local.txt.personalInfo" var="personalInfoFmt" />
            <fmt:message bundle="${loc}" key="local.label.name" var="nameFmt" />
            <fmt:message bundle="${loc}" key="local.label.phoneNumber" var="phoneNumberFmt" />
            <fmt:message bundle="${loc}" key="local.label.email" var="emailFmt" />

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Account</title>
                <link rel="stylesheet" href="css/PersonalInfo.css">
            </head>

            <body>

                <jsp:include page="/WEB-INF/jsp/Header.jsp" />

                <c:if test="${userData == null}">
                    <jsp:forward page="/restaurant">
                        <jsp:param name="command" value="print_user_registr_data" />
                    </jsp:forward>
                </c:if>

                <a href="/showAccount">
                    <img src="../../images/goBack.png" alt="goBack" id="goBackImg">
                </a>

                <div class="wrapper">
                    <main class="main">

                        <h2>${personalInfoFmt}</h2>

                        <div class="grid-container">
                            <div>${nameFmt}</div>
                            <div>
                                <c:if test="${!param.editName}">
                                    ${userData.name}
                                </c:if>
                                <c:if test="${param.editName}">
                                    <form action="restaurant" method="get" id="editUser">
                                        <input type="hidden" name="command" value="edit_user">
                                        <input type="text" name="userName" value="${userData.name}" required>
                                    </form>
                                </c:if>
                            </div>
                            <div>
                                <c:if test="${!param.editName}">
                                    <a href="/personalInfo?editName=true">
                                        <img src="../../images/edit.png" alt="edit name" id="editImg">
                                    </a>
                                </c:if>
                                <c:if test="${param.editName}">
                                    <input form="editUser" type="image" src="../../images/save.png" id="editImg">
                                </c:if>
                            </div>

                            <div>${phoneNumberFmt}</div>
                            <div>
                                <c:if test="${!param.editPhone}">
                                    ${userData.phoneNumber}
                                </c:if>
                                <c:if test="${param.editPhone}">
                                    <input type="text" name="phoneNumber" value="${userData.phoneNumber}" required>
                                </c:if>
                            </div>
                            <div>
                                <c:if test="${!param.editPhone}">
                                    <a href="/personalInfo?editPhone=true">
                                        <img src="../../images/edit.png" alt="edit phoneNumber" id="editImg">
                                    </a>
                                </c:if>
                                <c:if test="${param.editPhone}">
                                    <input form="editUser" type="image" src="../../images/save.png" id="editImg">
                                </c:if>
                            </div>

                            <div>${emailFmt}</div>
                            <div>
                                <c:if test="${!param.editEmail}">
                                    ${userData.email}
                                </c:if>
                                <c:if test="${param.editEmail}">
                                    <input type="text" name="email" value="${userData.email}" required>
                                </c:if>
                            </div>
                            <div>
                                <c:if test="${!param.editEmail}">
                                    <a href="/personalInfo?editEmail=true">
                                        <img src="../../images/edit.png" alt="edit email" id="editImg">
                                    </a>
                                </c:if>
                                <c:if test="${param.editEmail}">
                                    <input form="editUser" type="image" src="../../images/save.png" id="editImg">
                                </c:if>
                            </div>

                        </div>
                    </main>
                </div>

                <jsp:include page="/WEB-INF/jsp/Footer.jsp" />

            </body>

            </html>