package com.epam.restaurant.bean.criteria;

import java.util.HashMap;
import java.util.Map;

public class Criteria {
    private Map<String, Object> criteria = new HashMap<>();

    public Criteria() {
    }

    public void add(String searchCriteria, Object value) {
        criteria.put(searchCriteria, value);
    }

    public Map<String, Object> getCriteria() {
        return criteria;
    }
}