package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.criteria.SearchCriteria;
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

public class FindDishesBy implements Command {
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();
    private static final String CATEGORY_ID = "category_id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        int id = Integer.parseInt(request.getParameter(CATEGORY_ID));

        Criteria criteria = new Criteria();
        criteria.add(CATEGORY_ID, id);

        MenuService menuService = serviceProvider.getMenuService();
        List<Dish> dishes = menuService.find(criteria);

        request.getSession().setAttribute("dishes", dishes);
    }
}
