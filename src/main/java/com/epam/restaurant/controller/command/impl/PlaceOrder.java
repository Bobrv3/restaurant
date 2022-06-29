package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.AuthorizedUser;
import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.bean.Order;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.OrderService;
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

public class PlaceOrder implements Command {
    private static final Logger LOGGER = LogManager.getLogger(PlaceOrder.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String ORDER_ATTR = "order";
    private static final String USER_ATTR = "user";
    private static final String PAYMENT_BY_PARAM = "paymentBy";
    private static final String RECEIVING_PARAM = "receiving";
    private static final String CARD_ONLINE = "cardOnline";
    private static final String FINISHING_THE_ORDER_ADDR = "/finishingTheOrder";
    private static final String ONLINE_PAY_ADDR = "OnlinePay.jsp";
    private static final String MENU_ADDR = "menu";

    private static final String EX1 = "Invalid address to forward or redirect in the PlaceOrder command..";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        String paymentBy = request.getParameter(PAYMENT_BY_PARAM);

        try {
            if (CARD_ONLINE.equals(paymentBy)) {
                request.getRequestDispatcher(ONLINE_PAY_ADDR).forward(request, response);
            }
            else {
                HttpSession session = request.getSession();

                // create order
                AuthorizedUser user = (AuthorizedUser) session.getAttribute(USER_ATTR);
                Order order = (Order) session.getAttribute(ORDER_ATTR);

                OrderService orderService = serviceProvider.getOrderService();
                int orderId = orderService.createOder(order, user.getLogin());

                // create order detail
                Menu menu = (Menu) session.getAttribute(MENU_ADDR);
                String methodOfReceiving = request.getParameter(RECEIVING_PARAM);

                for (Dish dish : order.getOrderList().keySet()) {
                    Integer quantity = order.getOrderList().get(dish);
                    orderService.createOderDetail(orderId, dish.getId(), quantity, methodOfReceiving);
                }

                // create invoice
                PaymentService paymentService = serviceProvider.getPaymentService();
                int invoiceId = paymentService.createInvoice(orderId);

                response.sendRedirect(FINISHING_THE_ORDER_ADDR);
            }
        }
        catch (IOException e) {
            LOGGER.error(EX1, e);
        }
    }
}
