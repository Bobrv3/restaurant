package com.epam.restaurant.dao.impl;

import com.epam.restaurant.bean.Order;
import com.epam.restaurant.bean.OrderForCooking;
import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.OrderDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SQLOrderDAO implements OrderDAO {
    private static final Logger LOGGER = LogManager.getLogger(SQLOrderDAO.class);
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String INSERT_ORDER_QUERY = "INSERT INTO orders (date, order_status, user_id) VALUES (?, ?, ?)";
    private static final String INSERT_ORDER_DETAIL_QUERY = "INSERT INTO order_details (orders_id, menu_dishes_id, quantity, methodOfReceiving) VALUES (?, ?, ?, ?)";
    private static final String FIND_ORDER_BY_CRITERIA_QUERY = "SELECT ord.id, SUM(ordd.quantity * m.price) as 'total',  ord.date, methodOfReceiving FROM orders ord LEFT JOIN order_details ordd on ordd.orders_id = ord.id LEFT JOIN menu m on ordd.menu_dishes_id = m.dishes_id where {0} group by ord.id;";
    private static final String FIND_ORDER_WITH_USER_BY_CRITERIA_QUERY = "SELECT ord.id, SUM(ordd.quantity * m.price) as 'total',  ord.date, methodOfReceiving, u.name, phone_number, email FROM orders ord LEFT JOIN order_details ordd on ordd.orders_id = ord.id LEFT JOIN menu m on ordd.menu_dishes_id = m.dishes_id LEFT JOIN users u on user_id=u.id where {0} group by ord.id;";
    private static final String FIND_ORDER_WITH_DISH_BY_CRITERIA_QUERY = "SELECT ord.id,  m.name, methodOfReceiving FROM orders ord LEFT JOIN order_details ordd on ordd.orders_id = ord.id LEFT JOIN menu m on ordd.menu_dishes_id = m.dishes_id where {0};";
    private static final String UPDATE_ORDER_STATUS_QUERY = "UPDATE orders SET order_status='confirmed' where id=?;";

    private static final String AND = "AND ";
    private static final int NUM_OF_UPDATED_ROWS = 1;

    private static final String IN_PROCESSING = "in processing";
    private static final int GENERATED_KEYS = 1;

    @Override
    public int createOrder(Order order, int userId) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(INSERT_ORDER_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp (1, new Timestamp(System.currentTimeMillis()));
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

    @Override
    public boolean createOrderDetails(int oderId, int menuId, Integer quantity, String methodOfReceiving) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(INSERT_ORDER_DETAIL_QUERY);
            preparedStatement.setInt(1, oderId);
            preparedStatement.setInt(2, menuId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setString(4, methodOfReceiving);
            preparedStatement.executeUpdate();

            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to createOrderDetails", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }
    }

    @Override
    public List<Order> find(Criteria criteria) throws DAOException {
        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            Map<String, Object> criterias = criteria.getCriteria();

            StringBuilder where = new StringBuilder("");
            for (String key : criterias.keySet()) {
                where.append(MessageFormat.format("{0}=''{1}'' {2}", key.toLowerCase(), criterias.get(key), AND));
            }
            where = new StringBuilder(where.substring(0, where.length() - AND.length()));
            String query = MessageFormat.format(FIND_ORDER_BY_CRITERIA_QUERY, where);

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt(1));
                order.setTotalPrice(resultSet.getBigDecimal(2));
                order.setDateTime(resultSet.getTimestamp(3));
                order.setMethodOfReceiving(resultSet.getString(4));
                orders.add(order);
            }

            return orders;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to find", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, statement, resultSet);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }
    }

    @Override
    public Map<Order, RegistrationUserData> findOrdersWithUsersInfo(Criteria criteria) throws DAOException {
        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            Map<String, Object> criterias = criteria.getCriteria();

            StringBuilder where = new StringBuilder("");
            for (String key : criterias.keySet()) {
                where.append(MessageFormat.format("{0}=''{1}'' {2}", key.toLowerCase(), criterias.get(key), AND));
            }
            where = new StringBuilder(where.substring(0, where.length() - AND.length()));
            String query = MessageFormat.format(FIND_ORDER_WITH_USER_BY_CRITERIA_QUERY, where);

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            Map<Order, RegistrationUserData> orderUserDataMap = new LinkedHashMap<>();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt(1));
                order.setTotalPrice(resultSet.getBigDecimal(2));
                order.setDateTime(resultSet.getTimestamp(3));
                order.setMethodOfReceiving(resultSet.getString(4));

                RegistrationUserData userData = new RegistrationUserData();
                userData.setName(resultSet.getString(5));
                userData.setPhoneNumber(resultSet.getString(6));
                userData.setEmail(resultSet.getString(7));

                orderUserDataMap.put(order, userData);
            }

            return orderUserDataMap;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to findOrdersWithUsersInfo", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, statement, resultSet);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }
    }

    @Override
    public boolean confirmOrder(int orderID) throws DAOException {
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            statement = connection.prepareStatement(UPDATE_ORDER_STATUS_QUERY);
            statement.setInt(1, orderID);
            int num = statement.executeUpdate();

            return num == NUM_OF_UPDATED_ROWS;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to confirmOrder", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, statement, resultSet);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }
    }

    @Override
    public List<OrderForCooking> findOrdersWithDishInfo(Criteria criteria) throws DAOException {
        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            Map<String, Object> criterias = criteria.getCriteria();

            StringBuilder where = new StringBuilder("");
            for (String key : criterias.keySet()) {
                where.append(MessageFormat.format("{0}=''{1}'' {2}", key.toLowerCase(), criterias.get(key), AND));
            }
            where = new StringBuilder(where.substring(0, where.length() - AND.length()));
            String query = MessageFormat.format(FIND_ORDER_WITH_DISH_BY_CRITERIA_QUERY, where);

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            List<OrderForCooking> orderForCookingList = new ArrayList<>();
            while (resultSet.next()) {
                OrderForCooking order = new OrderForCooking();
                order.setOrderId(resultSet.getInt(1));
                order.setDishName(resultSet.getString(2));
                order.setMethodOfReceiving(resultSet.getString(3));
                orderForCookingList.add(order);
            }

            return orderForCookingList;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to findOrdersWithUsersInfo", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, statement, resultSet);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }
    }
}
