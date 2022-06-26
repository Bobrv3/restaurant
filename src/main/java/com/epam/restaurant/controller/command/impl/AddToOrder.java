package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Order;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.criteria.SearchCriteria;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.MenuService;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddToOrder implements Command {
    private static final ServiceProvider serviceProvider =  ServiceProvider.getInstance();
    private static final int FOUND_DISH = 0;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");

        if (order == null) {
            order = new Order();
            session.setAttribute("order", order);

            Integer quantityOfDishes = 0;
            session.setAttribute("quantityOfDishes", quantityOfDishes);
        }

        String dishId = request.getParameter("dish_id");
        if (dishId == null) { // it was caused as lastParameter(after change local), which doesn't keep any parameters
            try {
                request.getRequestDispatcher("index.jsp").forward(request, response); // not needed to add the number of dishes to the order, otherwise their number will increase without clicking the add button.
            } catch (IOException e) {
                // TODO обработать
                e.printStackTrace();
            }
            return;
        }

        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Dishes.DISHES_ID.toString(), dishId);

        MenuService menuService = serviceProvider.getMenuService();
        List<Dish> dishes = menuService.find(criteria);

        Dish dish = dishes.get(FOUND_DISH);
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));

        if (order.getOrderList().containsKey(dish)) {
            Integer currentQuantity = order.getOrderList().get(dish);
            currentQuantity += quantity;

            order.getOrderList().put(dish, currentQuantity);
        } else {
            order.getOrderList().put(dish, quantity);
        }

        setQuantityOfDishesToSession(order.getOrderList(), session);

        // TODO вывести сообщение о том, что блюдо добавлено в корзину

        try {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setQuantityOfDishesToSession(Map<Dish, Integer> orderList, HttpSession session) {
        Integer count = 0;

        for (Integer quantity : orderList.values()) {
            count += quantity;
        }
        session.setAttribute("quantityOfDishes", count);
    }
}
