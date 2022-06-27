package com.epam.restaurant.bean;

import java.io.Serializable;
import java.util.Objects;

public class AuthorizedUser implements Serializable {
    private static final long serialVersionUID = 3350492538842423262L;
    private String login;
    private String name;
    private int roleId;

    public AuthorizedUser() {
    }

    public AuthorizedUser(String login, String name, int roleId) {
        this.login = login;
        this.name = name;
        this.roleId = roleId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
        return roleId == that.roleId && Objects.equals(login, that.login) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, name, roleId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
