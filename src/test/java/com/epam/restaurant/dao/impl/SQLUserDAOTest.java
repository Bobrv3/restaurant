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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class SQLUserDAOTest {
    private static ConnectionPool connectionPool;
    private static final UserDAO userDAO = DAOProvider.getInstance().getUserDAO();

    private final RegistrationUserData newUser = new RegistrationUserData("testUs4", "123".toCharArray(), "TestUser", "+375446785678", "ant@gmail.com", 2);
    private final RegistrationUserData existentUser = new RegistrationUserData("testUs4", "123".toCharArray(), "TestUser", "+375446785678", "ant@gmail.com", 2);

    private final String existing_login = newUser.getLogin();
    private final char[] existing_password = newUser.getPassword();

    private final String non_existent_login = "jhgjhj";
    private final char[] non_correct_password = new char[] {'1', '5', '8'};

    @BeforeAll
    static void beforeAll() throws SQLException, ClassNotFoundException, InterruptedException {
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initConnectionPool();
    }

    @AfterAll
    static void afterAll() throws SQLException, InterruptedException {
        connectionPool.dispose();
    }

    @Test
    void signIn_UserExist_AuthorizedUser() throws DAOException {
        AuthorizedUser expected = new AuthorizedUser(10, newUser.getName(), newUser.getRoleId());
        AuthorizedUser actual = userDAO.signIn(existing_login, existing_password);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void signIn_UserNotExist_Null() throws DAOException {
        AuthorizedUser actual = userDAO.signIn(non_existent_login, non_correct_password);

        Assertions.assertNull(actual);
    }

    @Test
    void signIn_UserExistPasswordNotCorrect_Null() throws DAOException {
        AuthorizedUser actual = userDAO.signIn(existing_login, non_correct_password);

        Assertions.assertNull(actual);
    }

//    @Test
//    void signUp_NewUser_true() throws DAOException {
//        boolean actual = userDAO.signUp(newUser);
//
//        Assertions.assertTrue(actual);
//    }

    @Test
    void signUp_NewUserConcurrReg_true() throws DAOException, InterruptedException {
        UserWhoWantReg user1 = new UserWhoWantReg("TestUser3", new char[] {'1'});
        UserWhoWantReg user2 = new UserWhoWantReg("TestUser3", new char[] {'2'});
        UserWhoWantReg user3 = new UserWhoWantReg("TestUser3", new char[] {'3'});
        UserWhoWantReg user4 = new UserWhoWantReg("TestUser3", new char[] {'4'});

        Thread thred1 = new Thread(user1);
        Thread thred2 = new Thread(user2);
        Thread thred3 = new Thread(user3);
        Thread thred4 = new Thread(user4);

        thred1.start();
        thred2.start();
        thred3.start();
        thred4.start();

        thred1.join();
        thred2.join();
        thred3.join();
        thred4.join();

        List<Boolean> registrationResults = new ArrayList<>();
        registrationResults.add(user1.resultOfRegistr);
        registrationResults.add(user2.resultOfRegistr);
        registrationResults.add(user3.resultOfRegistr);
        registrationResults.add(user4.resultOfRegistr);

        boolean oneResSuccess = registrationResults.stream().findAny().equals(true);

        Assertions.assertTrue(oneResSuccess);
    }

    private class UserWhoWantReg implements Runnable {
        private boolean resultOfRegistr = false;
        private RegistrationUserData userData;

        public UserWhoWantReg(String login, char[] password) {
            userData = new RegistrationUserData(
                    login,
                    password,
                    Thread.currentThread().getName(),
                    "8028",
                    "fgh@hg.com",
                    2);
        }

        @Override
        public void run() {
            try {
                resultOfRegistr = userDAO.signUp(userData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void signUp_ThisUserAlreadyExist_false() throws DAOException {
        boolean actual = userDAO.signUp(existentUser);

        Assertions.assertFalse(actual);
    }

    @Test
    void find_UserWithCriteriaExist_authorizedUser() throws DAOException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Users.LOGIN.toString(), "testUs4");

        AuthorizedUser authorizedUser = new AuthorizedUser(10, "TestUser", 2);

        List<AuthorizedUser> expected = new ArrayList<>();
        expected.add(authorizedUser);

        List<AuthorizedUser> actual = userDAO.find(criteria);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void find_UserWithCriteriaNoExist_null() throws DAOException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Users.LOGIN.toString(), "fgfdsgdsfg");

        List<AuthorizedUser> actual = userDAO.find(criteria);

        Assertions.assertNull(actual);
    }
}