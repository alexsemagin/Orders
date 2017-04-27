package ru.rarus.restart.orders.data.retrofit.dto;

import com.google.gson.annotations.SerializedName;



public class BaseResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("code")
    protected int code;

    @SerializedName("error")
    protected String message;



    public BaseResponse() {
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }


}
