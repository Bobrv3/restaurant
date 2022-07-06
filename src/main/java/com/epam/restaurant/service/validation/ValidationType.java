package com.epam.restaurant.service.validation;

public enum ValidationType {
    LOGIN("[\\w]{1,10}"),
    NAME("[A-zА-я]{1,15}"),
    PHONE_NUMBER("^\\+\\d{10,14}$"),
    EMAIL("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$"),
    ID("[\\d&&[^0]]+"),
    PRICE("[\\d\\.]+"),
    STATUS("[\\d]+");

    private final String regex;

    ValidationType(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
