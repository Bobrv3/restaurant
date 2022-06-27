package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Order;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class SetQuantityOfDish implements Command {
    private static final String QUANTITY_PARAM = "quantity";
    private static final String DISH_ID_PARAM = "dish_id";
    private static final String ORDER_ATTR = "order";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute(ORDER_ATTR);

        Integer newQuantity = Integer.parseInt(request.getParameter(QUANTITY_PARAM));
        Integer dish_id = Integer.parseInt(request.getParameter(DISH_ID_PARAM));

        Map<Dish, Integer> orderList = order.getOrderList();
        for (Dish dish : orderList.keySet()) {
            if (dish.getId() == dish_id) {
                orderList.put(dish, newQuantity);
            }
        }
        session.setAttribute(ORDER_ATTR, order);

        try {
            request.getRequestDispatcher("/showCurrentOrder").forward(request, response);
        } catch (IOException e) {
            // TODO обработать
            e.printStackTrace();
        }
    }
}
