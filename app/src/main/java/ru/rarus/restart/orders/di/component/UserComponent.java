package ru.rarus.restart.orders.di.component;

import dagger.Subcomponent;
import ru.rarus.restart.orders.di.module.CacheModule;
import ru.rarus.restart.orders.di.module.UserModule;
import ru.rarus.restart.orders.di.scope.UserScope;
import ru.rarus.restart.orders.ui.MainActivity;
import ru.rarus.restart.orders.ui.account.AccountPresenter;
import ru.rarus.restart.orders.ui.history.HistoryPresenter;
import ru.rarus.restart.orders.ui.order.OrderPresenter;
import ru.rarus.restart.orders.ui.order.add.OrderAddPresenter;
import ru.rarus.restart.orders.ui.order.add.callLog.CallLogActivity;
import ru.rarus.restart.orders.ui.order.add.callLog.CallLogPresenter;
import ru.rarus.restart.orders.ui.orders.list.OrderListPresenter;
import ru.rarus.restart.orders.ui.orders.map.OrdersMapPresenter;


@UserScope
@Subcomponent(modules = {UserModule.class, CacheModule.class})
public interface UserComponent {

    void inject(OrderListPresenter orderListPresenter);
    void inject(MainActivity mainActivity);
    void inject(OrderPresenter orderPresenter);
    void inject(AccountPresenter accountPresenter);
    void inject(HistoryPresenter historyPresenter);
    void inject(CallLogActivity callLogActivity);
    void inject(CallLogPresenter callLogPresenter);
    void inject(OrdersMapPresenter ordersMapPresenter);
    void inject(OrderAddPresenter orderAddPresenter);

    @Subcomponent.Builder
    interface Builder{
        UserComponent.Builder userModule(UserModule userModule);
        UserComponent build();
    }

}
