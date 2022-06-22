package com.epam.restaurant.dao.impl;

import com.epam.restaurant.bean.AuthorizedUser;
import com.epam.restaurant.bean.Category;
import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.criteria.SearchCriteria;
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
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Dishes.DISHES_ID.toString(), 2);

        List<Dish> dishes = menuDAO.find(criteria);
        Dish dishWithSecondId = dishes.get(0);

        Menu menu = menuDAO.getMenu();
        int secondId = 1;

        Assertions.assertEquals(dishWithSecondId, menu.getDishes().get(secondId));
    }

    @Test
    void getCategories_NumOfCategoriesEquals5_true() throws DAOException {
        List<Category> categories = menuDAO.getCategories();
        int NumOfCategories = 5;

        Assertions.assertEquals(NumOfCategories, categories.size());
    }

    @Test
    void find_DishWithCriteriaNoExist_null() throws DAOException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Dishes.NAME.toString(), "fgfdsgdsfg");

        List<Dish> actual = menuDAO.find(criteria);

        Assertions.assertNull(actual);
    }

    @Test
    void find_DishWithCriteriaExist_shchi() throws DAOException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Dishes.STATUS.toString(), "0");

        int currentSizeOfMenu = 2;

        List<Dish> actual = menuDAO.find(criteria);

        Assertions.assertEquals(currentSizeOfMenu, actual.size());
    }
}