package ru.rarus.restart.orders.di.module;



import dagger.Module;
import dagger.Provides;
import ru.rarus.restart.orders.data.cache.CancelCache;
import ru.rarus.restart.orders.data.cache.db.DataBaseHelper;
import ru.rarus.restart.orders.data.cache.OrdersCache;
import ru.rarus.restart.orders.di.scope.UserScope;


@Module
public class CacheModule {

    @Provides
    @UserScope
    public OrdersCache provideOrdersCache(DataBaseHelper dataBaseHelper) {
        return new OrdersCache(dataBaseHelper);
    }


    @Provides
    @UserScope
    public CancelCache provideCancelCache(DataBaseHelper dataBaseHelper) {
        return new CancelCache(dataBaseHelper);
    }


}
