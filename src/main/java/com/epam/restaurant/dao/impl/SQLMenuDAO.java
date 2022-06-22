package com.epam.restaurant.dao.impl;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.MenuDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLMenuDAO implements MenuDAO {
    private static final Logger LOGGER = LogManager.getLogger(SQLMenuDAO.class);
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String GET_ALL_CATEGORIES_QUERY = "SELECT * FROM categories ORDER BY id";
    private static final String GET_MENU_QUERY = "SELECT dishes_id, name, description, price, category_id FROM menu where status != 1;";

    @Override
    public Menu getMenu() throws DAOException {
        Menu menu = null;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_MENU_QUERY);

            if (!resultSet.isBeforeFirst()) {
                return null;
            }

            menu = new Menu();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                BigDecimal price = BigDecimal.valueOf(resultSet.getDouble(4));
                int category_id = resultSet.getInt(5);

                menu.add(new Dish(id, price, name, description, category_id));
            }
        } catch (SQLException e) {
            throw new DAOException("Error when trying to get menu", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        }finally {
            try {
                connectionPool.closeConnection(connection, statement, resultSet);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }

        return menu;
    }

    @Override
    public List<Category> getCategories() throws DAOException {
        List<Category> categories = null;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ALL_CATEGORIES_QUERY);

            if (!resultSet.isBeforeFirst()) {
                return null;
            }

            categories = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);

                categories.add(new Category(id, name));
            }
        } catch (SQLException e) {
            throw new DAOException("Error when trying to get menu", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        }finally {
            try {
                connectionPool.closeConnection(connection, statement, resultSet);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }

        return categories;
    }
}
