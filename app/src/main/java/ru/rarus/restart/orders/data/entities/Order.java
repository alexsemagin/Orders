package ru.rarus.restart.orders.data.entities;


import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collections;
import java.util.Date;
import java.util.List;


@DatabaseTable(tableName = "Order")
public class Order {

    public static class STATUS {
        public final static int IN_WORK = 0;
        public final static int FREE = 1;
        public final static int CANCEL = 3;
        public final static int CLOSE = 4;
        public final static int SUCCESS = 5;
    }

    public static class STATUS_PAY {
        public final static int PAID = 0;
        public final static int NOT_PAY = 1;
    }

    public static class Column {
        public final static String ORDER_ID = "orderId";
        public final static String NUM = "num";
        public final static String DATE_ADD = "dateAdd";
        public final static String DATE_CHANGES = "dateChanges";
        public final static String DATE_PRECHECK = "datePrecheck";
        public final static String PREFIX_DB = "prefixDb";
        public final static String DATE_RESERVATION = "dateReservation";
        public final static String FIO_RESERVATION = "fioReservation";
        public final static String INFO_RESERVATION = "infoReservation";
        public final static String PREPAYMENT_CARD = "prepaymentCard";
        public final static String PREPAYMENT_SUM = "prepaymentSum";
        public final static String OBJECT_ID = "objectId";
        public final static String SEATS = "seats";
        public final static String STATUS = "statusOrder";
        public final static String STATUS_PAY = "statusPay";
        public final static String ORIGIN = "origin";
        public final static String LABEL = "label";
        public final static String CARD_ID = "cardId";
        public final static String DISCOUNT_PERCENT = "discountPercent";
        public final static String DISCOUNT_SUM = "discountSum";
        public final static String GUEST_ID = "guestId";
        public final static String IS_PAID_DELIVERY = "isPaidDelivery";
        public final static String DELIVERY_ADDRESS = "deliveryAddress";
        public final static String DELIVERY_LASTNAME = "deliveryLastName";
        public final static String DELIVERY_FIRSTNAME = "deliveryFirstName";
        public final static String DELIVERY_PATRONYMIC = "deliveryPatronymic";
        public final static String DELIVERY_PHONE = "deliveryPhone";
        public final static String DELIVERY_PHONE2 = "deliveryPhone2";
        public final static String DELIVERY_ZONE = "deliveryZone";
        public final static String DATE_READY = "dateReady";
        public final static String DATE_DELIVERY = "dateDelivery";
        public final static String DELIVERY_INFO = "deliveryInfo";
        public final static String DELIVERY_CLIENT_SUM = "deliveryClientSum";
        public final static String IS_MY = "isMy";
        public final static String TOTAL_SUM = "totalSum";
        public final static String DELIVERY_STATUS = "deliveryStatus";
    }
    /**
     * Заказы.Реквезиты объекта.
     */

    @DatabaseField(id = true, dataType = DataType.STRING, columnName = Column.ORDER_ID)
    private String id;

    @SerializedName("number")
    @DatabaseField(dataType = DataType.INTEGER, columnName = Column.NUM)
    private int num;

    @SerializedName("date_add")
    @DatabaseField(dataType = DataType.DATE, columnName = Column.DATE_ADD)
    private Date dateAdd;

    @SerializedName("date_changes")
    @DatabaseField(dataType = DataType.DATE, columnName = Column.DATE_CHANGES)
    private Date dateChanges;

    @SerializedName("date_precheck")
    @DatabaseField(dataType = DataType.DATE, columnName = Column.DATE_PRECHECK)
    private Date datePrecheck;

