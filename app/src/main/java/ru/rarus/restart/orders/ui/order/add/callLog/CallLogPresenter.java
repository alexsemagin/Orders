package ru.rarus.restart.orders.ui.order.add.callLog;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import ru.rarus.restart.orders.MyApp;
import ru.rarus.restart.orders.data.entities.CallLogOperation;
import ru.rarus.restart.orders.di.managers.CallLogManager;
import ru.rarus.restart.orders.ui.abscracts.BasePresenter;


public class CallLogPresenter extends BasePresenter<CallLogInterface> {

    @Inject
    CallLogManager mCallLogManager;

    CallLogPresenter() {
        MyApp.getApp().getUserComponent().inject(this);
    }

    public void getData() {
        Log.d("log", "Presenter get data");
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mView.checkPermissions();
        } else {
            Log.d("log", "Version < M");
           mView.showData(mCallLogManager.requestCallLog());
        }
    }

    public List<CallLogOperation> requestLogCallManager() {
       return mCallLogManager.requestCallLog();
    }
}
