package com.epam.restaurant.dao;

import com.epam.restaurant.dao.impl.SQLMenuDAO;
import com.epam.restaurant.dao.impl.SQLOrderDAO;
import com.epam.restaurant.dao.impl.SQLPaymentDAO;
import com.epam.restaurant.dao.impl.SQLUserDAO;

public class DAOProvider {
    private static final DAOProvider instance = new DAOProvider();
    private final UserDAO userDAOImpl = new SQLUserDAO();
    private final MenuDAO menuDAOImpl = new SQLMenuDAO();
    private final OrderDAO orderDAOImpl = new SQLOrderDAO();
    private final PaymentDAO paymentDAO = new SQLPaymentDAO();

    private DAOProvider() {
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAOImpl;
    }

    public MenuDAO getMenuDAO() {
        return menuDAOImpl;
    }

    public OrderDAO getOrderDAO() {
        return orderDAOImpl;
    }

    public PaymentDAO getPaymentDAO() {
        return paymentDAO;
    }
}
