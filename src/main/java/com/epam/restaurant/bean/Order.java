package com.epam.restaurant.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order {
    private int id;
    private Map<Dish, Integer> dishes = new HashMap<>();

    public Order() {
    }

    public Order(int id, Map<Dish, Integer> dishes) {
        this.id = id;
        this.dishes = dishes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Dish, Integer> getDishes() {
        return dishes;
    }

    public void setDishes(Map<Dish, Integer> dishes) {
        this.dishes = dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(dishes, order.dishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dishes);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "id=" + id +
                ", dishes=" + dishes +
                '}';
    }
}
