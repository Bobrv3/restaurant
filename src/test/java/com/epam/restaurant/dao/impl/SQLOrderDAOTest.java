package com.epam.restaurant.dao.impl;

import com.epam.restaurant.bean.Order;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.criteria.SearchCriteria;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.DAOProvider;
import com.epam.restaurant.dao.MenuDAO;
import com.epam.restaurant.dao.OrderDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLOrderDAOTest {
    private static ConnectionPool connectionPool;
    private static final OrderDAO orderDAO = DAOProvider.getInstance().getOrderDAO();

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
    void createOrder_equalsNextGeneratedId_True() throws DAOException {
        int nextId = 1;
        int actualId = orderDAO.createOrder(new Order(), 9);

        Assertions.assertEquals(nextId, actualId);
    }

    @Test
    void createOrderDetails_SuccessToCreateOrderDetail_True() throws DAOException {
        Assertions.assertTrue(orderDAO.createOrderDetails(4, 2, 3, "takeaway"));
    }

    @Test
    void find_CountOfConfirmedOrdersFromUserWithId_10_equals_2_True() throws DAOException {
        int userId = 10;
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Orders.USER_ID.toString(), 10);
        criteria.add(SearchCriteria.Orders.ORDER_STATUS.toString(), "confirmed");
        Assertions.assertEquals(2, orderDAO.find(criteria).size());
    }

    @Test
    void find_CountOfProcessingOrdersFromUserWithId_9_equals_7_True() throws DAOException {
        int userId = 10;
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Orders.USER_ID.toString(), 9);
        criteria.add(SearchCriteria.Orders.ORDER_STATUS.toString(), "in Processing");
        Assertions.assertEquals(7, orderDAO.find(criteria).size());
    }
}