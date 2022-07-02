package com.epam.restaurant.dao;

import com.epam.restaurant.bean.Order;
import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.criteria.Criteria;

import java.util.List;

public interface OrderDAO {

    int createOrder(Order order, int userId) throws DAOException;

    boolean createOrderDetails(int oderId, int menuId, Integer quantity, String methodOfReceiving) throws DAOException;

    List<Order> find(Criteria criteria) throws DAOException;

    List<Order> findOrdersWithUsersInfo(Criteria criteria, RegistrationUserData userData) throws DAOException;
}
