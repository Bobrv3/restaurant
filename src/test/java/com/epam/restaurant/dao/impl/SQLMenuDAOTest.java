package com.epam.restaurant.dao.impl;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.DAOProvider;
import com.epam.restaurant.dao.MenuDAO;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SQLMenuDAOTest {
    private static ConnectionPool connectionPool;
    private static final MenuDAO menuDAO = DAOProvider.getInstance().getMenuDAO();

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
    void getMenu_SecondIdBelongsToSchi_true() throws DAOException {
        Menu menu = menuDAO.getMenu();
        Dish dish = menu.getDishes().get(1);

        String dishWithSecondId = "shchi";

        Assertions.assertEquals(dishWithSecondId, dish.getName());
    }

    @Test
    void getCategories_NumOfCategoriesEquals5_true() throws DAOException {
        List<Category> categories = menuDAO.getCategories();
        int NumOfCategories = 5;

        Assertions.assertEquals(NumOfCategories, categories.size());
    }
}