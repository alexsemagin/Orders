package ru.rarus.restart.orders.di.managers;

import android.content.Context;

import ru.rarus.restart.orders.data.RxBus;
import ru.rarus.restart.orders.data.cache.db.DataBaseHelper;


public abstract class BaseManager {

    protected Context mContext;
    protected DataBaseHelper mDataBaseHelper;
    protected RxBus mRxBus;

    public BaseManager(Context mContext, DataBaseHelper mDataBaseHelper, RxBus mRxBus) {
        this.mContext = mContext;
        this.mDataBaseHelper = mDataBaseHelper;
        this.mRxBus = mRxBus;

    }
}
