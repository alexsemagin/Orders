package ru.rarus.restart.orders.data.entities;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by lysmik on 19.12.16.
 */

@DatabaseTable(tableName = "Menu")
public class Menu {

    private static class Column {
        private final static String ID = "id";
        private final static String STOPPED = "stopped";
        private final static String PARENT_ID = "parentId";
        private final static String IS_GROUP = "isGroup";
        private final static String NAME = "name";
        private final static String POSITION = "position";
        private final static String IMAGE = "image";
        private final static String PRICE = "price";
        private final static String COMMENT = "comment";

        private final static String NAME_MENU = "nameMenu";
        private final static String DATE_BEGIN = "dateBegin";
        private final static String TIME_BEGIN = "timeBegin";
        private final static String DATE_END = "dateEnd";
        private final static String TIME_END = "timeEnd";
        private final static String WEEK = "week";
        private final static String ARM = "arm";
    }
    /**
     * Виды меню. Реквезиты объекта.
     */
    @SerializedName("id")
    @DatabaseField(id = true, dataType = DataType.STRING, columnName = Column.ID)
    private String id;

    @SerializedName("name")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.NAME_MENU)
    private String nameMenu;

    @SerializedName("date_begin")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DATE_BEGIN)
    private String dateBegin;

    @SerializedName("time_begin")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.TIME_BEGIN)
    private String timeBegin;

    @SerializedName("date_end")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DATE_END)
    private String dateEnd;

    @SerializedName("time_end")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.TIME_END)
    private String timeEnd;

    @SerializedName("week")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.WEEK)
    private String week;

    @SerializedName("arm")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.ARM)
    private String arm;

    public String getNameMenu() {
        return nameMenu;
    }

    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(String timeBegin) {
        this.timeBegin = timeBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getArm() {
        return arm;
    }

    public void setArm(String arm) {
        this.arm = arm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
