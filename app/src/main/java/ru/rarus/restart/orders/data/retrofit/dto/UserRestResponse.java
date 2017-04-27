package ru.rarus.restart.orders.data.retrofit.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.rarus.restart.orders.data.entities.User;


public class UserRestResponse extends BaseResponse {

    @SerializedName("user")
    private List<User> user;

    public User getUser() {
        return user.get(0);
    }
}
