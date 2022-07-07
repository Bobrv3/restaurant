package com.epam.restaurant.service.validation;

import com.epam.restaurant.bean.RegistrationUserData;

public final class UserValidator {
    private UserValidator() {
    }

    public static void validateUserData(RegistrationUserData userData) throws ValidationException {
        if (userData == null) {
            throw new ValidationException("User is null");
        } else if (userData.getName() != null && !userData.getName().matches(ValidationType.NAME.getRegex())) {
            throw new ValidationException(ValidationType.NAME.getErrorMsg());
        } else if (userData.getLogin() != null && !userData.getLogin().matches(ValidationType.LOGIN.getRegex())) {
            throw new ValidationException(ValidationType.LOGIN.getErrorMsg());
        } else if (userData.getPhoneNumber() != null && !userData.getPhoneNumber().matches(ValidationType.PHONE_NUMBER.getRegex())) {
            throw new ValidationException(ValidationType.PHONE_NUMBER.getErrorMsg());
        } else if (userData.getEmail() != null && !userData.getEmail().matches(ValidationType.EMAIL.getRegex())) {
            throw new ValidationException(ValidationType.EMAIL.getErrorMsg());
        }
    }
}
