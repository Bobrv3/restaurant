package com.epam.restaurant.bean;

import java.util.Objects;

public class User {
    private int id;
    private int roleId;

    public User() {
    }

    public User(int id, int roleId) {
        this.id = id;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && roleId == user.roleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "id=" + id +
                ", roleId=" + roleId +
                '}';
    }
}
