<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="local" var="loc" />


        <fmt:message bundle="${loc}" key="local.link.Menu" var="menu_link" />
        <fmt:message bundle="${loc}" key="local.button.add" var="btn_add" />
        <fmt:message bundle="${loc}" key="local.button.save" var="saveFmt" />
        <fmt:message bundle="${loc}" key="local.link.addCategory" var="addCategoryFmt" />
        <fmt:message bundle="${loc}" key="local.txt.dishAddedMsg" var="dishAddedMsgFmt" />

        <link rel="stylesheet" href="css/Menu.css">

        <h1>${menu_link}</h1>

        <c:if test="${menu.dishes == null}">
            <jsp:forward page="/restaurant">
                <jsp:param name="command" value="get_menu" />
            </jsp:forward>
        </c:if>

        <c:if test="${param.invalidCategory || param.invalidDish}">
            <h3 id="errorMsg">${param.errMsgUpdCategory}</h3>
            <h3 id="errorMsg">${param.errMsgUpdDish}</h3>
        </c:if>

        <c:forEach items="${categories}" var="category">
            <c:if test="${param.editedCategory == category.id}">
                <form action="restaurant" method="post" class="CategoryName">
                    <input type="hidden" name="command" value="edit_category">
                    <input type="hidden" name="editedCategoryId" value="${category.id}">

                    <input type="text" name="categoryName" value="${category.name}" required>
                    <input type="image" src="../../images/save.png" alt="save" id="imgInEditCategory">
                </form>
            </c:if>
            <c:if test="${param.editedCategory != category.id}">
                <h2 class="CategoryName">
                    ${category.name}
                    <c:if test="${user.roleId == 1}">
                        <a href="/home?editedCategory=${category.id}">
                            <img src="../../images/edit.png" alt="edit" class="imgInTd">
                        </a>

                        <form action="restaurant" method="post">
                            <input type="hidden" name="command" value="remove_category">
                            <input type="hidden" name="categoryId" value="${category.id}">

                            <input type="image" src="../../images/remove.png" alt="remove" class="imgInTd">
                        </form>
                    </c:if>
                </h2>
            </c:if>


            <table>
                <c:forEach items="${menu.getDishes()}" var="dish">
                    <c:if test="${dish.categoryId == category.id}">
                        <tr>
                            <c:if test="${param.editedDishId == dish.id}">
                                <form action="restaurant" method="post">
                                    <input type="hidden" name="command" value="edit_dish">
                                    <input type="hidden" name="editedDishId" value="${dish.id}">

                                    <td>
                                        <h3 class="DishName">
                                            <li /><input type="text" name="dishName" value="${dish.name}" required>
                                        </h3>
                                        <textarea name="description" cols="70" rows="3"
                                            required>${dish.description}</textarea>
                                        <input type="submit" value="${saveFmt}">
                                    </td>
                                    <td>
                                        <input type="text" name="price" value="${dish.price}" required id="editedPrice">
                                    </td>
                                    <td>
                                        <img src="${dish.photoLink}" alt="photo of ${dish.name}" class="dishPhoto">
                                        <br>
                                        <input type="file" name="photoLink" accept="images/*" required>
                                    </td>
                                </form>
                            </c:if>

                            <c:if test="${param.editedDishId != dish.id}">
                                <td class="col1">
                                    <strong style="font-size: 15px;">
                                        <li />${dish.name}
                                    </strong>
                                    (${dish.description}) <br>

                                </td>
                                <td class="col2">
                                    <strong>${dish.price}</strong>
                                </td>
                                <td>
                                    <img src="${dish.photoLink}" alt="photo of ${dish.name}" class="dishPhoto">
                                </td>
                            </c:if>

                            <td class="col3">
                                <form action="restaurant" method="post">
                                    <input type="hidden" name="command" value="add_to_order">
                                    <input type="hidden" name="dish_id" value="${dish.id}">

                                    <button onclick="reduceOne(event)">-</button>
                                    <input type="text" name="quantity" value="1" required>
                                    <button onclick="addOne(event)">+</button> <br>

                                    <input type="submit" value="${btn_add}" class="yellow_button" id="addToOrderBtn">
                                </form>
                                <c:if test="${param.addedToOrder == true && dish.id == param.addedDishId}">
                                    <div style="color: rgb(27, 167, 27);">${dishAddedMsgFmt} &#10004</div>
                                </c:if>
                            </td>

                            <c:if test="${user.roleId == 1}">
                                <td style="padding-right: 30px;">
                                    <a href="/restaurant?command=remove_from_menu&&dishId=${dish.id}">
                                        <img src="../../images/remove.png" alt="remove" class="imgInTd">
                                    </a>

                                    <a href="/home?editedDishId=${dish.id}">
                                        <img src="../../images/edit.png" alt="edit" class="imgInTd">
                                    </a>
                                </td>
                            </c:if>

                        </tr>
                    </c:if>
                </c:forEach>

                <c:if test="${user.roleId == 1}">

                    <c:if test="${!param.createDish || (param.createDish && param.categoryForAdd != category.id)}">
                        <tr>
                            <td colspan="5">
                                <a href="/home?createDish=true&categoryForAdd=${category.id}">
                                    <img src="../../images/addContent.png" alt="add dish" id="imgAddContent">
                                </a>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${param.createDish && (param.categoryForAdd == category.id)}">
                        <tr>
                            <form action="restaurant" method="post">
                                <input type="hidden" name="command" value="add_dish">
                                <input type="hidden" name="categoryForAdd" value="${category.id}">
                                <td>
                                    <h3 class="DishName">
                                        <li /><input type="text" name="dishName" value="" placeholder="Dish name"
                                            required>
                                    </h3>
                                    <textarea name="description" cols="60" rows="3" placeholder="Description..."
                                        required></textarea>
                                    <input type="submit" value="${btn_add}">
                                </td>
                                <td>
                                    <input type="text" name="price" value="" placeholder="Price" required
                                        id="editedPrice">
                                </td>
                                <td>
                                    <input type="file" name="photoLink" accept="images/*" required>
                                </td>
                            </form>
                        </tr>
                    </c:if>
                </c:if>
            </table>
        </c:forEach>

        <c:if test="${user.roleId == 1}">
            <h2 id="create-category-txtbtn" class="CategoryName">
                ${addCategoryFmt}
                <input id="add-category-btn" type="image" src="../../images/addContent.png">
            </h2>
        </c:if>


        <script src="../../js/xhr.js"></script>
        <script src="../../js/Menu.js"></script>

        <script src="../../js/MenuAjax.js"></script>