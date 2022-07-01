package com.epam.restaurant.dao.impl;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.MenuDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLMenuDAO implements MenuDAO {
    private static final Logger LOGGER = LogManager.getLogger(SQLMenuDAO.class);
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String GET_ALL_CATEGORIES_QUERY = "SELECT * FROM categories ORDER BY id";
    private static final String GET_MENU_QUERY = "SELECT dishes_id, name, description, price, category_id FROM menu where status != 1;";
    private static final String FIND_DISH_BY_CRITERIA_QUERY = "Select dishes_id, name, description, price, category_id FROM menu where ";
    private static final String REMOVE_DISH_BY_CRITERIA_QUERY = "UPDATE menu SET status=1 where ";
    private static final String EDIT_CATEGORY_QUERY = "UPDATE categories SET name=? where id=?";

    private static final String AND = "AND ";

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
            throw new DAOException("Error when trying to get categories", e);
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

    @Override
    public List<Dish> find(Criteria criteria) throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        Map<String, Object> criterias = criteria.getCriteria();

        try {
            connection = connectionPool.takeConnection();

            StringBuilder queryBuilder = new StringBuilder(FIND_DISH_BY_CRITERIA_QUERY);
            for (String key : criterias.keySet()) {
                queryBuilder.append(MessageFormat.format("{0}=''{1}'' {2}", key.toLowerCase(), criterias.get(key), AND));
            }
            queryBuilder = new StringBuilder(queryBuilder.substring(0, queryBuilder.length() - AND.length()));

            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryBuilder.toString());

            if (!resultSet.isBeforeFirst()) {
                return null;
            }

            List<Dish> dishes = new ArrayList<>();
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt(1));
                dish.setName(resultSet.getString(2));
                dish.setDescription(resultSet.getString(3));
                dish.setPrice(BigDecimal.valueOf(resultSet.getDouble(4)));
                dish.setCategory_id(resultSet.getInt(5));

                dishes.add(dish);
            }

            return dishes;

        } catch (SQLException e) {
            throw new DAOException("Error when trying to create a statement dish find query", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, statement, resultSet);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }
    }

    @Override
    public int remove(Criteria criteria) throws DAOException {
        Connection connection = null;
        Statement statement = null;

        Map<String, Object> criterias = criteria.getCriteria();

        try {
            connection = connectionPool.takeConnection();

            StringBuilder queryBuilder = new StringBuilder(REMOVE_DISH_BY_CRITERIA_QUERY);
            for (String key : criterias.keySet()) {
                queryBuilder.append(MessageFormat.format("{0}=''{1}'' {2}", key.toLowerCase(), criterias.get(key), AND));
            }
            queryBuilder = new StringBuilder(queryBuilder.substring(0, queryBuilder.length() - AND.length()));

            statement = connection.createStatement();

            return statement.executeUpdate(queryBuilder.toString());

        } catch (SQLException e) {
            throw new DAOException("Error when trying to create a statement dish find query", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, statement, null);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }
    }

    @Override
    public boolean editCategory(int editedCategoryId, String newCategoryName) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();

             statement = connection.prepareStatement(EDIT_CATEGORY_QUERY);
             statement.setString(1, newCategoryName);
             statement.setInt(2, editedCategoryId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DAOException("Error when trying to create a prepareStatement in edit category query", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DAOException("Error when trying to take connection", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, statement, null);
            } catch (SQLException e) {
                LOGGER.error("Error to close connection...", e);
            }
        }
    }
}
