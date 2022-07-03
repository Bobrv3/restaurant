package com.epam.restaurant.bean;

import java.util.Objects;

public class OrderForCooking {
    private long orderId;
    private String dishName;
    private String methodOfReceiving;

    public OrderForCooking() {
    }

    public OrderForCooking(long orderId, String dishName, String methodOfReceiving) {
        this.orderId = orderId;
        this.dishName = dishName;
        this.methodOfReceiving = methodOfReceiving;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getMethodOfReceiving() {
        return methodOfReceiving;
    }

    public void setMethodOfReceiving(String methodOfReceiving) {
        this.methodOfReceiving = methodOfReceiving;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderForCooking that = (OrderForCooking) o;
        return orderId == that.orderId && Objects.equals(dishName, that.dishName) && Objects.equals(methodOfReceiving, that.methodOfReceiving);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, dishName, methodOfReceiving);
    }

    @Override
    public String toString() {
        return "OrderForCooking{" +
                "orderId=" + orderId +
                ", dishName='" + dishName + '\'' +
                ", methodOfReceiving='" + methodOfReceiving + '\'' +
                '}';
    }
}
