package ru.rarus.restart.orders.data.retrofit;


import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.rarus.restart.orders.data.retrofit.dto.OSRMResponse;
import rx.Observable;


public interface OsrmApi {

    @GET("route/v1/{mode}/{latLng1}{latLng2}?overview=false")
    Observable<OSRMResponse> getTimeDistance(
            @Path("mode") String mode,
            @Path("latLng1") String latLng1,
            @Path("latLng2") String latLng2
    );

}
