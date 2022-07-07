package com.epam.restaurant.service.validation;

public final class CategoryValidator {
    private CategoryValidator() {}

    public static void validate(Integer editedCategoryId, String newCategoryName) throws ValidationException {
        if (editedCategoryId == null) {
            throw new ValidationException("editedCategoryId is null");
        } else if (!editedCategoryId.toString().matches(ValidationType.ID.getRegex())) {
            throw new ValidationException(ValidationType.ID.getErrorMsg());
        } else if (newCategoryName == null) {
            throw new ValidationException("newCategoryName is null");
        } else if (!newCategoryName.matches(ValidationType.NAME.getRegex())) {
            throw new ValidationException(ValidationType.NAME.getErrorMsg());
        }
    }

    public static void validate(String newCategoryName) throws ValidationException {
        if (newCategoryName == null) {
            throw new ValidationException("newCategoryName is null");
        } else if (!newCategoryName.matches(ValidationType.NAME.getRegex())) {
            throw new ValidationException(ValidationType.NAME.getErrorMsg());
        }
    }

    public static void validate(Integer editedCategoryId) throws ValidationException {
        if (editedCategoryId == null) {
            throw new ValidationException("editedCategoryId is null");
        } else if (!editedCategoryId.toString().matches(ValidationType.ID.getRegex())) {
            throw new ValidationException(ValidationType.ID.getErrorMsg());
        }
    }
}
