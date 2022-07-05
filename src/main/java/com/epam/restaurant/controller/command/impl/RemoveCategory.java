package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.MenuService;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RemoveCategory implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RemoveCategory.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String CATEGORY_ID_PARAM = "categoryId";
    private static final String CATEGORIES_ATTR = "categories";
    private static final String MAIN_PAGE_ADDR = "/home";

    private static final String EX1 = "Error invalid address to redirect";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        int categoryId = Integer.parseInt(request.getParameter(CATEGORY_ID_PARAM));

        MenuService menuService = serviceProvider.getMenuService();
        menuService.removeCategory(categoryId);

        List<Category> categories = (List<Category>) request.getSession().getAttribute(CATEGORIES_ATTR);
        for (Category category : categories) {
            if (category.getId() == categoryId) {
                categories.remove(category);
                break;
            }
        }

        try {
            response.sendRedirect(MAIN_PAGE_ADDR);
        } catch (IOException e) {
            LOGGER.error(EX1, e);
        }
    }
}