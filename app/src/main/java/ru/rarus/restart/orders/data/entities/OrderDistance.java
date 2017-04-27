package ru.rarus.restart.orders.data.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static ru.rarus.restart.orders.data.info.InfoStorage.dfDuration;


@DatabaseTable(tableName = "OrderDistance")
public class OrderDistance {



    public static class Column {
        public final static String INDEX_LOGISTIC = "indexLogistic";
        public final static String ORDER_ID = "orderId";
        public final static String ORIGIN_LAT = "originLat";
        public final static String ORIGIN_LNG = "originLng";
        public final static String DESTINATION_LAT = "destinationLat";
        public final static String DESTINATION_LNG = "destinationLng";
        public final static String DISTANCE_TO = "distanceTo";
        public final static String TIME_TO = "timeTo";
    }
    /**
     * Местоположение заказов (1:1 Заказы)
     */
    @DatabaseField(id = true, dataType = DataType.STRING, columnName = Column.ORDER_ID)
    private String orderId;

    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.ORIGIN_LAT)
    private double originLat;

    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.ORIGIN_LNG)
    private double originLng;

    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.DESTINATION_LAT)
    private double destinationLat;

    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.DESTINATION_LNG)
    private double destinationLng;

    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.DISTANCE_TO)
    private double distanceTo;

    @DatabaseField(dataType = DataType.DOUBLE, columnName = Column.TIME_TO)
    private double timeTo;

    @DatabaseField(dataType = DataType.INTEGER, columnName = Column.INDEX_LOGISTIC)
    private int index;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getOriginLat() {
        return originLat;
    }

    public void setOriginLat(double originLat) {
        this.originLat = originLat;
    }


    public String getOriginLatString() {
        return String.valueOf(originLat);
    }


    public String getOriginLngString() {
        return String.valueOf(originLng);
    }

    public double getOriginLng() {
        return originLng;
    }

    public void setOriginLng(double originLng) {
        this.originLng = originLng;
    }

    public double getDestinationLat() {
        return destinationLat;
    }
    public String getDestinationLatString() {
        return String.valueOf(destinationLat);
    }

    public void setDestinationLat(double destinationLat) {
        this.destinationLat = destinationLat;
    }

    public double getDestinationLng() {
        return destinationLng;
    }

    public String getDestinationLngString() {
        return String.valueOf(destinationLng);
    }
    public void setDestinationLng(double destinationLng) {
        this.destinationLng = destinationLng;
    }

    public double getDistanceTo() {
        return distanceTo;
    }

    public void setDistanceTo(double distanceTo) {
        this.distanceTo = distanceTo;
    }

    public Double getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(double timeTo) {
        this.timeTo = timeTo;
    }

    public String getStringLocation() {
        if (timeTo==0d) return "";
        double ch = Math.floor(timeTo / 60 / 60);
        double mi = Math.abs((timeTo/60) - (ch*60));
        String chString = ch==0?"":dfDuration.format(ch);
        String miString = mi==0?"":dfDuration.format(mi);

        return "~"+(chString.isEmpty()?"":chString+ "ч ")+ (miString.isEmpty()?"":miString+" мин, ")+String.valueOf(dfDuration.format(distanceTo/1000))+" км";

    }




    public void clearDistance(){
        index = 888;
        timeTo=0d;
        distanceTo=0d;
        originLat=0;
        originLng=0;

    }

    public int compareTo(boolean b) {
        return 0;
    }

}
