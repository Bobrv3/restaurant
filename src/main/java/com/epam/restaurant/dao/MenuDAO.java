package com.epam.restaurant.dao;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.bean.criteria.Criteria;

import java.math.BigDecimal;
import java.util.List;

public interface MenuDAO {

    Menu getMenu() throws DAOException;

    List<Category> getCategories() throws DAOException;

    List<Dish> find(Criteria criteria) throws DAOException;

    int remove(Criteria criteria) throws DAOException;

    boolean editCategory(int editedCategoryId, String newCategoryName) throws DAOException;

    boolean editDish(int editedDishId, String newDishName, String description, BigDecimal price) throws DAOException;

    int addDish(BigDecimal price, String name, String description, int categoryForAdd) throws DAOException;
}
