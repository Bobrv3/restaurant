package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.PaymentService;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OnlinePay implements Command {
    private static final Logger LOGGER = LogManager.getLogger(OnlinePay.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();
    private static final String PAYMENT_PARAM = "paymentBy";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        int invoiceId = PlaceOrder.setInvoice(request);

        int paymentMethodId = (Integer) request.getSession().getAttribute(PAYMENT_PARAM);

        PaymentService paymentService = serviceProvider.getPaymentService();
        paymentService.createPayment(invoiceId, paymentMethodId);

        try {
            response.sendRedirect("/finishingTheOrder");
        } catch (IOException e) {
            LOGGER.error("Invalid address to redirect in online pay", e);
        }
    }
}
