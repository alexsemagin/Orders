package ru.rarus.restart.orders.data.entities;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by lysmik on 19.12.16.
 */

@DatabaseTable(tableName = "DiscCard")
public class DiscCard {

    private static class Column {
        private final static String DISC_CARD_ID = "discCardId";
        private final static String PARENT_ID = "parentId";
        private final static String IS_GROUP = "isGroup";
        private final static String CODE = "code";
        private final static String NAME = "name";
        private final static String BLOCKED = "blocked";
        private final static String BLOCK_REASON = "blockReason";
        private final static String COMMENT = "comment";
        private final static String IMAGE = "image";
        private final static String GUEST_ID = "guestId";
    }
    /**
     * Дисконтные карты. Реквезиты объекта.
     */
    @DatabaseField(id = true, dataType = DataType.STRING, columnName = Column.DISC_CARD_ID)
    private String discCardId;

    @SerializedName("parent_id")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.PARENT_ID)
    private String parentId;

    @SerializedName("isgroup")
    @DatabaseField(dataType = DataType.BOOLEAN, columnName = Column.IS_GROUP)
    private boolean isGroup;

    @SerializedName("code")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.CODE)
    private String code;

    @SerializedName("name")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.NAME)
    private String name;

    @SerializedName("blocked")
    @DatabaseField(dataType = DataType.BOOLEAN, columnName = Column.BLOCKED)
    private boolean blocked;

    @SerializedName("block_reason")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.BLOCK_REASON)
    private String blockReason;

    @SerializedName("comment")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.COMMENT)
    private String comment;

    @SerializedName("image")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.IMAGE)
    private String image;

    @SerializedName("guest_id")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.GUEST_ID)
    private String guestId;

    public DiscCard() {
    }

    public String getDiscCardId() {
        return discCardId;
    }

    public void setDiscCardId(String discCardId) {
        this.discCardId = discCardId;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

}
