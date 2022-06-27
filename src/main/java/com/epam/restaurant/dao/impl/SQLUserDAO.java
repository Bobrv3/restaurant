package com.epam.restaurant.dao.impl;

import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.AuthorizedUser;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SQLUserDAO implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(SQLUserDAO.class);
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String USER_AUTHORIZATION_QUERY = "SELECT login, name, role_id, password FROM users WHERE login=?";
    private static final String CHECK_USER_EXISTENCE_QUERY = "SELECT id FROM users WHERE login=?";
    private static final String REGISTER_USER_QUERY = "INSERT INTO users(login, password, name, phone_number, email, role_id) VALUES(?,?,?,?,?,?)";
    private static final String FIND_USER_BY_CRITERIA_QUERY = "Select login, name, role_id from users where ";

    private static final String AND = "AND ";

    @Override
    public AuthorizedUser signIn(String login, char[] password) throws DAOException {
        // TODO в слое сервисов посмотреть в куки: был ли пользователь авторизован или нет

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(USER_AUTHORIZATION_QUERY);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            if (!BCrypt.checkpw(
                    Arrays.toString(password),                   // auth user psw
                        resultSet.getString(4))) {   // psw from db
                return null;
            }

            AuthorizedUser user = new AuthorizedUser();

            user.setLogin(resultSet.getString(1));
            user.setName(resultSet.getString(2));
            user.setRoleId(resultSet.getInt(3));

            return user;

        } catch (SQLException e) {
            throw new DAOException("Error when trying to create a prepared user authorization query", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        } finally {
            Arrays.fill(password, ' ');

            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }
    }

    @Override
    public synchronized boolean signUp( RegistrationUserData userData) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(CHECK_USER_EXISTENCE_QUERY);
            preparedStatement.setString(1, userData.getLogin());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return false; // that means that the user with this username is already registered
            }

            preparedStatement = connection.prepareStatement(REGISTER_USER_QUERY);
            preparedStatement.setString(1, userData.getLogin());
            preparedStatement.setString(2, BCrypt.hashpw(Arrays.toString(userData.getPassword()), BCrypt.gensalt()));
            preparedStatement.setString(3, userData.getName());
            preparedStatement.setString(4, userData.getPhoneNumber());
            preparedStatement.setString(5, userData.getEmail());
            preparedStatement.setInt(6, userData.getRoleId());
            preparedStatement.executeUpdate();

            Arrays.fill(userData.getPassword(), ' ');

            return true;

        } catch (SQLException e) {
            throw new DAOException("Error when trying to create a prepared user registration query", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }
    }

    // TODO сделать для prepared statement: придумать как избавиться от '' после вставки на место ?
    @Override
    public List<AuthorizedUser> find(Criteria criteria) throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        Map<String, Object> criterias = criteria.getCriteria();

        try {
            connection = connectionPool.takeConnection();

            StringBuilder queryBuilder = new StringBuilder(FIND_USER_BY_CRITERIA_QUERY);
            for (String key : criterias.keySet()) {
                queryBuilder.append(MessageFormat.format("{0}=''{1}'' {2}", key.toLowerCase(), criterias.get(key), AND));
            }
            queryBuilder = new StringBuilder(queryBuilder.substring(0, queryBuilder.length() - AND.length()));

            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryBuilder.toString());

            if (!resultSet.isBeforeFirst()) {
                return null;
            }

            List<AuthorizedUser> users = new ArrayList<>();
            while (resultSet.next()) {
                AuthorizedUser user = new AuthorizedUser();
                user.setLogin(resultSet.getString(1));
                user.setName(resultSet.getString(2));
                user.setRoleId(resultSet.getInt(3));
                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            throw new DAOException("Error when trying to create a statement user find query", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, statement, resultSet);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }
    }
}
