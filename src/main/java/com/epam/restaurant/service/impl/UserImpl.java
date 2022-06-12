package com.epam.restaurant.service.impl;

import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.User;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.DAOProvider;
import com.epam.restaurant.dao.UserDAO;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.UserService;

import java.util.Arrays;


public class UserImpl implements UserService {
    private static final DAOProvider daoProvider = DAOProvider.getInstance();

    @Override
    public User signIn(String login, char[] password) throws ServiceException {
        // TODO validation
//        validationLogin(login);
//        validationPassword(password);

        User user = null;

        try {
            UserDAO userDAO = daoProvider.getUserDAO();
            user = userDAO.signIn(login, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            Arrays.fill(password, ' ');
        }

        return user;
    }

    @Override
    public boolean signUp(RegistrationUserData userData) throws ServiceException {
        // TODO validate
        UserDAO userDAO = daoProvider.getUserDAO();
        boolean result = false;
        try {
            result = userDAO.signUp(userData);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return result;
    }
}
