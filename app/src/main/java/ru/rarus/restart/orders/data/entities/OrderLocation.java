package ru.rarus.restart.orders.data.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by lysmik on 27.12.16.
 */
@DatabaseTable(tableName = "OrderLocation")
public class OrderLocation {

    public static class Column {
        public final static String ORDER_ID = "orderId";
        public final static String LAT = "lat";
        public final static String LNG = "lng";
        public final static String DISTANCE_TO = "distanceTo";
        public final static String TIME_TO = "timeTo";
    }
    /**
     * Местоположение заказов (1:1 Заказы)
     */
    @DatabaseField(id = true, dataType = DataType.STRING, columnName = Column.ORDER_ID)
    private String orderId;

    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.LAT)
    private double lat;

    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.LNG)
    private double lng;

    @DatabaseField(dataType = DataType.STRING, columnName = Column.DISTANCE_TO)
    private String distanceTo;

    @DatabaseField(dataType = DataType.STRING, columnName = Column.TIME_TO)
    private String timeTo;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDistanceTo() {
        return distanceTo;
    }

    public void setDistanceTo(String distanceTo) {
        this.distanceTo = distanceTo;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }
}
