package ru.rarus.restart.orders.data.retrofit.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.rarus.restart.orders.data.entities.MenuItem;

/**
 * Created by lysmik on 23.12.16.
 */

public class MenuItemRestResponse extends BaseResponse {
    @SerializedName("menu_items")
    private List<MenuItem> menuItems;

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
}
