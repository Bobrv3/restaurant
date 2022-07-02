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

import java.math.BigDecimal;
import java.util.List;

public class MenuImpl implements MenuService {
    private static final DAOProvider daoProvider = DAOProvider.getInstance();
    private static final MenuDAO menuDAO = daoProvider.getMenuDAO();

    @Override
    public Menu getMenu() throws ServiceException {
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
        try {
            return menuDAO.remove(criteria);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean editCategory(int editedCategoryId, String newCategoryName) throws ServiceException {
        try {
            return menuDAO.editCategory(editedCategoryId, newCategoryName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean editDish(int editedDishId, String newDishName, String description, BigDecimal price) throws ServiceException {
        try {
            return menuDAO.editDish(editedDishId, newDishName, description, price);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public int addDish(BigDecimal price, String name, String description, int categoryForAdd) throws ServiceException {
        try {
            return menuDAO.addDish(price, name, description, categoryForAdd);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
