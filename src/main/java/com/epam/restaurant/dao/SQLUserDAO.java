package com.epam.restaurant.dao;

import com.epam.restaurant.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(SQLUserDAO.class);
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public User signIn(String login, String password) throws DAOException {
        User user = new User();

        try {
            connectionPool.initConnectionPool();

            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login=? AND password=?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            // if there is no user with this login and password in the database, then the number of rows in the resultSet will be 0
            if (resultSet.getRow() < 1) {
                return null;
            }

            while (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setName(resultSet.getString(4));
                user.setPhone_number(resultSet.getString(5));
                user.setEmail(resultSet.getString(6));
                user.setRolesId(resultSet.getInt(7));
            }
        } catch (SQLException e) {
            throw new DAOException("Error when trying to create a prepared user authorization query", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOGGER.error("error to close resultSet...");
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    LOGGER.error("error to close statement...");
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error("error to put connection into the pool...");
                }
            }
        }

        return user;
    }

    @Override
    public User registration(User user) throws DAOException {
        try {
            connectionPool.initConnectionPool();

            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login=?");
            preparedStatement.setString(1, user.getLogin());
            resultSet = preparedStatement.executeQuery();

            // if there is no user with this login and password in the database, then the number of rows in the resultSet will be 0
            if (resultSet.getRow() < 1) {
                preparedStatement = connection.prepareStatement("INSERT INTO users(login, password, name, phone_number, email, role_id) VALUES(?,?,?,?,?,?)");
                preparedStatement.setString(1, user.getLogin());
                // TODO insert password with bcrypt
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getName());
                preparedStatement.setString(4, user.getPhone_number());
                preparedStatement.setString(5, user.getEmail());
                preparedStatement.setInt(6, user.getRolesId());
                preparedStatement.executeUpdate();
            } else {
                return null; // this means that the user with this username is already registered
            }
        } catch (SQLException e) {
            throw new DAOException("Error when trying to create a prepared user authorization query", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOGGER.error("error to close resultSet...");
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    LOGGER.error("error to close statement...");
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error("error to put connection into the pool...");
                }
            }
        }

        return signIn(user.getLogin(), user.getPassword());
    }
}
