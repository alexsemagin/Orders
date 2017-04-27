package ru.rarus.restart.orders.data.settings.enums;

/**
 * Created by lysmik on 21.10.16.
 */

public enum MinutesPeriodType {

    ONE_MIN(0, "1 мин"),
    TWO_MIN(1,"2 мин"),
    FIVE_MIN(2,"5 мин"),
    EIGHT_MIN(3,"8 мин"),
    TEN_MIN(4,"10 мин"),
    FIFTH_MIN(5,"15 мин");

    private final int id;
    private final String name;

    MinutesPeriodType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static MinutesPeriodType fromId(int id) {
        switch (id) {
            case 0: {
                return ONE_MIN;
            }
            case 1: {
                return TWO_MIN;
            }
            case 2: {
                return FIVE_MIN;
            }
            case 3: {
                return EIGHT_MIN;
            }
            case 4: {
                return TEN_MIN;
            }
            case 5: {
                return FIFTH_MIN;
            }
            default: {
                return ONE_MIN;
            }
        }
    }
}
