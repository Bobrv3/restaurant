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

    int removeDish(Criteria criteria) throws ServiceException;

    boolean editCategory(Integer editedCategoryId, String newCategoryName) throws ServiceException;

    boolean editDish(Integer editedDishId, String newDishName, String description, BigDecimal price, String photo_link) throws ServiceException;

    int addDish(BigDecimal price, String name, String description, Integer categoryForAdd, String photo_link) throws ServiceException;

    int addCategory(String categoryName) throws ServiceException;

    boolean removeCategory(Integer categoryId) throws ServiceException;
}
