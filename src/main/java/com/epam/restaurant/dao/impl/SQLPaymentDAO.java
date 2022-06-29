package com.epam.restaurant.dao.impl;

import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.PaymentDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLPaymentDAO implements PaymentDAO {
    private static final Logger LOGGER = LogManager.getLogger(SQLPaymentDAO.class);
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String INSERT_INVOICE_QUERY = "INSERT INTO invoice(date, status, orders_id) VALUES(?,?,?)";
    private static final String UNPAID = "unpaid";
    private static final int GENERATED_KEYS = 1;

    @Override
    public int createInvoice(int orderId) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(INSERT_INVOICE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
            preparedStatement.setString(2, UNPAID);
            preparedStatement.setInt(3, orderId);
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
