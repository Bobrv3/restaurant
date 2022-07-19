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
import com.epam.restaurant.service.validation.CategoryValidator;
import com.epam.restaurant.service.validation.DishValidator;
import com.epam.restaurant.service.validation.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class MenuImpl implements MenuService {
    private static final DAOProvider daoProvider = DAOProvider.getInstance();
    private static final MenuDAO menuDAO = daoProvider.getMenuDAO();
    private static final Logger LOGGER = LogManager.getLogger(MenuImpl.class);

    @Override
    public Menu getMenu() throws ServiceException {
        try {
            return menuDAO.getMenu();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Category> getCategories() throws ServiceException {
        try {
            return menuDAO.getCategories();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Dish> find(Criteria criteria) throws ServiceException {
        DishValidator.validate(criteria);

        try {
            return menuDAO.find(criteria);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int removeDish(Criteria criteria) throws ServiceException {
        DishValidator.validate(criteria);

        try {
            return menuDAO.removeDish(criteria);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean editCategory(Integer editedCategoryId, String newCategoryName) throws ServiceException {
        CategoryValidator.validate(editedCategoryId, newCategoryName);

        try {
            return menuDAO.editCategory(editedCategoryId, newCategoryName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean editDish(Integer editedDishId, String newDishName, String description, BigDecimal price, String photoLink) throws ServiceException {
        try {
            DishValidator.validate(editedDishId, newDishName, description, price, photoLink);

            return menuDAO.editDish(editedDishId, newDishName, description, price, photoLink);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public int addDish(BigDecimal price, String name, String description, Integer categoryForAdd, String photoLink) throws ServiceException {
        try {
            DishValidator.validate(price, name, description, categoryForAdd, photoLink);
            int id = 0;

            daoProvider.getTransactionDAO().startTransaction();
            id = menuDAO.addDish(price, name, description, categoryForAdd, photoLink);
            daoProvider.getTransactionDAO().commit();

            return id;
        } catch (DAOException e) {
            try {
                daoProvider.getTransactionDAO().rollback();
                throw new ServiceException(e);
            } catch (DAOException ex) {
                LOGGER.error("error to rollback when adding dish to menu", ex);
                throw new ServiceException(ex);
            }
        }
    }

    @Override
    public int addCategory(String categoryName) throws ServiceException {
        CategoryValidator.validate(categoryName);

        try {
            return menuDAO.addCategory(categoryName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean removeCategory(Integer categoryId) throws ServiceException {
        CategoryValidator.validate(categoryId);

        try {
            return menuDAO.removeCategory(categoryId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
