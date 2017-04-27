package ru.rarus.restart.orders.data.retrofit;

import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.rarus.restart.orders.data.retrofit.dto.MapsDistanceResponse;


public interface GoogleMapsAPI {
    @GET("json")
    MapsDistanceResponse getTimeDistance(
            @Query("origins") String latLng,
            @Query("destinations") String latLng2,
            @Query("units") String units,
            @Query("mode") String mode,
            @Query("key") String key
    );
}
