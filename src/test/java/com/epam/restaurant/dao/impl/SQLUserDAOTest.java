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

    private Criteria criteria = new Criteria();

    public SQLUserDAOTest() {
        criteria.add(SearchCriteria.Users.LOGIN.name(), "testUs4");
    }

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
        AuthorizedUser expected = new AuthorizedUser("testUs4", newUser.getName(), newUser.getRoleId());
        AuthorizedUser actual = userDAO.signIn(existing_login, existing_password);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void signIn_UserNotExist_Null() throws DAOException {
        AuthorizedUser actual = userDAO.signIn(non_existent_login, non_correct_password);

        Assertions.assertNull(actual.getLogin());
    }

    @Test
    void signIn_UserExistPasswordNotCorrect_Null() throws DAOException {
        AuthorizedUser actual = userDAO.signIn(existing_login, non_correct_password);

        Assertions.assertNull(actual.getLogin());
    }

//    @Test
//    void signUp_NewUser_true() throws DAOException {
//        boolean actual = userDAO.signUp(newUser);
//
//        Assertions.assertTrue(actual);
//    }

    @Test
    void signUp_ThisUserAlreadyExist_false() throws DAOException {
        boolean actual = userDAO.signUp(existentUser);

        Assertions.assertFalse(actual);
    }

    @Test
    void find_UserWithCriteriaExist_authorizedUser() throws DAOException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Users.LOGIN.toString(), "testUs4");

        RegistrationUserData userData = new RegistrationUserData();
        userData.setId(10);
        userData.setLogin("testUs4");
        userData.setName("TestUser4");
        userData.setPhoneNumber("+375446785678");
        userData.setEmail("ant@gmail.com");
        userData.setRoleId(2);

        List<RegistrationUserData> expected = new ArrayList<>();
        expected.add(userData);

        List<RegistrationUserData> actual = userDAO.find(criteria);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void find_UserWithCriteriaNoExist_EmptyList_true() throws DAOException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Users.LOGIN.toString(), "fgfdsgdsfg");

        List<RegistrationUserData> actual = userDAO.find(criteria);

        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void updateUserName_succsesfull_true() throws DAOException {
        RegistrationUserData userDataInDB = userDAO.find(criteria).get(0);

        RegistrationUserData userData = new RegistrationUserData();
        userData.setName(userDataInDB.getName());

        Assertions.assertTrue(userDAO.updateUser(userDataInDB.getLogin(), userData));
    }

    @Test
    void updateUserPhone_succsesfull_true() throws DAOException {
        RegistrationUserData userDataInDB = userDAO.find(criteria).get(0);

        RegistrationUserData userData = new RegistrationUserData();
        userData.setName(userDataInDB.getPhoneNumber());

        Assertions.assertTrue(userDAO.updateUser(userDataInDB.getLogin(), userData));
    }

    @Test
    void updateUserEmail_succsesfull_true() throws DAOException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Users.LOGIN.name(), "testUs4");

        RegistrationUserData userDataInDB = userDAO.find(criteria).get(0);

        RegistrationUserData userData = new RegistrationUserData();
        userData.setName(userDataInDB.getEmail());

        Assertions.assertTrue(userDAO.updateUser(userDataInDB.getLogin(), userData));
    }
}