package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
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
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

public class EditDish implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditDish.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String DISH_NAME_PARAM = "dishName";
    private static final String EDITED_DISH_ID_PARAM = "editedDishId";
    private static final String DESCRIPTION_PARAM = "description";
    private static final String PRICE_PARAM = "price";
    private static final String PHOTO_LINK_PARAM = "photoLink";
    private static final String MENU_ATTR = "menu";
    private static final int CATEGORY_NOT_USED = -1;
    private static final String PATH_TO_PHOTO = "../../images/dishes/{0}";
    private static final String JSON_UTF8_TYPE = "application/json; charset=UTF-8";
    private static final String ERROR_MSG_JSON = "{\"validationError\": \"true\", \"message\": \"%s\"}";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        PrintWriter writer = null;

        try {
            writer = response.getWriter();

            int editedDishId = Integer.parseInt(request.getParameter(EDITED_DISH_ID_PARAM));
            String newDishName = request.getParameter(DISH_NAME_PARAM);
            String description = request.getParameter(DESCRIPTION_PARAM);
            BigDecimal price = new BigDecimal(request.getParameter(PRICE_PARAM));
            String photoLink = MessageFormat.format(PATH_TO_PHOTO, request.getParameter(PHOTO_LINK_PARAM));

            editCategoryInDB(editedDishId, newDishName, description, price, photoLink);

            editDishInSession(request, editedDishId, newDishName, description, price, photoLink);

            response.setContentType(JSON_UTF8_TYPE);
            // todo переделать здесь либо в editdish.js
            writer.println(new Gson().toJson(new Dish(editedDishId, price, newDishName, description, CATEGORY_NOT_USED, photoLink)));
        } catch (IOException e) {
            try {
                LOGGER.error("Error to get writer when try to Edit Dish in Menu", e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                throw new ServletException(e);
            } catch (IOException ex) {
                LOGGER.error("Error to send error writer when try to Edit Dish in Menu", e);
                throw new ServletException(e);
            }
        } catch (ValidationException | NumberFormatException e) {
            response.setContentType(JSON_UTF8_TYPE);
            writer.println(String.format(ERROR_MSG_JSON, e.getMessage()));
        }
    }

    // todo через билдер
    private void editDishInSession(HttpServletRequest request, int editedDishId, String newDishName, String description, BigDecimal price, String photoLink) {
        Menu menu = (Menu) request.getSession().getAttribute(MENU_ATTR);
        List<Dish> dishes = menu.getDishes();

        for (Dish dish : dishes) {
            if (dish.getId() == editedDishId) {
                dish.setName(newDishName);
                dish.setDescription(description);
                dish.setPrice(price.setScale(1));
                dish.setPhotoLink(photoLink);
                break;
            }
        }
    }

    private void editCategoryInDB(int editedDishId, String newDishName, String description, BigDecimal price, String photoLink) throws ServiceException {
        MenuService menuService = serviceProvider.getMenuService();

        menuService.editDish(editedDishId, newDishName, description, price, photoLink);
    }
}
