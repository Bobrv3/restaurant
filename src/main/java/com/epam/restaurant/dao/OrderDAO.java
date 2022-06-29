package com.epam.restaurant.dao;

import com.epam.restaurant.bean.Order;

public interface OrderDAO {
    int createOrder(Order order, int userId) throws DAOException;
    boolean createOrderDetails(int oderId, int menuId, Integer quantity, String methodOfReceiving) throws DAOException;
}
