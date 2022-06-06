package com.epam.restaurant.dao.impl;

import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.User;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(SQLUserDAO.class);

    private static final String USER_AUTHORIZATION_QUERY = "SELECT id, roles_id FROM users WHERE login=? AND password=?";
    private static final String CHECK_USER_EXISTENCE_QUERY = "SELECT id, roles_id FROM users WHERE login=?";
    private static final String REGISTER_USER_QUERY = "INSERT INTO users(login, password, name, phone_number, email, roles_id) VALUES(?,?,?,?,?,?)";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public User signIn(String login, String password) throws DAOException {
        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(USER_AUTHORIZATION_QUERY);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            User user = new User();

            user.setId(resultSet.getInt(1));
            user.setRoleId(resultSet.getInt(2));

            return user;

        } catch (SQLException e) {
            throw new DAOException("Error when trying to create a prepared user authorization query", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    // TODO make it concurrency
    @Override
    public boolean registration(RegistrationUserData userData) throws DAOException {
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
            preparedStatement.setString(2, userData.getPassword()); // TODO insert password with bcrypt
            preparedStatement.setString(3, userData.getName());
            preparedStatement.setString(4, userData.getPhoneNumber());
            preparedStatement.setString(5, userData.getEmail());
            preparedStatement.setInt(6, userData.getRoleId());
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new DAOException("Error when trying to create a prepared user registration query", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }
}
