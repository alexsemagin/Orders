package ru.rarus.restart.orders.ui.orders.list;

import android.content.Context;

import java.util.List;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;

public interface OrdersView extends BasePresenter.BaseView {

    void showMyOrders(List<AbstractFlexibleItem> orders);

    void showFreeOrders(List<AbstractFlexibleItem> orders);

    void openOrder(String orderId);

    Context getViewContext();

    void openMap();

    void addOrder();
}
