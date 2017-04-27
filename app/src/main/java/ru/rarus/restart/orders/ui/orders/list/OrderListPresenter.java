package ru.rarus.restart.orders.ui.orders.list;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import ru.rarus.restart.orders.MyApp;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.data.RxBus;
import ru.rarus.restart.orders.data.entities.Order;
import ru.rarus.restart.orders.data.events.EventCalculateLocation;
import ru.rarus.restart.orders.data.events.EventNeedLocation;
import ru.rarus.restart.orders.data.events.EventProgressListOrder;
import ru.rarus.restart.orders.data.events.EventUpdateOrderList;
import ru.rarus.restart.orders.di.managers.OrdersManager;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;
import ru.rarus.restart.orders.ui.orders.flexible.DeliveryItem;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class OrderListPresenter extends BasePresenter<OrdersView> {

    static final int SORT_TIME = 0;
    static final int SORT_SUM = 1;
    static final int SORT_DIS = 2;

    @Inject
    OrdersManager ordersManager;

    @Inject
    Context mContext;

    @Inject
    RxBus mRxBus;


    private int currentSort = SORT_TIME;
    private boolean progressAvailable = false;

    OrderListPresenter() {

        MyApp.getApp().getUserComponent().inject(this);

        CompositeSubscription subscriptions = new CompositeSubscription();
        mRxBus.registerSubscription(subscriptions, event -> {
            if (event instanceof EventProgressListOrder)
                if (mView != null && progressAvailable) mView.setProgress(true);
            if (event instanceof EventCalculateLocation) {
                if (mView != null)
                    mView.showMessage(mContext.getString(R.string.text_calculete_time));
                getData(false, 0);
            }
            if (event instanceof EventNeedLocation) if (mView != null)
                mView.showMessageDialog(mContext.getString(R.string.text_set_place_get_order), mContext.getString(R.string.text_setting_need_for_calculate) + "\n" +
                        mContext.getString(R.string.text_in_route_order));
            if (event instanceof EventUpdateOrderList) getData(false, 0);
        });
    }

    void getData(boolean update, int position) {

        if (position == 0) progressAvailable = true;
        ordersManager.getOrders(false, update)
                .flatMap(Observable::from)
                .toSortedList((o1, o2) -> o1.getLocation().getIndex().compareTo(o2.getLocation().getIndex()))
                .map(list -> DeliveryItem.getList(list, mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                            if (mView != null) mView.showMyOrders(list);
                            getFreeOrders(update);
                        }
                        , e -> {
                            handleThrowable(e);
                            // getFreeOrders(update);
                        }
                );
    }

    private void getFreeOrders(boolean update) {
        ordersManager.getOrders(true, update)
                .flatMap(Observable::from)
                .toSortedList(this::sortList)
                .map(list -> DeliveryItem.getList(list, mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                            progressAvailable = false;
                            if (mView != null) mView.showFreeOrders(list);
                        }
                        , e -> {
                            handleThrowable(e);
                            progressAvailable = false;
                        }
                );
    }

    void onClickOrder(String id) {
        if (mView != null) {
            mView.openOrder(id);
        }
    }

    private int sortList(Order o1, Order o2) {
        switch (currentSort) {
            case SORT_TIME:
                return o1.getDateDelivery().compareTo(o2.getDateDelivery());
            case SORT_SUM:
                return o1.getTotalSum().compareTo(o2.getTotalSum()) * -1;
            case SORT_DIS:
                return o1.getLocation().getTimeTo().compareTo(o2.getLocation().getTimeTo());
        }
        return 0;
    }


    void setSort(int i) {
        currentSort = i;
        getData(false, 0);
    }

    int getCurrentSort() {
        return currentSort;
    }


    void setNewSortMyOrders(List<String> list) {
        ordersManager.setNewSortMyOrders(list);
        getData(false, 0);
    }

    void clickFab() {
        if (mView != null) mView.addOrder();
    }

    void clickOpenMap() {
        if (mView != null) mView.openMap();
    }
}
