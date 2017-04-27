package ru.rarus.restart.orders.data.settings.enums;

/**
 * Created by lysmik on 21.10.16.
 */

public enum DayType {
    ONE_DAY(0, "1 день"),
    TWO_DAYS(1, "2 дня"),
    THREE_DAYS(2, "3 дня"),
    FOUR_DAYS(3, "4 дня"),
    FIVE_DAYS(4, "5 дней"),
    SIX_DAYS(5, "6 дней"),
    SEVEN_DAYS(6, "7 дней");

    private final int id;
    private final String name;

    DayType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static DayType fromId(int id) {
        switch (id) {
            case 0: {
                return ONE_DAY;
            }
            case 1: {
                return TWO_DAYS;
            }
            case 2: {
                return THREE_DAYS;
            }
            case 3: {
                return FOUR_DAYS;
            }
            case 4: {
                return FIVE_DAYS;
            }
            case 5: {
                return SIX_DAYS;
            }
            case 6: {
                return SEVEN_DAYS;
            }
            default:
                return ONE_DAY;
        }
    }
}
