package com.epam.restaurant.bean.criteria;

public final class SearchCriteria {
    private SearchCriteria() {
    }

    public static enum Users{
        LOGIN, NAME, PHONE_NUMBER, EMAIL, ID
    }

    public static enum Dishes{
        DISHES_ID, PRICE, NAME, DESCRIPTION, STATUS, CATEGORY_ID, URL
    }

    public static enum Categories{
        ID, NAME, STATUS
    }

    public static enum Orders{
        ID, ORDER_STATUS, USER_ID
    }
}