    @SerializedName("prefix_db")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.PREFIX_DB)
    private String prefixDb;

    @SerializedName("date_reservation")
    @DatabaseField(dataType = DataType.DATE, columnName = Column.DATE_RESERVATION)
    private Date dateReservation;

    @SerializedName("fio_reservation")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.FIO_RESERVATION)
    private String fioReservation;

    @SerializedName("info_reservation")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.INFO_RESERVATION)
    private String infoReservation;

    @SerializedName("prepayment_card")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.PREPAYMENT_CARD)
    private String prepaymentCard;

    @SerializedName("prepayment_sum")
    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.PREPAYMENT_SUM)
    private double prepaymentSum;

    @SerializedName("clientid")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.OBJECT_ID)
    private String objectId;

    @SerializedName("seats")
    @DatabaseField(dataType = DataType.INTEGER, columnName = Column.SEATS)
    private int seats;

    @SerializedName("status_order")
    @DatabaseField(dataType = DataType.INTEGER, columnName = Column.STATUS)
    private int status;


    @SerializedName("status_pay")
    @DatabaseField(dataType = DataType.INTEGER, columnName = Column.STATUS_PAY)
    private int statusPay;

    @SerializedName("origin")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.ORIGIN)
    private String origin;

    @SerializedName("label")
    @DatabaseField(dataType = DataType.INTEGER, columnName = Column.LABEL)
    private int label;

    @SerializedName("card_id")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.CARD_ID)
    private String cardId;

    @SerializedName("discount_percent")
    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.DISCOUNT_PERCENT)
    private double discountPercent;

    @SerializedName("discount_sum")
    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.DISCOUNT_SUM)
    private double discountSum;

    @SerializedName("guest_id")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.GUEST_ID)
    private String guestId;

    @SerializedName("ispaid_delivery")
    @DatabaseField(dataType = DataType.BOOLEAN, columnName = Column.IS_PAID_DELIVERY)
    private boolean isPaidDelivery;

    @SerializedName("address")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DELIVERY_ADDRESS)
    private String deliveryAddress;

    @SerializedName("person")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DELIVERY_LASTNAME)
    private String deliveryLastName;

    @SerializedName("client")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DELIVERY_FIRSTNAME)
    private String deliveryFirstName;

    @SerializedName("delivery_patronymic")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DELIVERY_PATRONYMIC)
    private String deliveryPatronymic;

    @SerializedName("phone")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DELIVERY_PHONE)
    private String deliveryPhone;

    @SerializedName("delivery_phone2")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DELIVERY_PHONE2)
    private String deliveryPhone2;

    @SerializedName("delivery_zone_id")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DELIVERY_ZONE)
    private String deliveryZone;

    @SerializedName("date_ready")
    @DatabaseField(dataType = DataType.DATE, columnName = Column.DATE_READY)
    private Date dateReady;

    @SerializedName("date")
    @DatabaseField(dataType = DataType.DATE, columnName = Column.DATE_DELIVERY)
    private Date dateDelivery;

    @SerializedName("textrequest")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DELIVERY_INFO)
    private String deliveryInfo;

    @SerializedName("delivery_client_sum")
    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.DELIVERY_CLIENT_SUM)
    private double deliveryClientSum;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = Column.IS_MY)
    private boolean my;


    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.TOTAL_SUM)
    private double totalSum;

    @SerializedName("status")
    @DatabaseField(dataType = DataType.STRING, columnName = Column.DELIVERY_STATUS)
    private String deliveryStatus;

    private OrderDistance location;


    private OrderAddress orderAddress;

    @SerializedName("items")
    private List<OrderRow> orderRows = Collections.emptyList();


    public String getName(){
        String str = ""+
                (deliveryLastName == null || deliveryLastName.isEmpty()?"":" "+deliveryLastName)+
                (deliveryFirstName==null || deliveryFirstName.isEmpty()?"":" "+deliveryFirstName)+
                (deliveryPatronymic==null || deliveryPatronymic.isEmpty()?"":" "+deliveryPatronymic);

        if(str.length()>0)str = str.replaceFirst(" ","");
        return str;
    }


    public int getStatusPay() {
        return statusPay;
    }

    public void setStatusPay(int statusPay) {
        this.statusPay = statusPay;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Double getTotalSum() {
        return totalSum ;
    }

    public void setTotalSum() {
        this.totalSum = getSumOrder();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public String getNumString() {
        return "№"+String.valueOf(num);
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Date getDateChanges() {
        return dateChanges;
    }

    public void setDateChanges(Date dateChanges) {
        this.dateChanges = dateChanges;
    }

    public Date getDatePrecheck() {
        return datePrecheck;
    }

    public void setDatePrecheck(Date datePrecheck) {
        this.datePrecheck = datePrecheck;
    }

    public String getPrefixDb() {
        return prefixDb;
    }

    public void setPrefixDb(String prefixDb) {
        this.prefixDb = prefixDb;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getFioReservation() {
        return fioReservation;
    }

    public void setFioReservation(String fioReservation) {
        this.fioReservation = fioReservation;
    }

    public String getInfoReservation() {
        return infoReservation;
    }

    public void setInfoReservation(String infoReservation) {
        this.infoReservation = infoReservation;
    }

    public String getPrepaymentCard() {
        return prepaymentCard;
    }

    public void setPrepaymentCard(String prepaymentCard) {
        this.prepaymentCard = prepaymentCard;
    }

    public double getPrepaymentSum() {
        return prepaymentSum;
    }

    public void setPrepaymentSum(double prepaymentSum) {
        this.prepaymentSum = prepaymentSum;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
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

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public boolean isPaidDelivery() {
        return isPaidDelivery;
    }

    public void setPaidDelivery(boolean paidDelivery) {
        isPaidDelivery = paidDelivery;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryLastName() {
        return deliveryLastName;
    }

    public void setDeliveryLastName(String deliveryLastName) {
        this.deliveryLastName = deliveryLastName;
    }

    public String getDeliveryFirstName() {
        return deliveryFirstName;
    }

    public void setDeliveryFirstName(String deliveryFirstName) {
        this.deliveryFirstName = deliveryFirstName;
    }

    public String getDeliveryPatronymic() {
        return deliveryPatronymic;
    }

    public void setDeliveryPatronymic(String deliveryPatronymic) {
        this.deliveryPatronymic = deliveryPatronymic;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String getDeliveryPhone2() {
        return deliveryPhone2;
    }

    public void setDeliveryPhone2(String deliveryPhone2) {
        this.deliveryPhone2 = deliveryPhone2;
    }

    public String getDeliveryZone() {
        return deliveryZone;
    }

    public void setDeliveryZone(String deliveryZone) {
        this.deliveryZone = deliveryZone;
    }

    public Date getDateReady() {
        return dateReady;
    }

    public void setDateReady(Date dateReady) {
        this.dateReady = dateReady;
    }

    public Date getDateDelivery() {
        return dateDelivery;
    }



    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getDeliveryInfo() {
        if(deliveryInfo==null)return "";
        else return deliveryInfo;
    }

    public void setDeliveryInfo(String deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public double getDeliveryClientSum() {
        return deliveryClientSum;
    }

    public void setDeliveryClientSum(double deliveryClientSum) {
        this.deliveryClientSum = deliveryClientSum;
    }

    public List<OrderRow> getOrderRows() {
        return orderRows;
    }

    public void setOrderRows(List<OrderRow> orderRows) {
        this.orderRows = orderRows;
    }

    public OrderDistance getLocation() {
        return location;
    }

    public void setLocation(OrderDistance location) {
        this.location = location;
    }

    public boolean isMy() {
        return !my;
    }

    public boolean isFree() {
        return my;
    }

    public void setMy(boolean my) {
        this.my = my;
    }



    public double getSumOrder() {
        double sum = 0;
        for (OrderRow row : orderRows) {
            sum += row.getTotalSum();
        }
        return sum;
    }

    public double getShortChange(){
        if (deliveryClientSum == 0 ) return 0;
        else {
            return deliveryClientSum - getSumOrder();
        }
    }

    public double getCountItemsOrder() {
        double count = 0;
        for (OrderRow row : orderRows) {
            count += row.getCount();
        }
        return count;
    }


    public OrderAddress getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(OrderAddress orderAddress) {
        this.orderAddress = orderAddress;
    }
}
