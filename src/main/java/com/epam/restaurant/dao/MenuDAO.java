package com.epam.restaurant.dao;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.bean.Menu;

import java.util.List;

public interface MenuDAO {
    Menu getMenu() throws DAOException;
    List<Category> getCategories() throws DAOException;
    // TODO find
}
