package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.bean.builder.DishBuilder;
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

/**
 * The ${@code AddNewDishToMenu} class provides adding a new dish to the menu and database
 */
public class AddNewDishToMenu implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddNewDishToMenu.class);
    private static final MenuService menuService = ServiceProvider.getInstance().getMenuService();

    private static final String CATEGORY_FOR_ADD_PARAM = "categoryForAdd";
    private static final String DISH_NAME_PARAM = "dishName";
    private static final String DESCRIPTION_PARAM = "description";
    private static final String PRICE_PARAM = "price";
    private static final String PHOTO_LINK_PARAM = "photoLink";
    private static final String MENU_ATTR = "menu";
    private static final String PATH_TO_PHOTO = "../../images/dishes/{0}";
    private static final String JSON_UTF8_TYPE = "application/json; charset=UTF-8";
    private static final String ERROR_MSG_JSON = "{\"validationError\": \"true\", \"message\": \"%s\"}";

    /**
     * Provides the addition of a new dish to the session menu and adds it to the database
     * @param request client request
     * @param response response to the client
     * @throws ServiceException if an error occurred at the service level
     * @throws ServletException if an error occurred during execute command
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        PrintWriter writer = null;

        try {
            writer = response.getWriter();

            Integer categoryForAdd = Integer.parseInt(request.getParameter(CATEGORY_FOR_ADD_PARAM));
            String dishName = request.getParameter(DISH_NAME_PARAM);
            String description = request.getParameter(DESCRIPTION_PARAM);
            BigDecimal price = new BigDecimal(request.getParameter(PRICE_PARAM));
            String photoLink = MessageFormat.format(PATH_TO_PHOTO, request.getParameter(PHOTO_LINK_PARAM));

            int newDishId = menuService.addDish(new DishBuilder()
                    .setPrice(price)
                    .setName(dishName)
                    .setDescription(description)
                    .setCategoryId(categoryForAdd)
                    .setPhotoLink(photoLink)
                    .build());

            Menu menu = (Menu) request.getSession().getAttribute(MENU_ATTR);
            List<Dish> dishes = menu.getDishes();
            Dish addedDish = new Dish(newDishId, price.setScale(1), dishName, description, categoryForAdd, photoLink);
            dishes.add(addedDish);

            response.setContentType(JSON_UTF8_TYPE);
            writer.println(new Gson().toJson(addedDish));
        } catch (IOException e) {
            try {
                LOGGER.error("Error to get writer when try to Add New Dish To Menu", e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                throw new ServletException(e);
            } catch (IOException ex) {
                LOGGER.error("Error to send error writer when try to Add New Dish To Menu", e);
                throw new ServletException(e);
            }
        } catch (ValidationException | NumberFormatException e) {
            response.setContentType(JSON_UTF8_TYPE);
            writer.println(String.format(ERROR_MSG_JSON, e.getMessage()));
        }
    }
}
