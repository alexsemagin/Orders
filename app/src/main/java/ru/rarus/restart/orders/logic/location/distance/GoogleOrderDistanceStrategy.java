package ru.rarus.restart.orders.logic.location.distance;

import android.util.Log;

import ru.rarus.restart.orders.data.entities.OrderDistance;
import ru.rarus.restart.orders.data.retrofit.GoogleMapsAPI;
import ru.rarus.restart.orders.data.retrofit.dto.MapsDistanceResponse;

/**
 * Created by alanis on 05.01.17.
 */

public class GoogleOrderDistanceStrategy implements OrderDistanceStrategy {

    private static final String UNITS = "metric";
    private static final String MODE = "walking";
    private static final String KEY = "AIzaSyB2G72Z3u82Wgkl8vNFTRPshSc0-HKBBNI";
    private static final String GOOGLE_MAP_STATUS_OK = "OK";

    private final GoogleMapsAPI googleMapsAPI;

    public GoogleOrderDistanceStrategy(GoogleMapsAPI googleMapsAPI) {
        this.googleMapsAPI = googleMapsAPI;
    }

    @Override
    public OrderDistance handle(double currentLat, double currentLng, OrderDistance orderDistance) {
        String latLng = String.valueOf(currentLat) + String.valueOf(currentLng);
        String latLng2 = String.valueOf(orderDistance.getDestinationLat()) +
                String.valueOf(orderDistance.getDestinationLng());

        MapsDistanceResponse mapsDistanceResponse = googleMapsAPI.getTimeDistance(latLng, latLng2, UNITS, MODE, KEY);

        if (mapsDistanceResponse.getStatus().equals(GOOGLE_MAP_STATUS_OK)) {
           // orderDistance.setDistanceTo(mapsDistanceResponse.getRows().get(FIRST_ELEMENT).getElements().get(FIRST_ELEMENT).getDistance().getValue());
           // orderDistance.setTimeTo(mapsDistanceResponse.getRows().get(FIRST_ELEMENT).getElements().get(FIRST_ELEMENT).getDuration().getValue());
        } else {
            Log.d("log", mapsDistanceResponse.getStatus());
        }

        return orderDistance;
    }
}
