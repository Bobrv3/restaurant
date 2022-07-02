package com.epam.restaurant.controller.command.impl;

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
import java.util.List;

public class ConfirmationOfOrders implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ConfirmationOfOrders.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String IN_PROCESSING = "in processing";
    private static final String USER_DATA_ATTR = "userData";
    private static final String ORDERS_FOR_CONFIRMATION_ATTR = "ordersForConfirmation";
    private static final String CONFIRMATION_OF_ORDERS_ADDR = "/confirmationOfOrders";

    private static final String EX1 = "Error invalid address to redirect";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        OrderService orderService = serviceProvider.getOrderService();

        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Orders.ORDER_STATUS.toString(), IN_PROCESSING);

        RegistrationUserData userData = new RegistrationUserData();
        List<Order> orders = orderService.findOrdersWithUsersInfo(criteria, userData);

        request.getSession().setAttribute(ORDERS_FOR_CONFIRMATION_ATTR, orders);
        request.getSession().setAttribute(USER_DATA_ATTR, userData);

        try {
            response.sendRedirect(CONFIRMATION_OF_ORDERS_ADDR);
        } catch (IOException e) {
            LOGGER.error(EX1, e);
        }
    }
}
