package com.epam.restaurant.service;

import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Order;
import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.criteria.Criteria;

import java.util.List;
import java.util.Map;

public interface OrderService {

    int createOder(Order order, String userLogin) throws ServiceException;

    // TODO переделать возвращаемый тип
    void createOderDetail(int oderId, int menuId, Integer quantity, String methodOfReceiving) throws ServiceException;

    List<Order> find(Criteria criteria) throws ServiceException;

    Map<Order, RegistrationUserData> findOrdersWithUsersInfo(Criteria criteria) throws ServiceException;

    boolean confirmOrder(int orderID) throws ServiceException;

    Map<Order, Dish> findOrdersWithDishInfo(Criteria criteria) throws ServiceException;
}
