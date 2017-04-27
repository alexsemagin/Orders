package ru.rarus.restart.orders.data.retrofit.dto;

import ru.rarus.restart.orders.data.entities.User;

public class SignInRestResponse extends BaseResponse {

    private User user;

    public User getUser() {
        return user;
    }
}
