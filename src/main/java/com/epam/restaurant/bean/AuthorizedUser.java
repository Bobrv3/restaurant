package com.epam.restaurant.bean;

import java.io.Serializable;
import java.util.Objects;

public class AuthorizedUser implements Serializable {
    private static final long serialVersionUID = 3350492538842423262L;
    private int id;
    private String name;
    private int roleId;

    public AuthorizedUser() {
    }

    public AuthorizedUser(int id, String name, int roleId) {
        this.id = id;
        this.name = name;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        AuthorizedUser that = (AuthorizedUser) o;
        return id == that.id && roleId == that.roleId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, roleId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
