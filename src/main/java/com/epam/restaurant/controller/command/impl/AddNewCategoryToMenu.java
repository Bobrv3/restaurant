package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.MenuService;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.ServiceProvider;
import com.epam.restaurant.service.validation.ValidationException;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AddNewCategoryToMenu implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddNewCategoryToMenu.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String CATEGORY_NAME = "categoryName";
    private static final String CATEGORIES_ATTR = "categories";
    private static final String JSON_UTF8_TYPE = "application/json; charset=UTF-8";
    private static final String ERROR_MSG_JSON = "{\"validationError\": \"true\", \"message\": \"%s\"}";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        String categoryName = request.getParameter(CATEGORY_NAME);
        PrintWriter writer = null;

        try {
            writer = response.getWriter();

            MenuService menuService = serviceProvider.getMenuService();
            int categoryId = menuService.addCategory(categoryName);

            List<Category> categories = (List<Category>) request.getSession().getAttribute(CATEGORIES_ATTR);
            Category createdCategory = new Category(categoryId, categoryName);
            categories.add(createdCategory);

            response.setContentType(JSON_UTF8_TYPE);
            writer.println(new Gson().toJson(createdCategory));
        } catch (IOException e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            LOGGER.error("Error to get writer when try to add new category", e);
            throw new ServletException(e);
        } catch (ValidationException e) {
            response.setContentType(JSON_UTF8_TYPE);
            writer.println(String.format(ERROR_MSG_JSON, e.getMessage()));
        }
    }
}
