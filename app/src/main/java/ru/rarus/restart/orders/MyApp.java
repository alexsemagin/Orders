package ru.rarus.restart.orders;

import android.annotation.SuppressLint;
import android.app.Application;

import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import ru.rarus.restart.orders.di.component.AppComponent;
import ru.rarus.restart.orders.di.component.DaggerAppComponent;
import ru.rarus.restart.orders.di.component.UserComponent;
import ru.rarus.restart.orders.di.module.UserModule;
import timber.log.Timber;


public class MyApp extends Application {

    private static MyApp mApp;

    private AppComponent mAppComponent;
    private UserComponent mUserComponent;
    private String deviceId;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mApp = this;
        getComponent();
        Timber.plant(new Timber.DebugTree());

    }


    public AppComponent getComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .build();

            SharedPreferences mSp = getSharedPreferences("InfoStorage", 0);
            String idUser = mSp.getString("KEY_USER_ID", "");
            if(!idUser.isEmpty()) CreateUserComponent();

        }
        return mAppComponent;
    }

    public UserComponent getUserComponent(){
        return mUserComponent;
    }

    public UserComponent CreateUserComponent(){
        if (mUserComponent == null) mUserComponent = mAppComponent.userComponentBuilder()
                .userModule(new UserModule())
                .build();
        return mUserComponent;
    }

    public void removeUserComponent(){
        mUserComponent = null;
        SharedPreferences mSp = getSharedPreferences("InfoStorage", 0);
        mSp.edit().putString("KEY_USER_ID", "").apply();

    }



    @NonNull
    public static MyApp getApp() {
        return mApp;
    }

    @SuppressLint("HardwareIds")
    public String getAndroidId() {
        if (deviceId==null) deviceId= Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        return deviceId;
    }
}
