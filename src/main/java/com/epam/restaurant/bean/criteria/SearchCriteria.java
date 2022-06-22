package com.epam.restaurant.bean.criteria;

public class SearchCriteria {
    public static enum Users{
        LOGIN, NAME, PHONE_NUMBER, EMAIL
    }

    public static enum Dishes{
        DISHES_ID, PRICE, NAME, DESCRIPTION, STATUS, CATEGORY_ID
    }
}
