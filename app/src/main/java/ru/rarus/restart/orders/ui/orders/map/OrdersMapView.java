package ru.rarus.restart.orders.ui.orders.map;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;


interface OrdersMapView extends BasePresenter.BaseView {

    void showOrders(List<Order> orders, String currentLatitude, String currentLongitude);

    void openOrder(String id);

    void setCurrentLocation(LatLng currentLocation);

    void setRoute(String route);
}
