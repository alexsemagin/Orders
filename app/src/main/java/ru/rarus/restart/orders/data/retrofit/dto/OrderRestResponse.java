package ru.rarus.restart.orders.data.retrofit.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.rarus.restart.orders.data.entities.Order;



public class OrderRestResponse extends BaseResponse {

    @SerializedName("request")
    private List<Order> orders;


    public List<Order> getOrders() {
        return orders;
    }



}
