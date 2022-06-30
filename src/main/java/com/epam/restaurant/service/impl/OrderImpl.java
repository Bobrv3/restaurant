package com.epam.restaurant.service.impl;

import com.epam.restaurant.bean.AuthorizedUser;
import com.epam.restaurant.bean.Order;
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

public class OrderImpl implements OrderService {
    private static final DAOProvider daoProvider = DAOProvider.getInstance();

    @Override
    public int createOder(Order order, String userLogin) throws ServiceException {
        UserDAO userDAO = daoProvider.getUserDAO();

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
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            OrderDAO orderDAO = daoProvider.getOrderDAO();
            List<Order> orders = orderDAO.find(criteria);

            return orders;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
