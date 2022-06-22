package com.epam.restaurant.service;

import com.epam.restaurant.service.impl.MenuImpl;
import com.epam.restaurant.service.impl.UserImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();
    private final UserService userService = new UserImpl();
    private final MenuService menuService = new MenuImpl();

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
}

