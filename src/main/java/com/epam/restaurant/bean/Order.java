package com.epam.restaurant.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order {
    private static long id = 0;
    private Map<Dish, Integer> orderList = new HashMap<>();

    public Order() {

    }

    public Order(long id, Map<Dish, Integer> dishes) {
        this.id = id;
        this.orderList = dishes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<Dish, Integer> getOrderList() {
        return orderList;
    }

    public void setOrderList(Map<Dish, Integer> orderList) {
        this.orderList = orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(orderList, order.orderList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderList);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "id=" + id +
                ", dishes=" + orderList +
                '}';
    }
}
