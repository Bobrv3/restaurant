<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="local" var="loc" />

        <fmt:message bundle="${loc}" key="local.button.signUp" var="signUp_btn" />
        <fmt:message bundle="${loc}" key="local.button.signIn" var="signIn_btn" />
        <fmt:message bundle="${loc}" key="local.button.ru" var="ru_button" />
        <fmt:message bundle="${loc}" key="local.button.en" var="en_button" />
        <fmt:message bundle="${loc}" key="local.link.Menu" var="menu_link" />

        <link rel="stylesheet" href="css/Header.css">

        <header>
            <div>
                <form action="restaurant" method="get">
                    <input type="hidden" name="command" value="change_locale">
                    <input type="hidden" name="locale" value="en">
                    <input type="submit" value="${en_button}">
                </form>
                <form action="restaurant" method="get">
                    <input type="hidden" name="command" value="change_locale">
                    <input type="hidden" name="locale" value="ru">
                    <input type="submit" value="${ru_button}">
                </form>
            </div>
            <div>
                <ul>
                    <li><a href="/restaurant?command=move_to_home">${menu_link}</a></li>
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