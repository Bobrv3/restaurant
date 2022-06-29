package com.epam.restaurant.dao.impl;

import com.epam.restaurant.bean.Order;
import com.epam.restaurant.controller.command.impl.Registration;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.OrderDAO;
import com.epam.restaurant.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLOrderDAO implements OrderDAO {
    private static final Logger LOGGER = LogManager.getLogger(SQLOrderDAO.class);
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String INSERT_ORDER = "INSERT INTO orders (date, status, user_id) VALUES (?, ?, ?)";
    private static final String IN_PROCESSING = "in processing";
    private static final int GENERATED_KEYS = 1;

    @Override
    public int createOrder(Order order, int userId) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
            preparedStatement.setString(2, IN_PROCESSING);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            return resultSet.getInt(GENERATED_KEYS);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to create order", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }
    }
}
