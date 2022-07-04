package com.epam.restaurant.dao.impl;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.criteria.SearchCriteria;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.DAOProvider;
import com.epam.restaurant.dao.MenuDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

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

        Assertions.assertEquals(dishWithSecondId.getName(), menu.getDishes().get(secondId).getName());
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

        int currentSizeOfMenu = 3;

        List<Dish> actual = menuDAO.find(criteria);

        Assertions.assertEquals(currentSizeOfMenu, actual.size());
    }

    @Test
    void remove_DishWithId3_equal_1() throws DAOException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Dishes.DISHES_ID.toString(), 3);

        int numOdRemovedDishes = menuDAO.remove(criteria);

        Assertions.assertEquals(1, numOdRemovedDishes);
    }

    @Test
    void editCategory_ResultOfUpdateMoreThan0_true() throws DAOException {
        int editedCategoryId = 2;

        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Dishes.DISHES_ID.toString(), editedCategoryId);

        List<Dish> dishes = menuDAO.find(criteria);
        String newCategoryName = dishes.get(0).getName();

        Assertions.assertTrue(menuDAO.editCategory(editedCategoryId, newCategoryName));
    }

    @Test
    void editDish_ThrowEx_true() throws DAOException {
        int editedDishId = 2;

        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Dishes.DISHES_ID.toString(), editedDishId);

        List<Dish> dishes = menuDAO.find(criteria);
        String newDishName = dishes.get(0).getName();
        BigDecimal newPrice = dishes.get(0). getPrice();
        String newDescription = dishes.get(0).getDescription();
        String photo_link = dishes.get(0).getPhoto_link();

        try {
            menuDAO.editDish(editedDishId, newDishName, newDescription, newPrice, photo_link);
        } catch (DAOException e) {
            Assertions.assertTrue(e.getCause().getClass() == java.sql.SQLIntegrityConstraintViolationException.class);
        }
    }

//    @Test
//    void addCategory_ReturnedIndxMoreThan0_true() throws DAOException {
//        Assertions.assertTrue(menuDAO.addCategory("Vines") > 0);
//    }

//    @Test
//    void addDish_ReturnedIndxMoreThan0_true() throws DAOException {
//        Assertions.assertTrue(menuDAO.addDish(new BigDecimal("14.50"), "Kharcho", "Georgian national beef soup with rice, walnuts and tklapi or sour tkemali sauce.", 2) > 0);
//    }
}