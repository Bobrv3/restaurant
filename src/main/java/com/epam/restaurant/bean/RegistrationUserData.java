package com.epam.restaurant.bean;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class RegistrationUserData implements Serializable {
    private static final long serialVersionUID = -3734494546116300742L;
    private String login;
    private transient char[] password;
    private String name;
    private String phoneNumber;
    private String email;
    private int roleId;

    public RegistrationUserData() {
    }

    public RegistrationUserData(String login, char[] password, String name, String phoneNumber, String email, int roleId) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roleId = roleId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        RegistrationUserData that = (RegistrationUserData) o;
        return Objects.equals(login, that.login) && Objects.equals(password, that.password) && Objects.equals(name, that.name) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(email, that.email) && Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, name, phoneNumber, email, roleId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
