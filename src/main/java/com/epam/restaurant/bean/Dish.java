package com.epam.restaurant.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Dish implements Serializable {
    private static final long serialVersionUID = 8271867801723000721L;
    private int id;
    private BigDecimal price;
    private String name;
    private String description;
    private int category_id;
    private String photo_link;

    public Dish() {
    }

    public Dish(int id, BigDecimal price, String name, String description, int category_id, String photo_link) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.category_id = category_id;
        this.photo_link = photo_link;
    }

    public String getPhoto_link() {
        return photo_link;
    }

    public void setPhoto_link(String photo_link) {
        this.photo_link = photo_link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return id == dish.id && category_id == dish.category_id && Objects.equals(price, dish.price) && Objects.equals(name, dish.name) && Objects.equals(description, dish.description) && Objects.equals(photo_link, dish.photo_link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, name, description, category_id, photo_link);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category_id=" + category_id +
                ", photo_link='" + photo_link + '\'' +
                '}';
    }
}
