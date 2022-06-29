package com.epam.restaurant.service;

import com.epam.restaurant.service.impl.MenuImpl;
import com.epam.restaurant.service.impl.OrderImpl;
import com.epam.restaurant.service.impl.PaymentImpl;
import com.epam.restaurant.service.impl.UserImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();
    private final UserService userService = new UserImpl();
    private final MenuService menuService = new MenuImpl();
    private final OrderService orderService = new OrderImpl();
    private final PaymentService paymentService = new PaymentImpl();

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public OrderService getOrderService() {
        return orderService;
    }
}

