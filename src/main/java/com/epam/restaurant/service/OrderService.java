package com.epam.restaurant.service;

import com.epam.restaurant.bean.Order;
import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.criteria.Criteria;

import java.util.List;

public interface OrderService {

    int createOder(Order order, String userLogin) throws ServiceException;

    // TODO переделать возвращаемый тип
    void createOderDetail(int oderId, int menuId, Integer quantity, String methodOfReceiving) throws ServiceException;

    List<Order> find(Criteria criteria) throws ServiceException;

    List<Order> findOrdersWithUsersInfo(Criteria criteria, RegistrationUserData userData) throws ServiceException;

    boolean confirmOrder(int orderID) throws ServiceException;
}
