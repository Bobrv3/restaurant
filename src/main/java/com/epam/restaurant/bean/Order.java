package com.epam.restaurant.bean;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order {
    private long id = 0;
    private Map<Dish, Integer> orderList = new HashMap<>();
    private BigDecimal totalPrice;
    private Date date;

    public Order() {

    }

    public Order(long id, Map<Dish, Integer> dishes) {
        this.id = id;
        this.orderList = dishes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
