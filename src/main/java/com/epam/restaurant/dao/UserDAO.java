package com.epam.restaurant.dao;

import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.User;

import java.util.List;

public interface UserDAO {
    User signIn(String login, String password) throws DAOException;
    boolean registration(RegistrationUserData userData) throws DAOException;
    List<User> find(Criteria criteria) throws DAOException;
}
