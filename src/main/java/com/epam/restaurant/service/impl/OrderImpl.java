package com.epam.restaurant.service.impl;

import com.epam.restaurant.bean.Order;
import com.epam.restaurant.bean.OrderForCooking;
import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.criteria.SearchCriteria;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.DAOProvider;
import com.epam.restaurant.dao.OrderDAO;
import com.epam.restaurant.dao.UserDAO;
import com.epam.restaurant.service.OrderService;
import com.epam.restaurant.service.ServiceException;

import java.util.List;
import java.util.Map;

public class OrderImpl implements OrderService {
    private static final DAOProvider daoProvider = DAOProvider.getInstance();
    private static final UserDAO userDAO = daoProvider.getUserDAO();

    @Override
    public int createOder(Order order, String userLogin) throws ServiceException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Users.LOGIN.toString(), userLogin);

        try {
            List<RegistrationUserData> authorizedUsers = userDAO.find(criteria);

            OrderDAO orderDAO = daoProvider.getOrderDAO();
            int orderId = orderDAO.createOrder(order, authorizedUsers.get(0).getId());

            return orderId;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void createOderDetail(int oderId, int menuId, Integer quantity, String methodOfReceiving) throws ServiceException {
        OrderDAO orderDAO = daoProvider.getOrderDAO();
        try {
            orderDAO.createOrderDetails(oderId, menuId, quantity, methodOfReceiving);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> find(Criteria criteria) throws ServiceException {
        try {
            OrderDAO orderDAO = daoProvider.getOrderDAO();
            List<Order> orders = orderDAO.find(criteria);

            return orders;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Order, RegistrationUserData> findOrdersWithUsersInfo(Criteria criteria) throws ServiceException {
        try {
            OrderDAO orderDAO = daoProvider.getOrderDAO();
            Map<Order, RegistrationUserData> orderUserMap = orderDAO.findOrdersWithUsersInfo(criteria);

            return orderUserMap;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateOrderStatus(int orderID, String status) throws ServiceException {
        try {
            OrderDAO orderDAO = daoProvider.getOrderDAO();

            return orderDAO.updateOrderStatus(orderID, status);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<OrderForCooking> findOrdersWithDishInfo(Criteria criteria) throws ServiceException {
        try {
            OrderDAO orderDAO = daoProvider.getOrderDAO();

            return orderDAO.findOrdersWithDishInfo(criteria);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
