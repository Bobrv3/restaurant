package com.epam.restaurant.service;

import com.epam.restaurant.bean.AuthorizedUser;
import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.criteria.Criteria;

import java.util.List;

public interface UserService {
    AuthorizedUser signIn(String login, char[] password) throws ServiceException;

    boolean signUp(RegistrationUserData userData) throws ServiceException;

    List<RegistrationUserData> find(Criteria criteria) throws ServiceException;

    boolean updateUser(String login, RegistrationUserData userData) throws ServiceException;
}
