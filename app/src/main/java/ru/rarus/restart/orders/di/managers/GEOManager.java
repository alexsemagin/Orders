package ru.rarus.restart.orders.di.managers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.RxBus;
import ru.rarus.restart.orders.data.cache.OrdersCache;
import ru.rarus.restart.orders.data.cache.db.DataBaseHelper;
import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.data.entities.OrderDistance;
import ru.rarus.restart.orders.data.events.EventCalculateLocation;
import ru.rarus.restart.orders.data.events.EventNeedLocation;
import ru.rarus.restart.orders.data.retrofit.OsrmApi;
import ru.rarus.restart.orders.data.retrofit.dto.OSRMResponse;
import ru.rarus.restart.orders.data.settings.RestartSettings;
import ru.rarus.restart.orders.ui.abscracts.UiUtils;
import rx.Observable;

import rx.android.schedulers.AndroidSchedulers;

import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class GEOManager extends BaseManager implements LocationListener {
    private static final String OSRM_MODE = "driving";

    private final LocationManager mLocationManager;
    private RestartSettings mRestartSettings;

    private double currentLatitude;
    private double currentLongitude;

    private String latDP;
    private String lonDP;

    private OrdersCache mOrdersCache;
    private Geocoder mGeocoder;
    private OsrmApi mOsrmAp;
    private boolean isUpdate = false;
    private boolean isFirstMessage = true;
    private Map<Integer, Bitmap> hashNumbers = new HashMap<>();
    private Bitmap icFreeMarker;
    private Bitmap icPlaceMarker;

    public GEOManager(Context mContext, DataBaseHelper mDataBaseHelper, RxBus mRxBus, OrdersCache ordersCache, OsrmApi osrmAp, RestartSettings restartSettings) {
        super(mContext, mDataBaseHelper, mRxBus);

        mOrdersCache = ordersCache;
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        mGeocoder = new Geocoder(mContext);
        mOsrmAp = osrmAp;
        mRestartSettings = restartSettings;
        requestLocationUpdates();
        startTaskToUpdateDistance();
        initMyMarkerMap();
    }

    private void startTaskToUpdateDistance() {

        Observable.interval(1, 30, TimeUnit.SECONDS)
                .flatMap(new Func1<Long, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Long aLong) {
                        return updateDistanceListFreeOrders();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response -> {
                }, e -> Timber.e(e.getMessage()));
    }

    public void requestLocationUpdates() {
        Log.d("QQQ", "requestLocationUpdates");
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d("QQQ", "requestLocationUpdates++++");
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 40000, 10, this);
        }
    }


    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }


    Observable<Boolean> updateDistanceListFreeOrders() {

        latDP = mRestartSettings.getLatDP();
        lonDP = mRestartSettings.getLonDP();
        //Log.d("QQQ", "try  start get free " + latDP + "," + lonDP);
        if (latDP.isEmpty() && isFirstMessage) {
            mRxBus.send(new EventNeedLocation());
            isFirstMessage = false;
        }
        isUpdate = false;

        return Observable.from(mOrdersCache.getFreeOrders())
                .map(Order::getLocation)
                .filter(distance -> distance != null)
                .filter(distance -> !latDP.isEmpty())
                .filter(distance -> !TextUtils.equals(distance.getOriginLatString(), String.valueOf(Double.valueOf(latDP))) ||
                        !TextUtils.equals(distance.getOriginLngString(), String.valueOf(Double.valueOf(lonDP))))
                .map(c -> {

                    String latLng = latDP + "," + lonDP;
                    String latLngTo = ";" + c.getDestinationLatString() + "," + c.getDestinationLngString();
                    try {
                        OSRMResponse resp = mOsrmAp.getTimeDistance(OSRM_MODE, latLng, latLngTo).toBlocking().first();
                        c.setDistanceTo(resp.getRoutes().get(0).getDistance());
                        c.setTimeTo(resp.getRoutes().get(0).getDuration());

                        c.setOriginLat(Double.valueOf(latDP));
                        c.setOriginLng(Double.valueOf(lonDP));
                        mOrdersCache.saveLocation(c);
                        isUpdate = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                })
                .doOnCompleted(() -> {
                    if (isUpdate) mRxBus.send(new EventCalculateLocation());
                })
                .subscribeOn(Schedulers.io());
    }




    private boolean isNewGeo(double newLan, double newLon) {
        if (newLan != currentLatitude || newLon != currentLongitude) {
            currentLatitude = newLan;
            currentLongitude = newLon;
            return true;
        } else return false;
    }

    Observable<Boolean> updateDistanceMy() {

        latDP = String.valueOf(currentLatitude);
        lonDP = String.valueOf(currentLongitude);

        return Observable.from(mOrdersCache.getMyOrders())
                .map(Order::getLocation)
                .filter(distance -> distance != null)
                .filter(distance -> !latDP.isEmpty())
                .filter(distance -> !TextUtils.equals(distance.getOriginLatString(), String.valueOf(Double.valueOf(latDP))) ||
                        !TextUtils.equals(distance.getOriginLngString(), String.valueOf(Double.valueOf(lonDP))))

                .toList()
                .filter(list -> list.size()>0)
                .map(list -> {

                    HashMap<Integer, OrderDistance> hashMap = new HashMap<>();

                    String latLng = latDP + "," + lonDP;
                    String latLngTo = "";
                    int i = 0;
                    for (OrderDistance c : list) {
                        hashMap.put(i, c);
                        latLngTo = latLngTo + ";" + c.getDestinationLatString() + "," + c.getDestinationLngString();
                        i++;
                    }

                    try {
                        OSRMResponse resp = mOsrmAp.getTimeDistance(OSRM_MODE, latLng, latLngTo).toBlocking().first();
                        if (resp.getRoutes().size() > 0) {

                            //Log.d("QQQ", "all OrderDistance " + " =" + resp.getRoutes().get(0).getDistance() / 1000 + " км." + "   resp Duration()=" + resp.getRoutes().get(0).getDuration() / 60 + " мин");
                            i = 0;
                            for (OSRMResponse.Leg c : resp.getRoutes().get(0).getLegs()) {
                                //Log.d("QQQ", "Distance " + " =" + c.getDistance() / 1000 + " км." + "   duration()=" + c.getDuration() / 60 + " мин");
                                hashMap.get(i).setDistanceTo(c.getDistance());
                                hashMap.get(i).setTimeTo(c.getDuration());
                                hashMap.get(i).setOriginLat(Double.valueOf(latDP));
                                hashMap.get(i).setOriginLng(Double.valueOf(lonDP));
                                mOrdersCache.saveLocation(hashMap.get(i));
                                i++;
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                })
                .subscribeOn(Schedulers.io());
    }


    List<Order> initGeoData(List<Order> listOrder) {

        for (Order c : listOrder) {
            OrderDistance orderDistance = new OrderDistance();
            orderDistance.setOrderId(c.getId());
            mOrdersCache.initLocationOrder(c);
            if (c.getLocation() != null && c.getLocation().getDestinationLat() != 0) continue;
            //Log.d("QQQ", "try get location for order: " + c.getDeliveryAddress());
      //      try {
                List<Address> listGeo = new ArrayList<>();
//                List<Address> listGeo = mGeocoder.getFromLocationName(c.getOrderAddress().getGeoAddress(), 1);
//                if (listGeo.isEmpty())
//                    listGeo = mGeocoder.getFromLocationName(c.getOrderAddress().getGeoAddressMedium(), 1);
//                if (listGeo.isEmpty())
//                    listGeo = mGeocoder.getFromLocationName(c.getOrderAddress().getCity(), 1);

                if (!listGeo.isEmpty()) {
                    orderDistance.setDestinationLat(listGeo.get(0).getLatitude());
                    orderDistance.setDestinationLng(listGeo.get(0).getLongitude());
                }

                c.setLocation(orderDistance);
                mOrdersCache.saveLocationOrder(c);

        //    } catch (IOException e) {
         //       e.printStackTrace();
        //    }
        }
        return listOrder;
    }


    public Observable<Order> geocode(Order order) {

        Observable<Order> obs = Observable.create(subscriber -> {

            if (order.getLocation() != null && order.getLocation().getDestinationLat() != 0) {
                subscriber.onNext(order);
                subscriber.onCompleted();
                return;
            }

            if (order.getOrderAddress() != null) {
                OrderDistance orderDistance = new OrderDistance();
                orderDistance.setOrderId(order.getId());

                //Log.d("QQQ", "try to get location by address");
                try {
                    List<Address> list = mGeocoder.getFromLocationName(order.getOrderAddress().getGeoAddress(), 1);

                    if (list.isEmpty())
                        list = mGeocoder.getFromLocationName(order.getOrderAddress().getGeoAddressMedium(), 1);
                    if (list.isEmpty())
                        list = mGeocoder.getFromLocationName(order.getOrderAddress().getCity(), 1);

                    if (list.isEmpty()) {
                        order.setLocation(orderDistance);
                        subscriber.onNext(order);
                    } else {
                        orderDistance.setDestinationLat(list.get(0).getLatitude());
                        orderDistance.setDestinationLng(list.get(0).getLongitude());
                        order.setLocation(orderDistance);
                        mOrdersCache.saveLocationOrder(order);

                        subscriber.onNext(order);
                        subscriber.onCompleted();
                    }
                } catch (IOException e) {
                    subscriber.onError(new Throwable(mContext.getString(R.string.text_not_found_geo_map)));
                }
            } else {
                subscriber.onError(new Throwable(mContext.getString(R.string.text_not_set_address_delivery)));
            }
        });
        return obs.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d("QQQ", "onLocationChanged ");
        if (isNewGeo(location.getLatitude(), location.getLongitude())) {
            updateDistanceMy().subscribeOn(Schedulers.io()).subscribe(r -> {
            }, e -> {
            });
        }
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("QQQ", "onStatusChanged ");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("QQQ", "onProviderEnabled ");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("QQQ", "onProviderDisabled ");

    }

    public void testOsrmApi(Order order) {
        String latLng = String.valueOf(currentLatitude) + "," + String.valueOf(currentLatitude);
        String latLng2 = order.getLocation().getDestinationLatString() + "," + order.getLocation().getDestinationLngString();

        mOsrmAp.getTimeDistance(OSRM_MODE, latLng, latLng2)
                .subscribeOn(Schedulers.io())
                .subscribe(response -> Log.d("QQQ", "----- " + response)
                        , e -> Log.e("QQQ", " e " + e.getMessage()));
    }

    public String getLatDP() {
        return mRestartSettings.getLatDP();
    }


    public String getLonDP() {
        return mRestartSettings.getLonDP();
    }


    private void initMyMarkerMap() {

        icFreeMarker = UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_ic_add_location);
        icPlaceMarker = UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_ic_place_current);

        hashNumbers.put(0, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_1));
        hashNumbers.put(1, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_2));
        hashNumbers.put(2, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_3));
        hashNumbers.put(3, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_4));
        hashNumbers.put(4, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_5));
        hashNumbers.put(5, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_6));
        hashNumbers.put(6, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_7));
        hashNumbers.put(7, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_8));
        hashNumbers.put(8, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_9));
        hashNumbers.put(9, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_10));
        hashNumbers.put(10, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_11));
        hashNumbers.put(11, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_12));
        hashNumbers.put(12, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_13));
        hashNumbers.put(13, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_14));
        hashNumbers.put(14, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_15));
        hashNumbers.put(15, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_16));
        hashNumbers.put(16, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_17));
        hashNumbers.put(17, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_18));
        hashNumbers.put(18, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_19));
        hashNumbers.put(19, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_20));
        hashNumbers.put(20, UiUtils.getBitmapFromVectorDrawable(mContext, R.drawable.vector_drawable_20_plus));

    }

    public Bitmap getHashNumbersMyNumbers(Order order) {
        if(hashNumbers.size()<=order.getLocation().getIndex()) return hashNumbers.get(hashNumbers.size()-1);
        else return hashNumbers.get(order.getLocation().getIndex());
    }

    public Bitmap getBitMapFreeMarker() {
        return icFreeMarker;
    }

    public Bitmap getBitMapPlaceMarker() {
        return icPlaceMarker;
    }
}
