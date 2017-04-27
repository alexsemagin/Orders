package ru.rarus.restart.orders.data.entities;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by lysmik on 19.12.16.
 */

@DatabaseTable(tableName = "Hall")
public class Hall {

    private static class Column {
        private final static String HALL_ID = "hallId";
        private final static String NAME = "name";
        private final static String IMAGE = "image";
        private final static String POSITION = "position";
    }
    /**
     * Залы.
     */
    @DatabaseField(id = true, dataType = DataType.STRING, columnName = Column.HALL_ID)
    private String hallId;

    @SerializedName("name")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.NAME)
    private String name;

    @SerializedName("image")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.IMAGE)
    private String image;

    @SerializedName("position")
    @DatabaseField(dataType = DataType.INTEGER, columnName = Column.POSITION)
    private int position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getHallId() {
        return hallId;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }
}
