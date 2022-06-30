package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.MenuService;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GetCategories implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GetCategories.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        MenuService menuService = serviceProvider.getMenuService();

        List<Category> categories = menuService.getCategories();

        HttpSession session = request.getSession();
        session.setAttribute("categories", categories);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/home");
        try {
            requestDispatcher.forward(request, response);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
