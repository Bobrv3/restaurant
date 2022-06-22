package com.epam.restaurant.bean;

import java.math.BigDecimal;
import java.util.Objects;

public class Dish {
    private int dishes_id;
    private BigDecimal price;
    private String name;
    private String description;
    private int category_id;

    public Dish() {
    }

    public Dish(int dishes_id, BigDecimal price, String name, String description, int category_id) {
        this.dishes_id = dishes_id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.category_id = category_id;
    }

    public int getDishes_id() {
        return dishes_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory_id() {
        return category_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish menu = (Dish) o;
        return dishes_id == menu.dishes_id && category_id == menu.category_id && Objects.equals(price, menu.price) && Objects.equals(name, menu.name) && Objects.equals(description, menu.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishes_id, price, name, description, category_id);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "dishes_id=" + dishes_id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category_id=" + category_id +
                '}';
    }
}