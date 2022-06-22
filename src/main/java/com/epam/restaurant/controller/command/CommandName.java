package com.epam.restaurant.controller.command;

import com.epam.restaurant.controller.command.impl.ChangeLocaleToEng;

public enum CommandName {
    MOVE_TO_AUTHORIZATION_PAGE,
    MOVE_TO_REGISTRATION_PAGE,
    NONAME_COMMAND,
    AUTHORIZATION,
    REGISTRATION,
    CHANGE_LOCALE_TO_ENG,
    CHANGE_LOCALE_TO_RU,
    GET_MENU,
    GET_CATEGORIES
}
