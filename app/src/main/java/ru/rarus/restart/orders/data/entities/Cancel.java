package ru.rarus.restart.orders.data.entities;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by lysmik on 22.12.16.
 */
@DatabaseTable(tableName = "Cancel")
public class Cancel {
    public static class Column {
        public final static String ID = "id";
        public final static String PARENT_ID = "parentId";
        public final static String IS_GROUP  = "isGroup";
        public final static String NAME = "name";
        public final static String IS_PREPARED = "isPrepared";
    }
    /**
     * Причины отмен и списаний
     */
    @SerializedName("id")
    @DatabaseField(id = true, dataType = DataType.STRING, columnName = Column.ID)
    private String id;

    @SerializedName("parent_id")
    @DatabaseField( dataType = DataType.STRING, columnName = Column.PARENT_ID)
    private String parent_id;

    @SerializedName("isgroup")
    @DatabaseField( dataType = DataType.BOOLEAN, columnName = Column.IS_GROUP)
    private boolean isgroup;

    @SerializedName("name")
    @DatabaseField( dataType = DataType.STRING, columnName = Column.NAME)
    private String name;

    @SerializedName("is_prepared")
    @DatabaseField( dataType = DataType.BOOLEAN, columnName = Column.IS_PREPARED)
    private boolean is_prepared;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public boolean isgroup() {
        return isgroup;
    }

    public void setIsgroup(boolean isgroup) {
        this.isgroup = isgroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean is_prepared() {
        return is_prepared;
    }

    public void setIs_prepared(boolean is_prepared) {
        this.is_prepared = is_prepared;
    }
}
