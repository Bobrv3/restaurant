package com.epam.restaurant.dao;

import com.epam.restaurant.bean.Order;

import java.util.List;

public interface OrderDAO {
    int createOrder(Order order, int userId) throws DAOException;
    boolean createOrderDetails(int oderId, int menuId, Integer quantity, String methodOfReceiving) throws DAOException;
    List<Order> getAllUserOrders(int id) throws DAOException;
}
