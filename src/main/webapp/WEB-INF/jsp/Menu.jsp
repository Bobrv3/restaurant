<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Menu</h1>

<c:if test="${menu == null}">
    <jsp:forward page="/restaurant">
        <jsp:param name="command" value="get_menu" />
    </jsp:forward>
</c:if>

<c:forEach items="${categories}" var="category">
    <h2 class="CategoryName">${category.name}</h2>
    <table>
        <c:forEach items="${menu.getDishes()}" var="dish">
            <c:if test="${dish.category_id == category.id}">
                <tr>
                    <td>
                        <h3 class="DishName"><li/>${dish.name}</h3>
                        ${dish.description}
                    </td>
                    <td>
                        ${dish.price}
                    </td>
                    <td>
                        <form action="restaurant" method="post" class="container">
                            <input type="hidden" name="command" value="add_to_order"/>
                            <input type="hidden" name="dish_id" value="${dish.id}"/>
                            <button>-</button>
                            <input type="text" name="quantity" value="1" value="" required/>
                            <button>+</button> <br>
                            <input type="submit" value="add"/>
                        </form>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </table>
</c:forEach>