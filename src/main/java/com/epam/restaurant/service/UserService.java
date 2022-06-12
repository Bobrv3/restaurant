package com.epam.restaurant.service;

import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.User;
import com.epam.restaurant.bean.criteria.Criteria;

import java.util.List;

public interface UserService {
    User signIn(String login, char[] password) throws ServiceException;
    boolean signUp(RegistrationUserData userData) throws ServiceException;
//    List<User> find(Criteria criteria);
}
