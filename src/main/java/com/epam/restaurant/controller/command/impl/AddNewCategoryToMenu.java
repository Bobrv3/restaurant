package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.MenuService;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.ServiceProvider;
import com.epam.restaurant.service.validation.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class AddNewCategoryToMenu implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddNewCategoryToMenu.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String CATEGORY_NAME = "categoryName";
    private static final String CATEGORIES_ATTR = "categories";
    private static final String MAIN_PAGE_ADDR = "/home";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        String categoryName = request.getParameter(CATEGORY_NAME);

        try {
            MenuService menuService = serviceProvider.getMenuService();
            int categoryId = menuService.addCategory(categoryName);

            List<Category> categories = (List<Category>) request.getSession().getAttribute(CATEGORIES_ATTR);
            categories.add(new Category(categoryId, categoryName));

            response.sendRedirect(MAIN_PAGE_ADDR);
        } catch (IOException e) {
            LOGGER.error("Error invalid address to redirect", e);
        } catch (ValidationException e) {
            try {
                request.getRequestDispatcher(MessageFormat.format("/home?invalidCategory=true&errMsgUpdCategory={0}", e.getMessage())).forward(request, response);
            } catch (IOException ex) {
                LOGGER.error("Error invalid address to forward in edit category", e);
            }
        }
    }
}
