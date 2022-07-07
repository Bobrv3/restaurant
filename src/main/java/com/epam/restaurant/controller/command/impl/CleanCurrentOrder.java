package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CleanCurrentOrder implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CleanCurrentOrder.class);

    private static final String ORDER_ATTR = "order";
    private static final String QUANTITY_ATTR = "quantityOfDishes";
    private static final String CURRENT_ORDER_ADDR = "/showCurrentOrder";

    private static final String EX1 = "Error invalid address to redirect";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        request.getSession().removeAttribute(ORDER_ATTR);
        request.getSession().removeAttribute(QUANTITY_ATTR);

        try {
            response.sendRedirect(CURRENT_ORDER_ADDR);
        } catch (IOException e) {
            LOGGER.error(EX1, e);
        }
    }
}
