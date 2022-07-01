<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="local" var="loc" />


        <fmt:message bundle="${loc}" key="local.link.Menu" var="menu_link" />
        <fmt:message bundle="${loc}" key="local.button.add" var="btn_add" />
        <fmt:message bundle="${loc}" key="local.button.save" var="saveFmt" />

        <link rel="stylesheet" href="css/Menu.css">

        <h1>${menu_link}</h1>

        <c:if test="${menu == null}">
            <jsp:forward page="/restaurant">
                <jsp:param name="command" value="get_menu" />
            </jsp:forward>
        </c:if>

        <c:forEach items="${categories}" var="category">
            <h2 class="CategoryName">${category.name}
                <c:if test="${user != null && user.roleId == 1}">
                    <a href="/restaurant?command=edit_category&&dishId=${dish.id}">
                        <img src="../../images/edit.png" alt="edit" class="imgInTd">
                    </a>
                </c:if>
            </h2>



            <table>
                <c:forEach items="${menu.getDishes()}" var="dish">
                    <c:if test="${dish.category_id == category.id}">
                        <tr>
                            <c:if test="${param.editedDishId == dish.id}">
                                <form action="restaurant" method="post">
                                    <input type="hidden" name="command" value="editDish">
                                    <input type="hidden" name="dishId" value="${dish.id}">

                                    <td>
                                        <h3 class="DishName">
                                            <li /><input type="text" name="dishName" value="${dish.name}">
                                        </h3>
                                        <textarea name="description" cols="90" rows="3">${dish.description}</textarea>
                                        <input type="submit" value="${saveFmt}">
                                    </td>
                                    <td>
                                        <input type="text" name="price" value="${dish.price}" id="editedPrice">
                                    </td>
                                </form>
                            </c:if>

                            <c:if test="${param.editedDishId != dish.id}">
                                <td>
                                    <h3 class="DishName">
                                        <li />${dish.name}
                                    </h3>
                                    ${dish.description}
                                </td>
                                <td>
                                    ${dish.price}
                                </td>
                            </c:if>

                            <td>

                                <form action="restaurant" method="post">
                                    <input type="hidden" name="command" value="add_to_order">
                                    <input type="hidden" name="dish_id" value="${dish.id}">

                                    <button onclick="reduceOne(event)">-</button>
                                    <input type="text" name="quantity" value="1" required>
                                    <button onclick="addOne(event)">+</button> <br>

                                    <input type="submit" value="${btn_add}" class="yellow_button" id="addToOrderBtn">
                                </form>
                            </td>
                            <td>
                                <c:if test="${user != null && user.roleId == 1}">
                                    <a href="/restaurant?command=remove_from_menu&&dishId=${dish.id}">
                                        <img src="../../images/remove.png" alt="remove" class="imgInTd">
                                    </a>

                                    <a href="/home?editedDishId=${dish.id}">
                                        <img src="../../images/edit.png" alt="edit" class="imgInTd">
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </c:forEach>

        <script src="../../js/Menu.js"></script>