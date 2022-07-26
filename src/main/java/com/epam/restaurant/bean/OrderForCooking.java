package com.epam.restaurant.bean;

import java.io.Serializable;
import java.util.Objects;

public class OrderForCooking implements Serializable {
    private static final long serialVersionUID = 2899503221119242930L;
    private long orderId;
    private String dishName;
    private int quantity;
    private String methodOfReceiving;

    public OrderForCooking() {
    }

    public OrderForCooking(long orderId, String dishName, int quantity, String methodOfReceiving) {
        this.orderId = orderId;
        this.dishName = dishName;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
        OrderForCooking order = (OrderForCooking) o;
        return orderId == order.orderId && quantity == order.quantity && Objects.equals(dishName, order.dishName) && Objects.equals(methodOfReceiving, order.methodOfReceiving);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, dishName, quantity, methodOfReceiving);
    }

    @Override
    public String toString() {
        return "OrderForCooking{" +
                "orderId=" + orderId +
                ", dishName='" + dishName + '\'' +
                ", quantity=" + quantity +
                ", methodOfReceiving='" + methodOfReceiving + '\'' +
                '}';
    }
}
