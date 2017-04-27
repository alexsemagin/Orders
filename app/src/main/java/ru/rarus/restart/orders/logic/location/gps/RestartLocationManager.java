package ru.rarus.restart.orders.data.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import javax.inject.Inject;

import ru.rarus.restart.orders.data.info.InfoStorage;
import ru.rarus.restart.orders.data.settings.RestartSettings;
import ru.rarus.restart.orders.di.scope.OrdersScope;
import rx.Observable;
import rx.subjects.BehaviorSubject;


@OrdersScope
public class RestartLocationManager implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private static final int INTERVAL = 15000;
    private static final String LOG = "LocationManager";
    public static final String STATUS_ON = "ON";
    public static final String STATUS_OFF = "OFF";
    public static final String STATUS_ERROR = "ERROR";
    public static final String STATUS_SUSPENDED = "SUSPENDED";


    @Inject
    InfoStorage infoStorage;
    @Inject
    RestartSettings restartSettings;

    private BehaviorSubject<String> locationStatusSubject = BehaviorSubject.create(STATUS_OFF);
    private BehaviorSubject<Location> locationSubject = BehaviorSubject.create();
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private Context context;

    @Inject
    public RestartLocationManager(InfoStorage infoStorage, RestartSettings restartSettings) {
        this.infoStorage = infoStorage;
        this.restartSettings = restartSettings;
    }

    public void start(Context context) {
        Log.d(LOG, "RestartLocationManager start");
        this.context = context;
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    public void stop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            locationStatusSubject.onNext(STATUS_OFF);
            Log.d(LOG, "RestartLocationManager stop");
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(LOG, "onConnected");

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d(LOG, "Checking permissions..");
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d(LOG, "You don't have a permissions");
            } else {
                Log.d(LOG, "You have a permissions");
                sendRequest(locationRequest);
            }
        }
    }

    public void sendRequest(LocationRequest locationRequest) {
        Log.d(LOG, "Sending request..");
        locationStatusSubject.onNext(STATUS_ON);
        LocationServices
                .FusedLocationApi
                .requestLocationUpdates(mGoogleApiClient, locationRequest, locationSubject::onNext);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG, "onConnectionSuspended");
        locationStatusSubject.onNext(STATUS_SUSPENDED);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(LOG, "onConnectionFailed");
        locationStatusSubject.onNext(STATUS_ERROR);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(LOG, "onLocationChanged " + location.getLongitude() + " " + location.getLatitude());
    }

    public Observable<Location> getLocationObservable() {
        return locationSubject;
    }

    public Observable<String> getStatusObservable() {
        return locationStatusSubject;
    }

    public LocationRequest getLocationRequest() {
        return locationRequest;
    }
}
