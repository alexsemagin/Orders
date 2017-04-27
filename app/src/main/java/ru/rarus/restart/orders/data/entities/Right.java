package ru.rarus.restart.orders.data.entities;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by lysmik on 13.01.17.
 */
@DatabaseTable(tableName = "Right")
public class Right {
    public static class Column {
        public final static String ID = "id";
        public final static String NAME = "name";
        public final static String DESCRIPTION  = "description";
        public final static String USER_ID = "userId";
    }
    /**
     * Информация о Правах.
     */
    @SerializedName("id")
    @DatabaseField(id = true, dataType = DataType.STRING, columnName = Column.ID)
    private String id;

    @SerializedName("name")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.NAME)
    private String name;

    @SerializedName("description")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DESCRIPTION)
    private String description;

    @DatabaseField(dataType = DataType.STRING, columnName = Column.USER_ID)
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
