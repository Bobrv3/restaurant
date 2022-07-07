package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class QuitFromAccount implements Command {
    private static final Logger LOGGER = LogManager.getLogger(QuitFromAccount.class);

    private static final String ORDERS_IN_PROCESSING_ATTR = "ordersInProcessing";
    private static final String ORDERS_HISTORY_ATTR = "ordersHistory";
    private static final String PAYMENT_ATTR = "paymentBy";
    private static final String RECEIVING_ATTR = "receiving";
    private static final String QUANTITY_OF_DISHES_ATTR = "quantityOfDishes";
    private static final String ORDER_ATTR = "order";
    private static final String AUTHORIZED_USER_ATTR = "user";
    private static final String MAIN_PAGE_ADDR = "/home";
    private static final String ORDERS_FOR_CONFIRMATION_ATTR = "ordersForConfirmation";
    private static final String ORDERS_FOR_COOKING_ATTR = "ordersForCooking";

    private static final String EX1 = "Invalid address to redirect in QuitFromAccount";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        HttpSession session = request.getSession();

        session.removeAttribute(ORDERS_IN_PROCESSING_ATTR);
        session.removeAttribute(ORDERS_HISTORY_ATTR);
        session.removeAttribute(ORDER_ATTR);
        session.removeAttribute(PAYMENT_ATTR);
        session.removeAttribute(RECEIVING_ATTR);
        session.removeAttribute(QUANTITY_OF_DISHES_ATTR);
        session.removeAttribute(AUTHORIZED_USER_ATTR);
        session.removeAttribute(ORDERS_FOR_CONFIRMATION_ATTR);
        session.removeAttribute(ORDERS_FOR_COOKING_ATTR);

        try {
            response.sendRedirect(MAIN_PAGE_ADDR);
        } catch (IOException e) {
            LOGGER.error(EX1, e);
        }
    }
}
