package ru.rarus.restart.orders.data.settings.enums;

/**
 * Created by lysmik on 21.10.16.
 */

public enum ScanType {
    AUTO(0,"Автоматически"),
    HAND(1, "Вручную");

    private final int id;
    private final String name;

    ScanType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() { return  name; }

    public int getId() {
        return id;
    }

    public static ScanType fromId(int id) {
        if (id == 0) {
            return AUTO;
        } else {
            return HAND;
        }
    }
}
