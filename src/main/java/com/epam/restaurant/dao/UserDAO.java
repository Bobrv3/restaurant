package com.epam.restaurant.dao;

import com.epam.restaurant.entity.User;

public interface UserDAO {
    User signIn(String login, String password) throws DAOException;
    User registration(User user) throws DAOException;
}
