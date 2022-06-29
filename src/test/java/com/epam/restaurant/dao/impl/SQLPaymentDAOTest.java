package com.epam.restaurant.dao.impl;

import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.DAOProvider;
import com.epam.restaurant.dao.OrderDAO;
import com.epam.restaurant.dao.PaymentDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class SQLPaymentDAOTest {
    private static ConnectionPool connectionPool;
    private static final PaymentDAO paymentDAO = DAOProvider.getInstance().getPaymentDAO();

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
    void createInvoice_equalsNextInvoiceId_true() throws DAOException {
        int nextId = 1;
        int actualId = paymentDAO.createInvoice(6);

        Assertions.assertEquals(nextId, actualId);
    }
}