package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Order;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class MakeOrderCooked implements Command {
    private static final Logger LOGGER = LogManager.getLogger(MakeOrderCooked.class);

    private static final String ORDER_FOR_COOK_ATTR = "orderForCook";
    private static final String COOKED_ORDER_ID_PARAM = "cookedOrderId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        int cookedDishId = Integer.parseInt(request.getParameter(COOKED_ORDER_ID_PARAM));
        Map<Order, Dish> orderDishMap = (Map<Order, Dish>) request.getSession().getAttribute(ORDER_FOR_COOK_ATTR);


    }
}
