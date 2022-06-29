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

public class PlaceOrder implements Command {
    private static final Logger LOGGER = LogManager.getLogger(PlaceOrder.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        String paymentBy = request.getParameter("paymentBy");

        try {
            if ("cardOnline".equals(paymentBy)) {
                request.getRequestDispatcher("OnlinePay.jsp").forward(request, response);
            }
            else {
//                serviceProvider;

                response.sendRedirect("/finishingTheOrder");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
