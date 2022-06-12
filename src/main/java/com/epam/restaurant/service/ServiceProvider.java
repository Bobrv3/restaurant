package com.epam.restaurant.service;

import com.epam.restaurant.service.impl.UserImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();
    private final UserService userService;

    private ServiceProvider() {
        userService = new UserImpl();
    }

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }
}

