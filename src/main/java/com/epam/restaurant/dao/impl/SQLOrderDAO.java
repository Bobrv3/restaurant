package com.epam.restaurant.dao.impl;

import com.epam.restaurant.bean.Order;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.criteria.SearchCriteria;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.OrderDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLOrderDAO implements OrderDAO {
    private static final Logger LOGGER = LogManager.getLogger(SQLOrderDAO.class);
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String INSERT_ORDER_QUERY = "INSERT INTO orders (date, order_status, user_id) VALUES (?, ?, ?)";
    private static final String INSERT_ORDER_DETAIL_QUERY = "INSERT INTO order_details (orders_id, menu_dishes_id, quantity, methodOfReceiving) VALUES (?, ?, ?, ?)";
    private static final String FIND_ORDER_BY_CRITERIA_QUERY = "SELECT ord.id, SUM(ordd.quantity * m.price) as 'total',  ord.date, methodOfReceiving FROM orders ord LEFT JOIN order_details ordd on ordd.orders_id = ord.id LEFT JOIN menu m on ordd.menu_dishes_id = m.dishes_id where {0} group by ord.id;";

    private static final String AND = "AND ";

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
                order.setDate(resultSet.getDate(3));
                order.setMethodOfReceiving(resultSet.getString(4));
                orders.add(order);
            }

            return orders;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to getAllUserOrders", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, statement, resultSet);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }
    }
}
