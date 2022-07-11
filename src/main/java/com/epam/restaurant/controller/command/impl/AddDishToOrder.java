package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Order;
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
import java.util.Map;

public class AddDishToOrder implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddDishToOrder.class);
    private static final ServiceProvider serviceProvider =  ServiceProvider.getInstance();

    private static final String ORDER_ATTR = "order";
    private static final String QUANTITY_OF_DISHES_ATTR = "quantityOfDishes";
    private static final String DISH_ID_PARAM = "dish_id";
    private static final String QUANTITY_PARAM = "quantity";
    private static final String MAIN_PAGE_ADDR = "/home?addedDishId={0}&addedToOrder=true";
    private static final String MAIN_PAGE_WITH_VALIDATION_ERROR = "/home?invalidDish=true&errMsgUpdDish={0}";

    private static final int FOUND_DISH = 0;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute(ORDER_ATTR);

        if (order == null) {
            order = new Order();
            session.setAttribute(ORDER_ATTR, order);

            Integer quantityOfDishes = 0;
            session.setAttribute(QUANTITY_OF_DISHES_ATTR, quantityOfDishes);
        }

        String dishId = request.getParameter(DISH_ID_PARAM);

        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Dishes.DISHES_ID.toString(), dishId);

        // TODO вывести сообщение о том, что блюдо добавлено в корзину

        try {
            MenuService menuService = serviceProvider.getMenuService();
            List<Dish> dishes = menuService.find(criteria);

            Dish dish = dishes.get(FOUND_DISH);
            Integer newQuantity = Integer.parseInt(request.getParameter(QUANTITY_PARAM));

            if (order.getOrderList().containsKey(dish)) {
                Integer currentQuantity = order.getOrderList().get(dish);
                currentQuantity += newQuantity;

                order.getOrderList().put(dish, currentQuantity);
            } else {
                order.getOrderList().put(dish, newQuantity);
            }

            setQuantityOfDishesToSession(order.getOrderList(), session);

            request.getRequestDispatcher(MessageFormat.format(MAIN_PAGE_ADDR, dishId)).forward(request, response);
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            try {
                request.getRequestDispatcher(MessageFormat.format(MAIN_PAGE_WITH_VALIDATION_ERROR, e.getMessage())).forward(request, response);
            } catch (IOException ex) {
                LOGGER.error("Error invalid address to forward when Add Dish To Order", e);
            }
        }
    }

    private void setQuantityOfDishesToSession(Map<Dish, Integer> orderList, HttpSession session) {
        Integer count = 0;

        for (Integer quantity : orderList.values()) {
            count += quantity;
        }
        session.setAttribute(QUANTITY_OF_DISHES_ATTR, count);
    }
}
