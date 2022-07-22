package com.epam.restaurant.dao;

import com.epam.restaurant.bean.AuthorizedUser;
import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.criteria.Criteria;

import java.util.List;

public interface UserDAO {
    AuthorizedUser signIn(String login, char[] password) throws DAOException;

    boolean signUp(RegistrationUserData userData) throws DAOException;

    List<RegistrationUserData> find(Criteria criteria) throws DAOException;

    List<RegistrationUserData> findByLoginPattern(String loginPattern) throws DAOException;

    RegistrationUserData updateUser(RegistrationUserData userData) throws DAOException;
}
