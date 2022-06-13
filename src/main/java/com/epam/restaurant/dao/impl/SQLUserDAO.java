package com.epam.restaurant.dao.impl;

import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.AuthorizedUser;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SQLUserDAO implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(SQLUserDAO.class);
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String USER_AUTHORIZATION_QUERY = "SELECT id, name, role_id FROM users WHERE login=? AND password=?";
    private static final String CHECK_USER_EXISTENCE_QUERY = "SELECT id FROM users WHERE login=?";
    private static final String REGISTER_USER_QUERY = "INSERT INTO users(login, password, name, phone_number, email, role_id) VALUES(?,?,?,?,?,?)";
    private static final String FIND_USER_BY_CRITERIA_QUERY = "Select id, name, role_id from users where ";

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
            preparedStatement.setString(2, new String(password));
            resultSet = preparedStatement.executeQuery();

            Arrays.fill(password, ' ');

            if (!resultSet.next()) {
                return null;
            }

            AuthorizedUser user = new AuthorizedUser();

            user.setId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setRoleId(resultSet.getInt(3));

            return user;

        } catch (SQLException e) {
            throw new DAOException("Error when trying to create a prepared user authorization query", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    // TODO make it concurrency
    @Override
    public boolean signUp(RegistrationUserData userData) throws DAOException {
        // TODO на слое сервисов добавить валидацию введенных значений

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
            preparedStatement.setString(2, new String(userData.getPassword())); // TODO insert password with bcrypt
            preparedStatement.setString(3, userData.getName());
            preparedStatement.setString(4, userData.getPhoneNumber());
            preparedStatement.setString(5, userData.getEmail());
            preparedStatement.setInt(6, userData.getRoleId());
            preparedStatement.executeUpdate();

            Arrays.fill(userData.getPassword(), ' ');

            return true;

        } catch (SQLException e) {
            throw new DAOException("Error when trying to create a prepared user registration query", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    // TODO сделать для prepared statement: придумать как избавиться от '' после вставки на место ?
    @Override
    public List<AuthorizedUser> find(Criteria criteria) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Map<String, Object> criterias = criteria.getCriteria();

        try {
            connection = connectionPool.takeConnection();

            StringBuffer queryBuilder = new StringBuffer(FIND_USER_BY_CRITERIA_QUERY);
            for (String key : criterias.keySet()) {
                queryBuilder.append(key.toLowerCase() +"='" + (String) criterias.get(key) + "' " + AND);
            }
            queryBuilder = new StringBuffer(queryBuilder.substring(0,queryBuilder.length() - AND.length()));

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(queryBuilder.toString());

            if (!resultSet.next()) {
                return null;
            }

            List<AuthorizedUser> users = new ArrayList<>();
            while (resultSet.next()) {
                AuthorizedUser user = new AuthorizedUser();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setRoleId(resultSet.getInt(3));
                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            throw new DAOException("Error when trying to create a statement user find query", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }
}
