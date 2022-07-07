package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
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
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

public class AddNewDishToMenu implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddNewDishToMenu.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String CATEGORY_FOR_ADD_PARAM = "categoryForAdd";
    private static final String DISH_NAME_PARAM = "dishName";
    private static final String DESCRIPTION_PARAM = "description";
    private static final String PRICE_PARAM = "price";
    private static final String PHOTO_LINK_PARAM = "photoLink";
    private static final String MENU_ATTR = "menu";
    private static final String MAIN_PAGE_ADDR = "/home";
    private static final String PATH_TO_PHOTO = "../../images/dishes/{0}";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        Integer categoryForAdd = Integer.parseInt(request.getParameter(CATEGORY_FOR_ADD_PARAM));
        String dishName = request.getParameter(DISH_NAME_PARAM);
        String description = request.getParameter(DESCRIPTION_PARAM);
        BigDecimal price = new BigDecimal(request.getParameter(PRICE_PARAM));
        String photoLink = MessageFormat.format(PATH_TO_PHOTO, request.getParameter(PHOTO_LINK_PARAM));

        try {
            MenuService menuService = serviceProvider.getMenuService();
            int newDishId = menuService.addDish(price, dishName, description, categoryForAdd, photoLink);

            Menu menu = (Menu) request.getSession().getAttribute(MENU_ATTR);
            List<Dish> dishes = menu.getDishes();
            dishes.add(new Dish(newDishId, price, dishName, description, categoryForAdd, photoLink));

            response.sendRedirect(MAIN_PAGE_ADDR);
        } catch (IOException e) {
            LOGGER.error("Error invalid address to redirect when Add New Dish To Menu", e);
        } catch (ValidationException e) {
            try {
                request.getRequestDispatcher(MessageFormat.format("/home?invalidDish=true&errMsgUpdDish={0}", e.getMessage())).forward(request, response);
            } catch (IOException ex) {
                LOGGER.error("Error invalid address to forward when Add New Dish To Menu", e);
            }
        }
    }
}
