package ru.rarus.restart.orders.di.managers;

import android.content.Context;


import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.holder.StringHolder;

import ru.rarus.restart.orders.data.RxBus;
import ru.rarus.restart.orders.data.cache.OrdersCache;
import ru.rarus.restart.orders.data.cache.db.DataBaseHelper;
import ru.rarus.restart.orders.data.events.EventDrawer;
import ru.rarus.restart.orders.ui.abscracts.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;


public class DrawerManager extends BaseManager {


    private Drawer drawer;
    private OrdersCache ordersCache;

    public DrawerManager(Context mContext, DataBaseHelper mDataBaseHelper, RxBus mRxBus, OrdersCache ordersCache) {
        super(mContext, mDataBaseHelper, mRxBus);
        this.ordersCache = ordersCache;

        CompositeSubscription subscriptions = new CompositeSubscription();
        mRxBus.registerSubscription(subscriptions, event -> {
            if (event instanceof EventDrawer) {
                int count = ((EventDrawer) event).countMyOrder;
                if (count == 0) drawer.updateBadge(1, new StringHolder(""));
                else
                    drawer.updateBadge(BaseActivity.DRAWER_ITEM_ORDERS, new StringHolder(String.valueOf(count)));
            }
        });
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
        Observable.defer(() -> Observable.just(ordersCache.getMyOrders()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> this.drawer.updateBadge(BaseActivity.DRAWER_ITEM_ORDERS, new StringHolder(l.size() == 0 ? "" : String.valueOf(l.size())))
                        , e -> {
                            Timber.e(e.getMessage());
                        });
    }

}
