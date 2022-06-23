package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.MenuService;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.ServiceProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GetMenu implements Command {
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        MenuService menuService = serviceProvider.getMenuService();

        Menu menu = menuService.getMenu();
        List<Category> categories = menuService.getCategories();

        HttpSession session = request.getSession();
        session.setAttribute("menu", menu);
        session.setAttribute("categories", categories);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            // TODO обработать
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
