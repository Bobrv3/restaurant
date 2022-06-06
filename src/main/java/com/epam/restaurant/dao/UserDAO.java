package com.epam.restaurant.dao;

import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.User;

public interface UserDAO {
    User signIn(String login, String password) throws DAOException;
    boolean registration(RegistrationUserData userData) throws DAOException;
}
