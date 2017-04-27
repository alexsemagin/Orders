package ru.rarus.restart.orders.ui.order;


import android.content.Context;
import android.graphics.Bitmap;

import javax.inject.Inject;

import ru.rarus.restart.orders.MyApp;
import ru.rarus.restart.orders.data.RxBus;
import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.di.managers.GEOManager;
import ru.rarus.restart.orders.di.managers.OrdersManager;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;
import timber.log.Timber;


public class OrderPresenter extends BasePresenter<OrderView> {

    static final int ATTACH_ORDER = 0;
    static final int CANCEL_ORDER = 1;
    static final int DETACH_ORDER = 2;
    static final int COMPLETE_ORDER = 3;

    @Inject
    Context mContext;

    @Inject
    OrdersManager mOrdersManager;

    @Inject
    GEOManager mGeoManager;

    @Inject
    RxBus mRxBus;

    private Order mOrder;
    private String id = "";

    OrderPresenter(String orderId) {
        MyApp.getApp().getUserComponent().inject(this);
        id = orderId;

//        CompositeSubscription subscriptions = new CompositeSubscription();
//        mRxBus.registerSubscription(subscriptions, event -> {
//            if (event instanceof EventCalculateLocation) if (mView != null) {
//                getData();
//                mView.showMessage(mContext.getString(R.string.text_calculete_time));
//            }
//        });
    }

    public Order getOrder() {
        return mOrder;
    }

    void getData() {

        mOrdersManager.getOrder(id)
                .filter(order->order!=null)
                .subscribe(order -> {
                            mOrder = order;
                            if (mView != null) {
                                mView.showOrder(order);
                            }
                        }
//                        , e -> {
//                            Timber.e(e.getMessage());
//                            handleThrowable(e);
//                        }
                );
    }


    void clickAction(int idAction) {
        switch (idAction) {
            case ATTACH_ORDER:
                if (mView != null) mView.setProgress(true);
                mOrdersManager.attachOrder(mOrder).subscribe(tr -> {
                            if (mView != null) mView.setProgress(false);
                            if (mView != null) mView.closeFragment();
                        }
                        , e -> {
                            Timber.e(e.getMessage());
                            handleThrowable(e);
                        }
                );
                break;
            case CANCEL_ORDER:
                mOrdersManager.getCancelItems().subscribe(list -> {
                            if (mView != null) mView.showChoiceCancel(list);
                        }
                        , e -> {
                            Timber.e(e.getMessage());
                            handleThrowable(e);
                        }
                );
                break;
            case DETACH_ORDER:
                if (mView != null) mView.setProgress(true);
                mOrdersManager.detachOrder(mOrder).subscribe(r -> {
                            if (mView != null) mView.setProgress(false);
                            if (mView != null) mView.closeFragment();
                        }
                        , e -> {
                            Timber.e(e.getMessage());
                            handleThrowable(e);
                        }
                );
                break;
            case COMPLETE_ORDER:
                if (mView != null) mView.showCompleteOrder();
                break;
            case 4:
                break;

        }

    }



    private void handleAction(boolean isAction) {
        if (mView != null) {
            mView.setProgress(false);
            mView.closeFragment();
        }
    }

    void callDelivery() {
        if (mView != null) mView.setProgress(false);
        if (mView != null) mView.call(mOrder.getDeliveryPhone());

    }

    void openMap(int index) {
        mGeoManager.geocode(mOrder).subscribe(r -> {
                    if (mView != null)
                        mView.openMap(mOrder, index, mGeoManager.getCurrentLatitude(), mGeoManager.getCurrentLongitude());
                }
                , e -> handleThrowable(e)
        );

    }

    void openChoiceMap() {
        if (mView != null) mView.showChoiceMap();

    }

    void getMarker() {
        mGeoManager.geocode(mOrder).subscribe(r -> {
            if (mView != null)
                mView.showMarker(mOrder, mGeoManager.getLatDP(), mGeoManager.getLonDP());
        }, e -> handleThrowable(e));
    }

    void setupYaMap() {
        if (mView != null) mView.setupYaMap();
    }

    void choiceItemCancel(String idCancel) {
        if (idCancel == null) idCancel = "";
        if (mView != null) mView.setProgress(true);
        mOrdersManager.cancelOrder(mOrder, idCancel).subscribe(this::handleAction, e -> handleThrowable(e));
    }

    void completeOrder() {
        if (mView != null) mView.setProgress(true);
        mOrdersManager.completeOrder(mOrder).subscribe(this::handleAction, e -> handleThrowable(e));
    }

    Bitmap getBitMapMyMarker(Order order) {
        return mGeoManager.getHashNumbersMyNumbers(order);
    }

    Bitmap getBitMapFreeMarker() {
        return mGeoManager.getBitMapFreeMarker();
    }
}

