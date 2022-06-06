package com.epam.restaurant.dao;

import com.epam.restaurant.dao.impl.SQLUserDAO;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private final UserDAO userDAOImpl = new SQLUserDAO();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAOImpl;
    }
}
