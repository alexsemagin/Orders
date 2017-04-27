package ru.rarus.restart.orders.data.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class User {
    /**
     * Информация о Пользователе.
     */
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("right")
    private List<Right> right;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Right> getRights() {
        return right;
    }

    public void setRights(List<Right> rights) {
        this.right = rights;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", right=" + right +
                '}';
    }
}
