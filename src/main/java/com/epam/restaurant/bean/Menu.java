package com.epam.restaurant.bean;

import java.util.List;
import java.util.Objects;

public class Menu {
    private List<Dish> dishes;

    public Menu() {
    }

    public Menu(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public void add(Dish dish) {
        dishes.add(dish);
    }

    public void remove(Dish dish) {
        dishes.remove(dish);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(dishes, menu.dishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishes);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "dishes=" + dishes +
                '}';
    }
}
