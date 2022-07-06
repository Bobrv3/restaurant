package com.epam.restaurant.service.validation;

import com.epam.restaurant.bean.RegistrationUserData;

public final class UserValidator {
    private UserValidator() {
    }

    public static void validateUserData(RegistrationUserData userData) throws ValidationException {
        if (userData == null) {
            throw new ValidationException("User is null");
        } else if (userData.getName() != null && !userData.getName().matches(ValidationType.NAME.getRegex())) {
            throw new ValidationException("The name must consist of the following characters:A-zА-я, max len 15ch");
        } else if (userData.getLogin() != null && !userData.getLogin().matches(ValidationType.LOGIN.getRegex())) {
            throw new ValidationException("The login must consist of the following characters:_A-z0-9, max len 15ch");
        } else if (userData.getPhoneNumber() != null && !userData.getPhoneNumber().matches(ValidationType.PHONE_NUMBER.getRegex())) {
            throw new ValidationException("PhoneNumber must consist of [0-9] dig, min len 10,  max len 14ch");
        } else if (userData.getEmail() != null && !userData.getEmail().matches(ValidationType.EMAIL.getRegex())) {
            throw new ValidationException("Not correct email");
        }
    }
}
