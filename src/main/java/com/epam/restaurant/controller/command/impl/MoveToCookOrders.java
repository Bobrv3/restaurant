package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Order;
import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.criteria.SearchCriteria;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.OrderService;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MoveToCookOrders implements Command {
    private static final Logger LOGGER = LogManager.getLogger(MoveToCookOrders.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String CONFIRMED_STATUS = "confirmed";
    private static final String DISH_FOR_COOK_ATTR = "dishForCook";
    private static final String KITCHEN_ADDR = "/kitchen";

    private static final String EX1 = "Error invalid address to redirect";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        OrderService orderService = serviceProvider.getOrderService();

        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Orders.ORDER_STATUS.name(), CONFIRMED_STATUS);

        Map<Order, Dish> orderDishMap = orderService.findOrdersWithDishInfo(criteria);

        request.getSession().setAttribute(DISH_FOR_COOK_ATTR, orderDishMap);

        try {
            response.sendRedirect(KITCHEN_ADDR);
        } catch (IOException e) {
            LOGGER.error(EX1, e);
        }
    }
}
