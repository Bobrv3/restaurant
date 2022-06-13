package com.epam.restaurant.service;

import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.AuthorizedUser;

public interface UserService {
    AuthorizedUser signIn(String login, char[] password) throws ServiceException;
    boolean signUp(RegistrationUserData userData) throws ServiceException;
//    List<User> find(Criteria criteria);
}
