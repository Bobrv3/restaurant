package com.epam.restaurant.service.impl;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.DAOProvider;
import com.epam.restaurant.dao.MenuDAO;
import com.epam.restaurant.service.MenuService;
import com.epam.restaurant.service.ServiceException;

import java.util.List;

public class MenuImpl implements MenuService {
    private static final DAOProvider daoProvider = DAOProvider.getInstance();

    @Override
    public Menu getMenu() throws ServiceException {
        MenuDAO menuDAO = daoProvider.getMenuDAO();

        Menu menu = null;
        try {
            menu = menuDAO.getMenu();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return menu;
    }

    @Override
    public List<Category> getCategories() throws ServiceException {
        MenuDAO menuDAO = daoProvider.getMenuDAO();

        List<Category> categories = null;
        try {
            categories = menuDAO.getCategories();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return categories;
    }
}
