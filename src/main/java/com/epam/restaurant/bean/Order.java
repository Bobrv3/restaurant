package com.epam.restaurant.bean;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order {
    private long id = 0;
    private Map<Dish, Integer> orderList = new HashMap<>();
    private BigDecimal totalPrice;
    private Timestamp dateTime;
    private String methodOfReceiving;
    private String status;

    public Order() {

    }

    public Order(long id, Map<Dish, Integer> orderList, BigDecimal totalPrice, Timestamp dateTime, String methodOfReceiving, String status) {
        this.id = id;
        this.orderList = orderList;
        this.totalPrice = totalPrice;
        this.dateTime = dateTime;
        this.methodOfReceiving = methodOfReceiving;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMethodOfReceiving() {
        return methodOfReceiving;
    }

    public void setMethodOfReceiving(String methodOfReceiving) {
        this.methodOfReceiving = methodOfReceiving;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
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
        return id == order.id && Objects.equals(orderList, order.orderList) && Objects.equals(totalPrice, order.totalPrice) && Objects.equals(dateTime, order.dateTime) && Objects.equals(methodOfReceiving, order.methodOfReceiving) && Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderList, totalPrice, dateTime, methodOfReceiving, status);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "id=" + id +
                ", orderList=" + orderList +
                ", totalPrice=" + totalPrice +
                ", dateTime=" + dateTime +
                ", methodOfReceiving='" + methodOfReceiving + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
