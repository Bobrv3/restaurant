package com.epam.restaurant.dao.impl;

import com.epam.restaurant.bean.Order;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.criteria.SearchCriteria;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.DAOProvider;
import com.epam.restaurant.dao.OrderDAO;
import com.epam.restaurant.dao.util.TransactionImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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
    void createOrder_equalsNextGeneratedId_True() throws DAOException, InterruptedException {
        MyThread thread1 = new MyThread();
        MyThread thread2 = new MyThread();

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            try {
                int nextId = 44;
                DAOProvider.getInstance().getTransactionDAO().startTransaction();
                int actualId = orderDAO.createOrder(new Order(), 9);
                DAOProvider.getInstance().getTransactionDAO().commit();
                Assertions.assertEquals(nextId, actualId);
            } catch (DAOException e) {
                try {
                    DAOProvider.getInstance().getTransactionDAO().rollback();
                } catch (DAOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Test
    void createOrderDetails_SuccessToCreateOrderDetail_True() throws DAOException {
        Assertions.assertTrue(orderDAO.createOrderDetails(44, 2, 3, "takeaway"));
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
        Assertions.assertEquals(14, orderDAO.find(criteria).size());
    }

    @Test
    void findOrdersWithUsersInfo_SizeOfListMoreThan0_true() throws DAOException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Orders.ORDER_STATUS.toString(), "in Processing");

        Assertions.assertTrue(orderDAO.findOrdersWithUsersInfo(criteria).size() > 0);
    }

    @Test
    void updateOrderStatus_withOrderIdEq5_true() throws DAOException {
        int orderIdForConfirming = 5;
        String status = "confirmed";

        Assertions.assertTrue(orderDAO.updateOrderStatus(orderIdForConfirming, status));
    }

    @Test
    void findOrdersWithDishInfo_SizeOfListMoreThan0_true() throws DAOException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Orders.ORDER_STATUS.toString(), "confirmed");

        Assertions.assertTrue(orderDAO.findOrdersWithDishInfo(criteria).size() > 0);
    }
}