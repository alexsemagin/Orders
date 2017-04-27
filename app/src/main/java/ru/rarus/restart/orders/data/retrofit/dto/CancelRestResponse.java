package ru.rarus.restart.orders.data.retrofit.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.rarus.restart.orders.data.entities.Cancel;


public class CancelRestResponse extends BaseResponse {

    @SerializedName("cancel")
    private List<Cancel> cancel;

    public List<Cancel> getCancel() {
        return cancel;
    }

    public void setCancel(List<Cancel> cancel) {
        this.cancel = cancel;
    }
}
