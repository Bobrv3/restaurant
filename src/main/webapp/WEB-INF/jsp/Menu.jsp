<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="local" var="loc" />


        <fmt:message bundle="${loc}" key="local.link.Menu" var="menu_link" />
        <fmt:message bundle="${loc}" key="local.button.add" var="btnAddFmt" />
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
                <h2 id="category${category.id}" class="CategoryName">
                    <label>${category.name}</label>
                    <c:if test="${user.roleId == 1}">
                        <input type="image" src="../../images/edit.png" alt="edit" class="imgInTd"
                            onclick="showEditCategory(`${category.id}`, event)">
                        <input type="image" src="../../images/remove.png" alt="remove" class="imgInTd"
                            onclick="removeCategory(`${category.id}`, event)">
                    </c:if>
                </h2>
            </c:if>


            <table>
                <c:forEach items="${menu.getDishes()}" var="dish">
                    <c:if test="${dish.categoryId == category.id}">
                        <tr id="dish${dish.id}row">
                            <td class="col1">
                                <li>
                                    <strong id="dishName${dish.id}_${category.id}"
                                        style="font-size: 15px;">${dish.name}</strong>
                                    (<span id="description${dish.id}_${category.id}">${dish.description}</span>)
                                </li>
                            </td>
                            <td class="col2">
                                <strong id="price${dish.id}_${category.id}">${dish.price}</strong>
                            </td>
                            <td class="col3">
                                <img id="photoLink${dish.id}_${category.id}" src="${dish.photoLink}"
                                    alt="photo of ${dish.name}" class="dishPhoto">
                            </td>
                            <td class="col4">
                                <form action="restaurant" method="post">
                                    <input type="hidden" name="command" value="add_to_order">
                                    <input type="hidden" name="dish_id" value="${dish.id}">

                                    <button onclick="reduceOne(event)">-</button>
                                    <input type="text" name="quantity" value="1" onkeypress='validate(event)' required>
                                    <button onclick="addOne(event)">+</button> <br>

                                    <input type="submit" value="${btnAddFmt}" class="yellow_button" id="addToOrderBtn">
                                </form>
                                <c:if test="${param.addedToOrder == true && dish.id == param.addedDishId}">
                                    <div style="color: rgb(27, 167, 27);">${dishAddedMsgFmt} &#10004</div>
                                </c:if>
                            </td>
                            <c:if test="${user.roleId == 1}">
                                <td class="col5">
                                    <input type="image" src="../../images/remove.png" alt="remove" class="imgInTd"
                                        onclick="removeDishFromMenu(`${dish.id}`, event)">
                                    <input type="image" src="../../images/edit.png" alt="edit" class="imgInTd"
                                        onclick="editDish(`${dish.id}`, `${category.id}`, `${saveFmt}`)">
                                </td>
                            </c:if>
                        </tr>
                    </c:if>
                </c:forEach>

                <c:if test="${user.roleId == 1}">
                    <c:if test="${!param.createDish || (param.createDish && param.categoryForAdd != category.id)}">
                        <tr id="addNewDishTr${category.id}">
                            <td colspan="5">
                                <input type="image" src="../../images/addContent.png" alt="add dish" id="imgAddContent"
                                    onclick="showCreateDishFrom(`${category.id}`, event, `${saveFmt}`, `${btnAddFmt}`)">
                            </td>
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
        <script src="../../js/Menu/AddNewCategory.js"></script>
        <script src="../../js/Menu/EditCategory.js"></script>
        <script src="../../js/Menu/RemoveCategory.js"></script>
        <script src="../../js/Menu/RemoveDishFromMenu.js"></script>
        <script src="../../js/Menu/AddDish.js"></script>
        <script src="../../js/Menu/EditDish.js"></script>
        <script src="../../js/Menu/AddReduceBtn.js"></script>