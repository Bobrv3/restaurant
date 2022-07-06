package com.epam.restaurant.service.impl;

import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.AuthorizedUser;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.DAOProvider;
import com.epam.restaurant.dao.UserDAO;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.UserService;

import java.util.List;


public class UserImpl implements UserService {
    private static final DAOProvider daoProvider = DAOProvider.getInstance();
    private static final UserDAO userDAO = daoProvider.getUserDAO();

    @Override
    public AuthorizedUser signIn(String login, char[] password) throws ServiceException {
        // TODO validation
//        validationLogin(login);
//        validationPassword(password);

        AuthorizedUser user = null;

        try {
            user = userDAO.signIn(login, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public boolean signUp(RegistrationUserData userData) throws ServiceException {
        // TODO validate

        boolean result = false;
        try {
            result = userDAO.signUp(userData);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    @Override
    public List<RegistrationUserData> find(Criteria criteria) throws ServiceException {
        try {
            return userDAO.find(criteria);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateUser(String login, Criteria criteria) throws ServiceException {
        try {
            return userDAO.updateUser(login, criteria);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
