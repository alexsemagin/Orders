package ru.rarus.restart.orders.ui.order.add;


import android.content.Context;
import android.text.Editable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.Date;

import javax.inject.Inject;

import ru.rarus.restart.orders.MyApp;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.RxBus;
import ru.rarus.restart.orders.data.events.EventUpdateOrderList;
import ru.rarus.restart.orders.data.settings.RestartSettings;
import ru.rarus.restart.orders.di.managers.OrdersManager;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrderAddPresenter extends BasePresenter<OrderAddInterface> {


    @Inject
    RestartSettings restartSettings;

    @Inject
    OrdersManager ordersManager;

    @Inject
    RxBus mRxBus;

    @Inject
    Context context;

    private String name = "";
    private Editable number;
    private Date date;
    private String address = "";
    private double latitude = 0d;
    private double longitude = 0d;

    OrderAddPresenter() {
        MyApp.getApp().getUserComponent().inject(this);
    }


    public void getData() {
        if (mView != null) {
            if (!name.isEmpty()) mView.showName(name);
            if (date != null) mView.showDate(date);
            if (!address.isEmpty()) mView.showAddress(address);
            if (number != null) mView.showNumber(number);
            allowCreate();
        }

    }

    void clickAddress() {
        if (mView != null) mView.chooseAddress(getLatLngBounds());
    }

    private LatLngBounds getLatLngBounds() {
        try {
            LatLng southwest;
            LatLng northeast;
            if (this.latitude == 0) {
                southwest = new LatLng(Double.valueOf(restartSettings.getLatDP()) - 0.05, Double.valueOf(restartSettings.getLonDP()) - 0.05);
                northeast = new LatLng(Double.valueOf(restartSettings.getLatDP()) + 0.05, Double.valueOf(restartSettings.getLonDP()) + 0.05);
            } else {
                southwest = new LatLng(latitude - 0.05, longitude - 0.05);
                northeast = new LatLng(latitude + 0.05, longitude + 0.05);
            }
            return new LatLngBounds(southwest, northeast);
        } catch (Exception e) {
            return null;
        }
    }

    void setDeliveryPoint(double latitude, double longitude, String str) {
        this.latitude = latitude;
        this.longitude = longitude;

        address = str;
        if (mView != null) mView.showAddress(str);
        allowCreate();
    }

    void clickDate() {
        if (mView != null) mView.showSelectDate();
    }

    void clickName() {
        if (mView != null) mView.showSetName(name);
    }

    void clickCall() {
        if (mView != null) mView.showSetNumberCall(number);
    }

    public void setName(CharSequence input) {
        name = String.valueOf(input);
        if (mView != null && !name.isEmpty()) mView.showName(name);
        allowCreate();
    }

    void setNumber(Editable text) {
        number = text;
        if (mView != null && number != null) mView.showNumber(number);
        allowCreate();
    }

    public void setDate(Date dt) {
        date = dt;
        if (mView != null && date != null) mView.showDate(date);
        allowCreate();
    }

    private void allowCreate() {
        if (!name.isEmpty() &&
                date != null &&
                !address.isEmpty() &&
                number != null) {
            if (mView != null) mView.showFab(true);
        } else if (mView != null) mView.showFab(false);
    }

    void clickFab() {
        ordersManager.createNewOrder(name, date, address, String.valueOf(number))
                .subscribe(o -> {
                    if (o == 0) {
                        mRxBus.send(new EventUpdateOrderList());
                        mView.finishActivity();
                    } else {
                        mView.showMessage(context.getString(R.string.write_db_error_message));
                    }
                }, e -> mView.showMessage(context.getString(R.string.write_db_error_message)));
    }

    void clickContacts() {
        mView.checkReadContactsPermissions();
    }

    void clickCallLog() {
        mView.initSelectCallLogIntent();
    }
}
