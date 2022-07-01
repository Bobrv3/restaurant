package com.epam.restaurant.service.impl;

import com.epam.restaurant.bean.Category;
import com.epam.restaurant.bean.Dish;
import com.epam.restaurant.bean.Menu;
import com.epam.restaurant.bean.criteria.Criteria;
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

    @Override
    public List<Dish> find(Criteria criteria) throws ServiceException {
        MenuDAO menuDAO = daoProvider.getMenuDAO();

        List<Dish> dishes = null;
        try {
            dishes = menuDAO.find(criteria);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return dishes;
    }

    @Override
    public int remove(Criteria criteria) throws ServiceException {
        MenuDAO menuDAO = daoProvider.getMenuDAO();

        try {
            return menuDAO.remove(criteria);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean editCategory(int editedCategoryId, String newCategoryName) throws ServiceException {
        MenuDAO menuDAO = daoProvider.getMenuDAO();

        try {
            return menuDAO.editCategory(editedCategoryId, newCategoryName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
