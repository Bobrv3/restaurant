package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.criteria.SearchCriteria;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class RemoveDishFromMenu implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RemoveDishFromMenu.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String DISH_ID_PARAM = "dishId";
    private static final String MENU_ATTR = "menu";
    private static final String MAIN_PAGE_ADDR = "/home";

    private static final String EX1 = "Error invalid address to redirect";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        Integer idToRemove = Integer.parseInt(request.getParameter(DISH_ID_PARAM));

        try {
            removeFromDB(idToRemove);

            removeDishFromSessionMenu(request, idToRemove);

            response.sendRedirect(MAIN_PAGE_ADDR);
        } catch (IOException e) {
            LOGGER.error(EX1, e);
        } catch (ValidationException e) {
            try {
                request.getRequestDispatcher(MessageFormat.format("/home?invalidDish=true&errMsgUpdDish={0}", e.getMessage())).forward(request, response);
            } catch (IOException ex) {
                LOGGER.error("Error invalid address to forward when Remove Dish From Menu", e);
            }
        }
    }

    private void removeFromDB(int idToRemove) throws ServiceException {
        MenuService menuService = serviceProvider.getMenuService();

        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Dishes.DISHES_ID.toString(), idToRemove);

        menuService.removeDish(criteria);
    }

    private void removeDishFromSessionMenu(HttpServletRequest request, int idToRemove) {
        HttpSession session = request.getSession();
        Menu menu = (Menu) session.getAttribute(MENU_ATTR);

        List<Dish> dishes = menu.getDishes();
        for (Dish dish : dishes) {
            if (dish.getId() == idToRemove) {
                dishes.remove(dish);
                break;
            }
        }
    }
}
