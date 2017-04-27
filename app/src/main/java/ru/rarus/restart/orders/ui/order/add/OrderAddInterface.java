package ru.rarus.restart.orders.ui.order.add;

import android.text.Editable;

import com.google.android.gms.maps.model.LatLngBounds;

import java.util.Date;

import ru.rarus.restart.orders.ui.abscracts.BasePresenter;


interface OrderAddInterface extends BasePresenter.BaseView {
    void chooseAddress(LatLngBounds latLngBounds);

    void showSelectDate();

    void showSetName(String name);

    void showSetNumberCall(Editable number);


    void showDate(Date date);

    void showAddress(String address);

    void showNumber(Editable number);

    void showName(String name);

    void showFab(boolean b);

    void checkReadContactsPermissions();

    void initSelectCallLogIntent();

    void finishActivity();
}
