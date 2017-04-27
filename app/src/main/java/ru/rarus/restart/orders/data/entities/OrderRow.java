package ru.rarus.restart.orders.data.entities;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;


@DatabaseTable(tableName = "OrderRow")
public class OrderRow {

    public static class Column {
        public final static String ID = "id";
        public final static String PARENT_ID = "parentId";
        public final static String DATE_ADD = "dateAdd";
        public final static String DATE_DEL = "dateDel";
        public final static String USER_DEL_NAME = "userDelName";
        public final static String CANCEL_DESCRIPTION = "cancelDescription";
        public final static String PRODUCT_ID = "productId";
        public final static String MODIFIER_ID = "modifierId";
        public final static String MENU_ID = "menuId";
        public final static String PRICE = "price";
        public final static String COUNT = "count";
        public final static String SUM = "sum";
        public final static String DISCOUNT_PERCENT = "discountPercent";
        public final static String DISCOUNT_SUM = "discountSum";
        public final static String TOTAL_DISCOUNT_PERCENT = "totalDiscountPercent";
        public final static String TOTAL_DISCOUNT_SUM = "totalDiscountSum";
        public final static String TOTAL_SUM = "totalSum";
        public final static String STATUS = "status";
        public final static String COMMENT = "comment";
        public final static String ORDER_ID = "orderId";
        public final static String NAME = "name";
        public final static String SHORT_NAME = "shortName";
    }
    /**
     * Заказы.Строки заказа.
     */
    @SerializedName("id")
    @DatabaseField(id = true, dataType = DataType.STRING, columnName = Column.ID)
    private String id;

    @SerializedName("parent_id")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.PARENT_ID)
    private String parentId;

    //@SerializedName("date_add")
    @DatabaseField(dataType = DataType.DATE, columnName = Column.DATE_ADD)
    private Date dateAdd;
    private String date_add;

    //@SerializedName("date_del")
    @DatabaseField(dataType = DataType.DATE, columnName = Column.DATE_DEL)
    private Date dateDel;
    private String date_del;

    @SerializedName("user_del_name")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.USER_DEL_NAME)
    private String userDelName;

    @SerializedName("cancel_descriprion")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.CANCEL_DESCRIPTION)
    private String cancelDescription;

    @SerializedName("product_id")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.PRODUCT_ID)
    private String productId;

    @SerializedName("modifier_id")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.MODIFIER_ID)
    private String modifierId;

    @SerializedName("menu_id")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.MENU_ID)
    private String menuId;

    @SerializedName("price")
    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.PRICE)
    private double price;

    @SerializedName("count")
    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.COUNT)
    private double count;

    @SerializedName("sum")
    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.SUM)
    private double sum;

    @SerializedName("discount_percent")
    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.DISCOUNT_PERCENT)
    private double discountPercent;

    @SerializedName("discount_sum")
    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.DISCOUNT_SUM)
    private double discountSum;

    @SerializedName("total_discount_percent")
    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.TOTAL_DISCOUNT_PERCENT)
    private double totalDiscountPercent;

    @SerializedName("total_discount_sum")
    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.TOTAL_DISCOUNT_SUM)
    private double totalDiscountSum;

    @SerializedName("total_sum")
    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.TOTAL_SUM)
    private double totalSum;

    @SerializedName("status")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.STATUS)
    private String status;

    @SerializedName("comment")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.COMMENT)
    private String comment;

    @DatabaseField(dataType = DataType.STRING, columnName = Column.ORDER_ID)
    private String orderId;

    @SerializedName("product_name")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.NAME)
    private String name;

    @SerializedName("product_shortname")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.SHORT_NAME)
    private String shortname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Date getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Date getDateDel() {
        return dateDel;
    }

    public void setDateDel(Date dateDel) {
        this.dateDel = dateDel;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getDiscountSum() {
        return discountSum;
    }

    public void setDiscountSum(double discountSum) {
        this.discountSum = discountSum;
    }

    public double getTotalDiscountPercent() {
        return totalDiscountPercent;
    }

    public void setTotalDiscountPercent(double totalDiscountPercent) {
        this.totalDiscountPercent = totalDiscountPercent;
    }

    public double getTotalDiscountSum() {
        return totalDiscountSum;
    }

    public void setTotalDiscountSum(double totalDiscountSum) {
        this.totalDiscountSum = totalDiscountSum;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserDelName() {
        return userDelName;
    }

    public void setUserDelName(String userDelName) {
        this.userDelName = userDelName;
    }

    public String getCancelDescription() {
        return cancelDescription;
    }

    public void setCancelDescription(String cancelDescription) {
        this.cancelDescription = cancelDescription;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
}
