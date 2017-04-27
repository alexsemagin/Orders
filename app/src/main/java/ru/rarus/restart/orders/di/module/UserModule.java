package ru.rarus.restart.orders.di.module;

import android.content.Context;
import android.provider.CallLog;

import dagger.Module;
import dagger.Provides;

import ru.rarus.restart.orders.data.RxBus;
import ru.rarus.restart.orders.data.cache.OrdersCache;
import ru.rarus.restart.orders.data.cache.db.DataBaseHelper;
import ru.rarus.restart.orders.data.info.InfoStorage;
import ru.rarus.restart.orders.data.retrofit.OsrmApi;
import ru.rarus.restart.orders.data.retrofit.RestAPI;
import ru.rarus.restart.orders.data.settings.RestartSettings;
import ru.rarus.restart.orders.di.managers.CallLogManager;
import ru.rarus.restart.orders.di.managers.DrawerManager;
import ru.rarus.restart.orders.di.managers.ErrorsManager;
import ru.rarus.restart.orders.di.managers.GEOManager;
import ru.rarus.restart.orders.di.managers.HistoryManager;
import ru.rarus.restart.orders.di.managers.UserManager;
import ru.rarus.restart.orders.di.scope.UserScope;
import ru.rarus.restart.orders.di.managers.OrdersManager;


@UserScope
@Module
public class UserModule {

    @UserScope
    @Provides
    HistoryManager provideHistoryManager(Context context, DataBaseHelper dataBaseHelper, RxBus mRxBus){
        return new HistoryManager(context, dataBaseHelper,  mRxBus);
    }

    @UserScope
    @Provides
    UserManager provideUserManager(Context context, InfoStorage infoStorage, DataBaseHelper dataBaseHelper, RxBus mRxBus, HistoryManager historyManager, RestAPI restAPI){
        return new UserManager(context, infoStorage, dataBaseHelper,mRxBus, historyManager, restAPI);
    }


    @UserScope
    @Provides
    GEOManager provideGEOManager(Context context, DataBaseHelper dataBaseHelper, RxBus mRxBus, OrdersCache ordersCache, OsrmApi osrmApi, RestartSettings restartSettings){
        return new GEOManager(context, dataBaseHelper,  mRxBus, ordersCache, osrmApi, restartSettings);
    }

    @UserScope
    @Provides
    OrdersManager provideOrdersManager (Context context, RestAPI restAPI, DataBaseHelper dataBaseHelper, ErrorsManager errorsManager, RxBus mRxBus, GEOManager geoManager, UserManager userManager, OrdersCache ordersCache, HistoryManager historyManager) {
        return new OrdersManager(context,restAPI, dataBaseHelper, errorsManager, mRxBus, geoManager, userManager, ordersCache, historyManager);
    }


    @UserScope
    @Provides
    DrawerManager provideDrawerManager (Context context, DataBaseHelper dataBaseHelper, RxBus mRxBus, OrdersCache ordersCache){
        return new DrawerManager(context, dataBaseHelper,  mRxBus, ordersCache);
    }

    @UserScope
    @Provides
    CallLogManager provideCallLogManager (Context context, DataBaseHelper dataBaseHelper, RxBus mRxBus){
        return new CallLogManager(context, dataBaseHelper,  mRxBus);
    }

}
