<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="local" var="loc" />

        <fmt:message bundle="${loc}" key="local.button.name.signUp" var="signUp_btn" />
        <fmt:message bundle="${loc}" key="local.button.name.signIn" var="signIn_btn" />
        <fmt:message bundle="${loc}" key="local.button.name.ru" var="ru_button" />
        <fmt:message bundle="${loc}" key="local.button.name.en" var="en_button" />

        <link rel="stylesheet" href="css/Header.css">

        <header>
            <div>
                <form action="restaurant" method="get">
                    <input type="hidden" name="command" value="change_locale_to_eng">
                    <input type="submit" value="${en_button}">
                </form>
                <form action="restaurant" method="get">
                    <input type="hidden" name="command" value="change_locale_to_ru">
                    <input type="submit" value="${ru_button}">
                </form>
            </div>
            <div>
                <ul>
                    <li><a href="/index.jsp">Menu</a></li>
                </ul>
            </div>
            <div>
                <form action="restaurant" method="get">
                    <input type="hidden" name="command" value="move_to_authorization_page">
                    <input type="submit" value="${signIn_btn}" id="signIn_button">
                </form>
                <form action="restaurant" method="get">
                    <input type="hidden" name="command" value="move_to_registration_page">
                    <input type="submit" value="${signUp_btn}" id="signUp_button">
                </form>

                <c:if test="${order != null}">
                    <a href="/restaurant?command=show_current_order">
                        <img class="linkImageHeader" src="../../images/order.png">${quantityOfDishes}
                    </a>
                </c:if>
            </div>
        </header>