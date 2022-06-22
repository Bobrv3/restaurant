package com.epam.restaurant.service;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.bean.criteria.Criteria;

import java.util.List;

public interface MenuService {
    Menu getMenu() throws ServiceException;
    List<Category> getCategories() throws ServiceException;
//    TODO List<Dish> find(Criteria criteria) throws ServiceException;
}
