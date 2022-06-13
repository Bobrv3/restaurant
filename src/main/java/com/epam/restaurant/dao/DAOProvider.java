package com.epam.restaurant.dao;

import com.epam.restaurant.dao.impl.SQLUserDAO;

public class DAOProvider {
    private static final DAOProvider instance = new DAOProvider();
    private final UserDAO userDAOImpl = new SQLUserDAO();

    private DAOProvider() {
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAOImpl;
    }
}
