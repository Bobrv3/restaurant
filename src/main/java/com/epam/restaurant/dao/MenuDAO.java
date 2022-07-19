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

    List<Category> findCategory(Criteria criteria) throws DAOException;

    int removeDish(Criteria criteria) throws DAOException;

    boolean editCategory(int editedCategoryId, String newCategoryName) throws DAOException;

    boolean editDish(int editedDishId, String newDishName, String description, BigDecimal price, String photoLink) throws DAOException;

    int addDish(BigDecimal price, String name, String description, int categoryForAdd, String photoLink) throws DAOException;

    int addCategory(String categoryName) throws DAOException;

    boolean removeCategory(int categoryId) throws DAOException;
}
