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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OnlinePay implements Command {
    private static final Logger LOGGER = LogManager.getLogger(OnlinePay.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();
    private static final String PAYMENT_ATTR = "paymentBy";
    private static final String RECEIVING_ATTR = "receiving";
    private static final String QUANTITY_OF_DISHES_ATTR = "quantityOfDishes";
    private static final String ORDER_ATTR = "order";
    private static final String FINISHING_THE_ORDER_ADDR = "/finishingTheOrder";

    private static final String EX1 = "Invalid address to redirect in online pay";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        int invoiceId = PlaceOrder.setInvoice(request);

        HttpSession session = request.getSession();
        int paymentMethodId = (Integer) session.getAttribute(PAYMENT_ATTR);

        PaymentService paymentService = serviceProvider.getPaymentService();
        paymentService.createPayment(invoiceId, paymentMethodId);

        session.removeAttribute(ORDER_ATTR);
        session.removeAttribute(PAYMENT_ATTR);
        session.removeAttribute(RECEIVING_ATTR);
        session.removeAttribute(QUANTITY_OF_DISHES_ATTR);

        try {
            response.sendRedirect(FINISHING_THE_ORDER_ADDR);
        } catch (IOException e) {
            LOGGER.error(EX1, e);
        }
    }
}
