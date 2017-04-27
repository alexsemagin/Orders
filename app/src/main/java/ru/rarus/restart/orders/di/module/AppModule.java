package ru.rarus.restart.orders.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.rarus.restart.orders.MyApp;
import ru.rarus.restart.orders.data.RxBus;
import ru.rarus.restart.orders.data.info.InfoStorage;
import ru.rarus.restart.orders.data.retrofit.RestAPI;
import ru.rarus.restart.orders.data.settings.RestartSettings;
import ru.rarus.restart.orders.data.cache.db.DataBaseHelper;
import ru.rarus.restart.orders.di.managers.ErrorsManager;
import ru.rarus.restart.orders.di.managers.SyncManager;


@Module
public class AppModule {

    @Provides
    @Singleton
    MyApp provideApp() {
        return MyApp.getApp();
    }

    @Provides
    @Singleton
    Context provideContext(MyApp app) {
        return app.getApplicationContext();
    }


    @Provides
    @Singleton
    RestartSettings provideRestartSettings(Context context) {
        return new RestartSettings(context);
    }

    @Provides
    InfoStorage provideInfoStorage(Context context, RestartSettings restartSettings ) {
        return new InfoStorage(context);
    }



    @Provides
    @Singleton
    DataBaseHelper provideDatabaseHelper(Context context, InfoStorage infoStorage) {
        return new DataBaseHelper(context, infoStorage, infoStorage.getDatabaseName(), infoStorage.getDatabaseVersion());
    }


    @Provides
    @Singleton
    ErrorsManager provideErrorsManager(Context context , RestAPI restAPI, SyncManager syncManager){
        return new ErrorsManager(context,restAPI, syncManager);
    }

    @Provides
    @Singleton
    SyncManager provideSyncManager(Context context, RestAPI restAPI,DataBaseHelper dataBaseHelper, InfoStorage infoStorage) {
        return new SyncManager(context, restAPI, dataBaseHelper, infoStorage);
    }

    @Provides
    @Singleton
    RxBus provideRxBus(){
        return new RxBus();
    }




}
