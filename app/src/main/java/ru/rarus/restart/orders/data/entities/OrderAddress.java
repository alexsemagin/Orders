package ru.rarus.restart.orders.data.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "OrderAddress")
public class OrderAddress {


    public static class Column {
        public final static String ORDER_ID = "orderId";
        public final static String INDEX = "index";
        public final static String REGION = "region";
        public final static String AREA = "area";
        public final static String CITY = "city";
        public final static String LOCALITY = "locality";
        public final static String STREET = "street";
        public final static String HOUSE = "house";
        public final static String HOUSING = "housing";
        public final static String APARTMENT = "apartment";
        public final static String PORCH = "porch";
        public final static String FLOOR = "floor";
        public final static String DOOR_CODE = "doorCode";
        public final static String SUBWAY = "subway";
        public final static String ZONE = "zone";
    }

    public OrderAddress() {

    }

    /**
     * Адресс заказа (1:1 Заказы)
     */
    @DatabaseField(id = true, dataType = DataType.STRING, columnName = Column.ORDER_ID)
    private String orderId;

    @DatabaseField(dataType = DataType.STRING, columnName = Column.INDEX)
    private String index = "";

    @DatabaseField(dataType = DataType.STRING, columnName = Column.REGION)
    private String region = "";

    @DatabaseField(dataType = DataType.STRING, columnName = Column.AREA)
    private String area = "";

    @DatabaseField(dataType = DataType.STRING, columnName = Column.CITY)
    private String city = "";

    @DatabaseField(dataType = DataType.STRING, columnName = Column.LOCALITY)
    private String locality = "";

    @DatabaseField(dataType = DataType.STRING, columnName = Column.STREET)
    private String street = "";

    @DatabaseField(dataType = DataType.STRING, columnName = Column.HOUSE)
    private String house = "";

    @DatabaseField(dataType = DataType.STRING, columnName = Column.HOUSING)
    private String housing = "";

    @DatabaseField(dataType = DataType.STRING, columnName = Column.APARTMENT)
    private String apartment = "";

    @DatabaseField(dataType = DataType.STRING, columnName = Column.PORCH)
    private String porch = "";

    @DatabaseField(dataType = DataType.STRING, columnName = Column.FLOOR)
    private String floor = "";

    @DatabaseField(dataType = DataType.STRING, columnName = Column.DOOR_CODE)
    private String doorCode = "";

    @DatabaseField(dataType = DataType.STRING, columnName = Column.SUBWAY)
    private String subway = "";

    @DatabaseField(dataType = DataType.STRING, columnName = Column.ZONE)
    private String zone = "";

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getHousing() {
        return housing;
    }

    public void setHousing(String housing) {
        this.housing = housing;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getPorch() {
        return porch;
    }

    public void setPorch(String porch) {
        this.porch = porch;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDoorCode() {
        return doorCode;
    }

    public void setDoorCode(String doorCode) {
        this.doorCode = doorCode;
    }

    public String getSubway() {
        return subway;
    }

    public void setSubway(String subway) {
        this.subway = subway;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }


    @Override
    public String toString() {
        return "OrderAddress{" +
                "orderId='" + orderId + '\'' +
                ", index='" + index + '\'' +
                ", region='" + region + '\'' +
                ", area='" + area + '\'' +
                ", city='" + city + '\'' +
                ", locality='" + locality + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", housing='" + housing + '\'' +
                ", apartment='" + apartment + '\'' +
                ", porch='" + porch + '\'' +
                ", floor='" + floor + '\'' +
                ", doorCode='" + doorCode + '\'' +
                ", subway='" + subway + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }


    public String getShortString() {
        String address =""+
                (area.isEmpty()?"": ", р-н " + area) +
                (city.isEmpty()?"": ", г. " + city) +
                (subway.isEmpty()?"": ", м. " + subway)+
                (locality.isEmpty()?"": ", locality='" + locality) +
                (street.isEmpty()?"": ", ул. " + street) +
                (house.isEmpty()?"": ", д. " + house ) +
                (housing.isEmpty()?"": "/" + housing) +
                (apartment.isEmpty()?"": ", кв. " + apartment) +
                (porch.isEmpty()?"": ", подъезд " + porch ) +
                (floor.isEmpty()?"": ", этаж " + floor) +
                (doorCode.isEmpty()?"": ", домофон " + doorCode)
                ;


        if(address.length()>1)address = address.replaceFirst(", ","");
        return address;

    }

    public String getGeoAddress() {
        return  ""+city + " " + street + " "+ house+(housing.isEmpty()?"": "/" + housing);

    }

    public String getGeoAddressMedium() {
        return  ""+city + " " + street.replace("просп.","") + " "+ house+(housing.isEmpty()?"": "/" + housing);

    }

    public String getGeoAddressStreetAndHouse() {
        return  "" + street + " "
                + house+(housing.isEmpty()?"": "/" + housing)
                + (apartment.isEmpty()?"": " кв." + apartment);

    }

}
