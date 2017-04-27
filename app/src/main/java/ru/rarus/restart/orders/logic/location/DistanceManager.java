package ru.rarus.restart.orders.logic.location;


/**
 * Created by lysmik on 25.01.17.
 */

public class DistanceManager {

//    private GeocodeService mGeocodeService;
//    private OSRMOrderDistanceStrategy mOsrmDistance;
//    private RestartLocationManager mLocationManager;
//    private OsrmApi osrmService;
//
//    private List<Order> orderList;
//    private List<OrderDistance> orderDistanceList;
//
//    public DistanceManager(List<Order> orders) {
//        GeocodeService mGeocodeService = new GeocodeService(RestartApp.getApp());
//        OSRMOrderDistanceStrategy mOsrmDistance = new OSRMOrderDistanceStrategy(osrmService);
//
//        orderDistanceList = new ArrayList<>();
//        this.orderList = orders;
//    }
//
//    public void setTargetLocation() {
//        mGeocodeService.parseOrderAddress(orderList);
//        for (Order o: orderList) {
//            orderDistanceList.add(mGeocodeService.geocode(o));
//        }
//    }
//
//    public void setDeviceLocation() {
//        MyApp.getApp().getLocationManager()
//                .getLocationObservable()
//                .subscribe(location -> {
//                    for (OrderDistance orderDistance : orderDistanceList) {
//                        orderDistance.setOriginLat(location.getCurrentLatitude());
//                        orderDistance.setOriginLng(location.getLongitude());
//                    }
//                });
//    }
//
//    public void setTimeAndDistance() {
//        for (OrderDistance orderDistance : orderDistanceList) {
//            mOsrmDistance.getTimeAndDistance(orderDistance);
//        }
//    }
//
//    public List<OrderDistance> getOrderDistanceList() {
//        return orderDistanceList;
//    }

}
