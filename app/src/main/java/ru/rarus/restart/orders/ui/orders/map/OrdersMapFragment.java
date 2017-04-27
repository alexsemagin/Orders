package ru.rarus.restart.orders.ui.orders.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.data.info.InfoStorage;
import ru.rarus.restart.orders.ui.abscracts.BaseFragment;
import ru.rarus.restart.orders.ui.order.OrderActivity;

import static ru.rarus.restart.orders.data.info.InfoStorage.dfMoney;
import static ru.rarus.restart.orders.data.info.InfoStorage.formatDate;


public class OrdersMapFragment extends BaseFragment implements OnMapReadyCallback, OrdersMapView {

    private OrdersMapPresenter mPresenter;
    private GoogleMap mGoogleMap;
    private Map<String, Order> hashMarkers = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = new OrdersMapPresenter();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.container_map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().replace(R.id.container_map, mapFragment).commit();

        }
        mapFragment.getMapAsync(this);
        mPresenter.setView(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mPresenter.getData();

    }

    @Override
    public void showOrders(List<Order> orders, String currentLatitude, String currentLongitude) {
        boolean isMarker = false;

        mGoogleMap.clear();

        if (orders.size() > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (int i = 0; i < orders.size(); i++) {
                Order order = orders.get(i);
                if (order.getLocation() == null) continue;
                isMarker = true;
                Marker marker = mGoogleMap.addMarker(
                        new MarkerOptions()
                                .position(new LatLng(order.getLocation().getDestinationLat(), order.getLocation().getDestinationLng()))
                                .title(order.getNumString() + "  " + formatDate.format(order.getDateDelivery()))
                                .snippet(dfMoney.format(order.getTotalSum()))
                                .icon(BitmapDescriptorFactory.fromBitmap(order.isFree()?mPresenter.getBitMapFreeMarker():mPresenter.getBitMapMyMarker(order)))

                );
                hashMarkers.put(marker.getId(), order);
                InfoStorage.formatDate.format(order.getDateDelivery());
                builder.include(marker.getPosition());
            }

            if (!currentLatitude.isEmpty()) {
                isMarker = true;
                Marker marker = mGoogleMap.addMarker(
                        new MarkerOptions()
                                .position(new LatLng(Double.valueOf(currentLatitude), Double.valueOf(currentLongitude)))
                                .title(getString(R.string.text_address_point_delivery))
                                .icon(BitmapDescriptorFactory.fromBitmap(mPresenter.getBitMapPlaceMarker()))
                );

                builder.include(marker.getPosition());
            }

            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mGoogleMap.setMyLocationEnabled(true);
            }


            if (isMarker) {
                LatLngBounds bounds = builder.build();
                DisplayMetrics dm = getResources().getDisplayMetrics();

                int padding = dm.widthPixels / 4;
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, dm.widthPixels, dm.heightPixels, padding);

                mGoogleMap.moveCamera(cu);
                mGoogleMap.setOnMarkerClickListener(marker -> false);
                mGoogleMap.setOnInfoWindowClickListener(marker -> mPresenter.openOrder(hashMarkers.get(marker.getId())));
            }
        }
    }


    @Override
    public void openOrder(String orderId) {
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        Bundle args = new Bundle();
        args.putString("ID", orderId);
        intent.putExtras(args);
        startActivity(intent);

    }

    @Override
    public void setCurrentLocation(LatLng currentLocation) {

    }

    @Override
    public void setRoute(String route) {

    }

}
