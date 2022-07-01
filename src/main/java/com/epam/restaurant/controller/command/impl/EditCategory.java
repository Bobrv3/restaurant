package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
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

public class EditCategory implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditCategory.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String MAIN_PAGE_ADDR = "/home";
    private static final String CATEGORIES_ATTR = "categories";
    private static final String CATEGORY_NAME_PARAM = "categoryName";
    private static final String EDITED_CATEGORY_ID_PARAM = "editedCategoryId";

    private static final String EX1 = "Error invalid address to redirect";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        int editedCategoryId = Integer.parseInt(request.getParameter(EDITED_CATEGORY_ID_PARAM));
        String newCategoryName = request.getParameter(CATEGORY_NAME_PARAM);

        editCategoryInSession(request, editedCategoryId, newCategoryName);

        editCategoryInDB(editedCategoryId, newCategoryName);

        try {
            response.sendRedirect(MAIN_PAGE_ADDR);
        } catch (IOException e) {
            LOGGER.error(EX1, e);
        }
    }

    private void editCategoryInSession(HttpServletRequest request, int editedCategoryId, String newCategoryName) {
        List<Category> categories = (List<Category>) request.getSession().getAttribute(CATEGORIES_ATTR);
        for (Category category : categories) {
            if (category.getId() == editedCategoryId) {
                category.setName(newCategoryName);
                break;
            }
        }

        request.getSession().setAttribute(CATEGORIES_ATTR, categories);
    }

    private void editCategoryInDB(int editedCategoryId, String newCategoryName) throws ServiceException {
        MenuService menuService = serviceProvider.getMenuService();

        menuService.editCategory(editedCategoryId, newCategoryName);
    }
}
