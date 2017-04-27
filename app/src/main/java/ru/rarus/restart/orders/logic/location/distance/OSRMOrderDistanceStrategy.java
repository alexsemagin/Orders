package ru.rarus.restart.orders.logic.location.distance;

import ru.rarus.restart.orders.data.entities.OrderDistance;


public class OSRMOrderDistanceStrategy implements OrderDistanceStrategy {
    @Override
    public OrderDistance handle(double currentLat, double currentLng, OrderDistance orderDistance) {
        return null;
    }

//    private static final String LOG = "OSRMOrderDistance";
//
//    private static final String OSRM_STATUS_OK = "Ok";
//    private static final String OSRM_MODE = "driving";
//
//    private final OsrmApi service;
//
//    public OSRMOrderDistanceStrategy(OsrmApi service) {
//        this.service = service;
//    }
//
//    @Override
//    public OrderDistance handle(double currentLat, double currentLng, OrderDistance orderDistance) {
//        String latLng = String.valueOf(currentLat) + "," + String.valueOf(currentLng);
//        String latLng2 = String.valueOf(orderDistance.getDestinationLat()) + "," +
//                String.valueOf(orderDistance.getDestinationLng());
//        OSRMResponse response = service.getTimeDistance(OSRM_MODE, latLng, latLng2);
//        if (response.getCode().equals(OSRM_STATUS_OK)) {
//            orderDistance.setDistanceTo(response.getRoutes().get(FIRST_ELEMENT).getDistance().toString());
//            orderDistance.setTimeTo(response.getRoutes().get(FIRST_ELEMENT).getDuration().toString());
//        } else {
//            Log.e(LOG, response.getCode());
//        }
//        return orderDistance;
//    }
//
//
//    public OrderDistance getTimeAndDistance(OrderDistance distance) {
//        String latLng = String.valueOf(distance.getOriginLat()) + "," + String.valueOf(distance.getOriginLng());
//        String latLng2 = String.valueOf(distance.getDestinationLat()) + "," + String.valueOf(distance.getDestinationLng());
//        OSRMResponse response = service.getTimeDistance(OSRM_MODE, latLng, latLng2);
//
//        if (response.getCode().equals(OSRM_STATUS_OK)) {
//            distance.setDistanceTo(response.getRoutes().get(FIRST_ELEMENT).getDistance().toString());
//            distance.setTimeTo(response.getRoutes().get(FIRST_ELEMENT).getDuration().toString());
//        } else {
//            Log.e(LOG, response.getCode());
//        }
//
//        return distance;
//    }


}
