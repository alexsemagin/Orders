package ru.rarus.restart.orders.ui.order;

import java.util.List;

import ru.rarus.restart.orders.data.entities.Cancel;
import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;


public interface OrderView extends BasePresenter.BaseView {

    void showOrder(Order order);

    void call(String phone);

    void closeFragment();

    void openMap(Order mOrder, int index, double latitude, double longitude);

    void showChoiceMap();

    void showMarker(Order mOrder, String currentLatitude, String currentLongitude);

    void setupYaMap();

    void showChoiceCancel(List<Cancel> list);

    void showCompleteOrder();
}
