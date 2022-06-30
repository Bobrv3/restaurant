package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.AuthorizedUser;
import com.epam.restaurant.bean.Order;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.OrderService;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GetHistoryOfOrders implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GetHistoryOfOrders.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String USER_ATTR = "user";
    private static final String ORDERS_HISTORY_ATTR = "ordersHistory";
    private static final String HISTORY_OF_ORDERS_ADDR = "/historyOfOrders";
    private static final String EX = "Error invalid address to forward";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        OrderService orderService = serviceProvider.getOrderService();
        HttpSession session = request.getSession();

        AuthorizedUser user = (AuthorizedUser) session.getAttribute(USER_ATTR);

        List<Order> ordersHistory = orderService.getAllUserOrders(user.getLogin());

        session.setAttribute(ORDERS_HISTORY_ATTR, ordersHistory);

        try {
            request.getRequestDispatcher(HISTORY_OF_ORDERS_ADDR).forward(request, response);
        } catch (IOException e) {
            LOGGER.error(EX, e);
        }

    }
}
