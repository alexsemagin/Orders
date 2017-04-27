package ru.rarus.restart.orders.ui.orders.map;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

import javax.inject.Inject;

import ru.rarus.restart.orders.MyApp;
import ru.rarus.restart.orders.data.RxBus;
import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.di.managers.GEOManager;
import ru.rarus.restart.orders.di.managers.OrdersManager;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class OrdersMapPresenter extends BasePresenter<OrdersMapView> {

    @Inject
    Context mContext;

    @Inject
    OrdersManager mOrdersManager;

    @Inject
    GEOManager mGEOManager;

    @Inject
    RxBus mRxBus;

    @Inject
    OrdersMapPresenter() {
        MyApp.getApp().getUserComponent().inject(this);

//        CompositeSubscription subscriptions = new CompositeSubscription();
//        mRxBus.registerSubscription(subscriptions, event -> {
//            if (event instanceof EventCalculateLocation) if(mView!=null) {
//                getData();
//                mView.showMessage(mContext.getString(R.string.text_calculete_time));
//            }
//        });
    }

    void getData() {


        Observable.zip( mOrdersManager.getMyOrdersFromDB(), mOrdersManager.getFreeOrdersFromDB(), (orders, orders2) -> {
            //if (showAll) {
                orders.addAll(orders2);
            //}
            return orders;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleOrders
                        , e -> handleThrowable(e)
                );

    }


    private void handleOrders(List<Order> orders) {
        if (mView != null) mView.showOrders(orders, mGEOManager.getLatDP(), mGEOManager.getLonDP());
    }

    void openOrder(Order order) {
        if (mView != null && order != null) mView.openOrder(order.getId());
    }

    Bitmap getBitMapMyMarker(Order order){
        return mGEOManager.getHashNumbersMyNumbers(order);
    }

    Bitmap getBitMapFreeMarker() {
        return mGEOManager.getBitMapFreeMarker();
    }

    Bitmap getBitMapPlaceMarker() {
        return mGEOManager.getBitMapPlaceMarker();
    }
}
