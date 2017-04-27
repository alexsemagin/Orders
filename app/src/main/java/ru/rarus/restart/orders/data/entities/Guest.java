package ru.rarus.restart.orders.data.entities;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by lysmik on 19.12.16.
 */

@DatabaseTable(tableName = "Guest")
public class Guest {

    private static class Column {
        private final static String GUEST_ID = "guestId";
        private final static String PARENT_ID = "parentId";
        private final static String IS_GROUP = "isGroup";
        private final static String NAME = "name";
        private final static String FIRSTNAME = "firstName";
        private final static String PATRONYMIC = "patronymic";
        private final static String LASTNAME = "lastName";
        private final static String DATE_BIRTH = "dateBirth";
        private final static String DELIVERY_ADDRESS = "deliveryAddress";
        private final static String ACTUAL_ADDRESS = "actualAddress";
        private final static String DELIVERY_METRO = "deliveryMetro";
        private final static String DELIVERY_STREET = "deliveryStreet";
        private final static String DELIVERY_DISTR = "deliveryDistr";
        private final static String PHONE = "phone";
        private final static String PHONE2 = "phone2";
        private final static String EMAIL = "email";
        private final static String BLACKLISTED = "blackListed";
        private final static String BLACK_REASON = "blackReason";
    }
    /**
     * Гости. Реквезиты объекта.
     */
    @DatabaseField(id = true, dataType = DataType.STRING, columnName = Column.GUEST_ID)
    private String guestId;

    @SerializedName("parent_id")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.PARENT_ID)
    private String parentId;

    @SerializedName("isgroup")
    @DatabaseField(dataType = DataType.BOOLEAN, columnName = Column.IS_GROUP)
    private boolean isGroup;

    @SerializedName("name")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.NAME)
    private String name;

    @SerializedName("first_name")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.FIRSTNAME)
    private String firstName;

    @SerializedName("patronymic")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.PATRONYMIC)
    private String patronymic;

    @SerializedName("last_name")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.LASTNAME)
    private String lastName;

    @SerializedName("date_birth")
    @DatabaseField(dataType = DataType.DATE, columnName = Column.DATE_BIRTH)
    private Date dateBirth;

    @SerializedName("delivery_address")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DELIVERY_ADDRESS)
    private String deliveryAddress;

    @SerializedName("actual_address")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.ACTUAL_ADDRESS)
    private String actualAddress;

    @SerializedName("delivery_metro")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DELIVERY_METRO)
    private String deliveryMetro;

    @SerializedName("delivery_street")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DELIVERY_STREET)
    private String deliveryStreet;

    @SerializedName("delivery_distr")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DELIVERY_DISTR)
    private String deliveryDistr;

    @SerializedName("phone1")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.PHONE)
    private String phone;

    @SerializedName("phone2")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.PHONE2)
    private String phone2;

    @SerializedName("email")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.EMAIL)
    private String email;

    @SerializedName("black_listed")
    @DatabaseField(dataType = DataType.BOOLEAN, columnName = Column.BLACKLISTED)
    private boolean blackListed;

    @SerializedName("black_reason")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.BLACK_REASON)
    private String blackReason;

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getActualAddress() {
        return actualAddress;
    }

    public void setActualAddress(String actualAddress) {
        this.actualAddress = actualAddress;
    }

    public String getDeliveryMetro() {
        return deliveryMetro;
    }

    public void setDeliveryMetro(String deliveryMetro) {
        this.deliveryMetro = deliveryMetro;
    }

    public String getDeliveryStreet() {
        return deliveryStreet;
    }

    public void setDeliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
    }

    public String getDeliveryDistr() {
        return deliveryDistr;
    }

    public void setDeliveryDistr(String deliveryDistr) {
        this.deliveryDistr = deliveryDistr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBlackListed() {
        return blackListed;
    }

    public void setBlackListed(boolean blackListed) {
        this.blackListed = blackListed;
    }

    public String getBlackReason() {
        return blackReason;
    }

    public void setBlackReason(String blackReason) {
        this.blackReason = blackReason;
    }
}
