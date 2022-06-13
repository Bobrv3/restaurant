package com.epam.restaurant.dao.impl;

import com.epam.restaurant.bean.AuthorizedUser;
import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.criteria.SearchCriteria;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.DAOProvider;
import com.epam.restaurant.dao.UserDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SQLUserDAOTest {
    private static ConnectionPool connectionPool;
    private static final UserDAO userDAO = DAOProvider.getInstance().getUserDAO();

    private final String existing_login = "anton98";
    private final char[] existing_password = new char[] {'1'};

    private final String non_existent_login = "jhgjhj";
    private final char[] non_existent_password = new char[] {'1', '5', '8'};

    private final RegistrationUserData newUser = new RegistrationUserData("testUs1", "123".toCharArray(), "TestUser", "+375446785678", "ant@gmail.com", 2);
    private final RegistrationUserData existentUser = new RegistrationUserData("testUs1", "123".toCharArray(), "TestUser", "+375446785678", "ant@gmail.com", 2);

    @BeforeAll
    static void beforeAll() throws DAOException {
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initConnectionPool();
    }

    @AfterAll
    static void afterAll() throws DAOException {
        connectionPool.dispose();
    }

    @Test
    void signIn_UserExist_AuthorizedUser() throws DAOException {
        AuthorizedUser expected = new AuthorizedUser(2, "Anton", 2);

        AuthorizedUser actual = userDAO.signIn(existing_login, existing_password);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void signIn_UserNotExist_Null() throws DAOException {
        AuthorizedUser expected = null;

        AuthorizedUser actual = userDAO.signIn(non_existent_login, non_existent_password);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void signIn_UserExistPasswordNotCorrect_Null() throws DAOException {
        AuthorizedUser expected = null;

        AuthorizedUser actual = userDAO.signIn(existing_login, non_existent_password);

        Assertions.assertEquals(expected, actual);
    }

//    @Test
//    void signUp_NewUser_true() throws DAOException {
//        boolean expected = true;
//
//        boolean actual = userDAO.signUp(newUser);
//
//        Assertions.assertEquals(expected, actual);
//    }

    @Test
    void signUp_ThisUserAlreadyExist_false() throws DAOException {
        boolean expected = false;

        boolean actual = userDAO.signUp(existentUser);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void find_UserWithCriteriaExist_authorizedUser() throws DAOException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Users.LOGIN.toString(), "anton98");

        AuthorizedUser authorizedUser = new AuthorizedUser(2, "Anton", 2);

        List<AuthorizedUser> expected = new ArrayList<>();
        expected.add(authorizedUser);

        List<AuthorizedUser> actual = userDAO.find(criteria);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void find_UserWithCriteriaNoExist_null() throws DAOException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Users.LOGIN.toString(), "fgfdsgdsfg");

        List<AuthorizedUser> expected = null;

        List<AuthorizedUser> actual = userDAO.find(criteria);

        Assertions.assertEquals(expected, actual);
    }
}