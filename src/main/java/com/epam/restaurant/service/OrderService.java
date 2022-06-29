package com.epam.restaurant.service;

import com.epam.restaurant.bean.Order;

public interface OrderService {
    int createOder(Order order, String userLogin) throws ServiceException;
    void createOderDetail(int oderId, int menuId, Integer quantity, String methodOfReceiving) throws ServiceException;
}
