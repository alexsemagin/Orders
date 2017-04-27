package ru.rarus.restart.orders.data.entities;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by lysmik on 19.12.16.
 */

@DatabaseTable(tableName = "Table")
public class Table {
    private static class Column {
        private  final static String TABLE_ID = "tableId";
        private final static String NAME = "name";
        private final static String AREA_ID = "areaId";
        private final static String SEATS = "seats";
        private final static String NUM = "num";
        private final static String IMAGE = "image";
        private final static String IMAGE_SELECTED = "imageSelected";
    }
    /**
     *  Столы.
     */
    @DatabaseField(id = true, dataType = DataType.STRING, columnName = Column.TABLE_ID)
    private String tableId;

    @SerializedName("name")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.NAME)
    private String name;

    @SerializedName("area_id")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.AREA_ID)
    private String areaId;

    @SerializedName("seats")
    @DatabaseField(dataType = DataType.INTEGER, columnName = Column.SEATS)
    private int seats;

    @SerializedName("num")
    @DatabaseField(dataType = DataType.INTEGER, columnName = Column.NUM)
    private int num;

    @SerializedName("image")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.IMAGE)
    private String image;

    @SerializedName("image_selected")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.IMAGE_SELECTED)
    private String imageSelected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageSelected() {
        return imageSelected;
    }

    public void setImageSelected(String imageSelected) {
        this.imageSelected = imageSelected;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
}
