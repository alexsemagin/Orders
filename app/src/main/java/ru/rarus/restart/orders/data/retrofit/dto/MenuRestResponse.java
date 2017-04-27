package ru.rarus.restart.orders.data.retrofit.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.rarus.restart.orders.data.entities.Menu;

/**
 * Created by alanis on 23.12.16.
 */

public class MenuRestResponse extends BaseResponse {

    @SerializedName("menu")
    private List<Menu> menus;

    public List<Menu> getMenus() {
        return menus;
    }

}
