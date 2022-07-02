package com.epam.restaurant.service;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.bean.criteria.Criteria;

import java.math.BigDecimal;
import java.util.List;

public interface MenuService {

    Menu getMenu() throws ServiceException;

    List<Category> getCategories() throws ServiceException;

    List<Dish> find(Criteria criteria) throws ServiceException;

    int remove(Criteria criteria) throws ServiceException;

    boolean editCategory(int editedCategoryId, String newCategoryName) throws ServiceException;

    boolean editDish(int editedDishId, String newDishName, String description, BigDecimal price) throws ServiceException;
}
