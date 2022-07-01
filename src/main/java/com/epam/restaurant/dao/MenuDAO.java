package com.epam.restaurant.dao;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.bean.criteria.Criteria;

import java.util.List;

public interface MenuDAO {

    Menu getMenu() throws DAOException;

    List<Category> getCategories() throws DAOException;

    List<Dish> find(Criteria criteria) throws DAOException;

    int remove(Criteria criteria) throws DAOException;
}
