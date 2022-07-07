package com.epam.restaurant.service.validation;

import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.criteria.SearchCriteria;

import java.math.BigDecimal;
import java.util.Map;

public class DishValidator {

    public static void validate(BigDecimal price, String name, String description, Integer categoryForAdd, String photoLink) throws ValidationException {
        if (price == null || !price.toString().matches(ValidationType.PRICE.getRegex())) {
            throw new ValidationException(ValidationType.PRICE.getErrorMsg());
        } else if (name == null || !name.matches(ValidationType.NAME.getRegex())) {
            throw new ValidationException(ValidationType.NAME.getErrorMsg());
        } else if (description == null) {
            throw new ValidationException("description shouldn't be null");
        } else if (categoryForAdd == null || !categoryForAdd.toString().matches(ValidationType.ID.getRegex())) {
            throw new ValidationException(ValidationType.ID.getErrorMsg());
        } else if (photoLink == null) {
            throw new ValidationException("photoLink shouldn't be null");
        }
    }

    public static void validate(Criteria criteria) throws ValidationException {
        Map<String, Object> criterias = criteria.getCriteria();
        for (String key : criterias.keySet()) {
            if (key.equals(SearchCriteria.Dishes.DISHES_ID)) {
                if (criterias.get(key) == null || !criterias.get(key).toString().matches(ValidationType.ID.getRegex())) {
                    throw new ValidationException(ValidationType.ID.getErrorMsg());
                }
            } else if (key.equals(SearchCriteria.Dishes.NAME)) {
                if (criterias.get(key) == null || !criterias.get(key).toString().matches(ValidationType.NAME.getRegex())) {
                    throw new ValidationException(ValidationType.NAME.getErrorMsg());
                }
            } else if (key.equals(SearchCriteria.Dishes.PRICE)) {
                if (criterias.get(key) == null || !criterias.get(key).toString().matches(ValidationType.PRICE.getRegex())) {
                    throw new ValidationException(ValidationType.PRICE.getErrorMsg());
                }
            } else if (key.equals(SearchCriteria.Dishes.STATUS)) {
                if (criterias.get(key) == null || !criterias.get(key).toString().matches(ValidationType.STATUS.getRegex())) {
                    throw new ValidationException(ValidationType.STATUS.getErrorMsg());
                }
            } else if (key.equals(SearchCriteria.Dishes.CATEGORY_ID)) {
                if (criterias.get(key) == null || !criterias.get(key).toString().matches(ValidationType.ID.getRegex())) {
                    throw new ValidationException(ValidationType.ID.getErrorMsg());
                }
            } else if (key.equals(SearchCriteria.Dishes.DESCRIPTION)) {
                if (criterias.get(key) == null) {
                    throw new ValidationException("description shouldn't be null");
                }
            }
        }
    }

    public static void validate(Integer editedDishId, String newDishName, String description, BigDecimal price, String photoLink) throws ValidationException {
        if (price == null || !price.toString().matches(ValidationType.PRICE.getRegex())) {
            throw new ValidationException(ValidationType.PRICE.getErrorMsg());
        } else if (newDishName == null || !newDishName.matches(ValidationType.NAME.getRegex())) {
            throw new ValidationException(ValidationType.NAME.getErrorMsg());
        } else if (description == null) {
            throw new ValidationException("description shouldn't be null");
        } else if (editedDishId == null || !editedDishId.toString().matches(ValidationType.ID.getRegex())) {
            throw new ValidationException(ValidationType.ID.getErrorMsg());
        } else if (photoLink == null) {
            throw new ValidationException("photoLink shouldn't be null");
        }
    }

}
