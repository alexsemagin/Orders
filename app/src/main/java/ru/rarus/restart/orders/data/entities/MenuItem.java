package ru.rarus.restart.orders.data.entities;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by lysmik on 22.12.16.
 */
@DatabaseTable(tableName = "MenuItem")
public class MenuItem {
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
    }
    /**
     * Состав меню. Реквезиты табличной части.
     */
    @SerializedName("id")
    @DatabaseField(id = true, dataType = DataType.STRING, columnName = Column.ID)
    private String id;

    @SerializedName("stopped")
    @DatabaseField(dataType = DataType.BOOLEAN, columnName = Column.STOPPED)
    private boolean stopped;

    @SerializedName("parent_id")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.PARENT_ID)
    private String parentId;

    @SerializedName("isgroup")
    @DatabaseField(dataType = DataType.BOOLEAN, columnName = Column.IS_GROUP)
    private boolean isGroup;

    @SerializedName("name")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.NAME)
    private String name;

    @SerializedName("position")
    @DatabaseField(dataType = DataType.INTEGER, columnName = Column.POSITION)
    private int position;

    @SerializedName("image")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.IMAGE)
    private String image;

    @SerializedName("price")
    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.PRICE)
    private double price;

    @SerializedName("comment")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.COMMENT)
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
